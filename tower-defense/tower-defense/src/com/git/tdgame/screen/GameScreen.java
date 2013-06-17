package com.git.tdgame.screen;

import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.git.tdgame.TDGame;
import com.git.tdgame.data.DataProvider;
import com.git.tdgame.gameActor.Base;
import com.git.tdgame.gameActor.Gold;
import com.git.tdgame.gameActor.MenuButton;
import com.git.tdgame.gameActor.MenuButton.ButtonType;
import com.git.tdgame.gameActor.PauseButton;
import com.git.tdgame.gameActor.PauseMenu;
import com.git.tdgame.gameActor.level.Enemy;
import com.git.tdgame.gameActor.level.LevelModel;
import com.git.tdgame.gameActor.level.Wave;
import com.git.tdgame.gameActor.projectile.AbstractProjectile;
import com.git.tdgame.gameActor.tower.Tower;
import com.git.tdgame.gameActor.tower.TowerConstructButton;
import com.git.tdgame.gameActor.tower.InfoDisplay;
import com.git.tdgame.gameActor.tower.TowerRemoveButton;
import com.git.tdgame.gameActor.tower.TowerUpgradeButton;
import com.git.tdgame.map.TDGameMapHelper;


public class GameScreen implements Screen, InputProcessor{

	// To access game functions
	private TDGame game;
	
	private Tower hoveredTower;
	private boolean isUpgradeDisplay = false;

	// Stage
	private Stage stage;
	private Image splashImage;
	private boolean defeat = false;
	private boolean victory = false;
	private List<Wave> waves;
	private int currentWave = 0;
	private LevelModel levelModel;
	private Gold gold;
	private int levelIndex;
	
	private HashMap<String, HashMap<String,String>> enemyTypes;
	private HashMap<String, HashMap<String,String>> towerTypes;

	// Map variables
	private TDGameMapHelper tdGameMapHelper;
	private Array<Array<Vector2>> paths;
	private Vector2 tileSize;
	
	// Wave variables
	private float spawnTime = 0;
	private int spawnLeft;
	private final float spawnDelay = 0.5f;
	private float waveDelay;
	private int totalSpawnLeft = 0;
	
	// Tower gui popup
	private TowerUpgradeButton towerUpgradeButton;
	private TowerRemoveButton towerRemoveButton;
	
	// Selected tower
	private TowerConstructButton selectedTower; 
	
	private InfoDisplay infoDisplay;
	
	private MenuButton quitButton;
	private MenuButton restartButton;
	private MenuButton resumeButton;

	// in-game music
	private Music music;
	
	private boolean isPaused;
	
	public GameScreen(TDGame game, LevelModel levelModel)
	{
		this.game = game;
		this.levelModel = levelModel;
		this.enemyTypes = DataProvider.getEnemyTypes();
		this.towerTypes = DataProvider.getTowerTypes();
		this.waves = levelModel.getWaveList();
		this.levelIndex = levelModel.getLevelIndex();
	}
	
	@Override
	public void render(float delta)
	{
		// Clear screen
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // Map render
		tdGameMapHelper.render();

		// Stage update
		if(!defeat && !victory && !isPaused)
		{
	        waveDelay -= delta;
			stage.act(delta);
		}
        stage.draw();
        sortActors();

        // Spawn enemies
        if(waveDelay < 0 && !defeat && spawnLeft <= 0)
        {
        	if(waves.size() > currentWave)
        	{
        		if(currentWave+1 == waves.size())
        			waveDelay = 0;
        		else
        			waveDelay = waves.get(currentWave+1).getDelay();
        		
        		if(waves.get(currentWave).getEnemies() != null)
        			spawnLeft = waves.get(currentWave).getEnemies().size();
        		else
        			spawnLeft = 0;
        		
        		currentWave++;
        	} else if(totalSpawnLeft <= 0) {
        		// To Do : Victory
        		boolean isKilledAll = true;
            	Array<Actor> actors = stage.getActors();
            	for(Actor a: actors) {
            		if(a instanceof Enemy)
            		{
            			Enemy e = (Enemy)a;
            			if(e.isAlive())
            			{
            				isKilledAll = false;
            				break;
            			}
            		}
            	}
            	if(isKilledAll)
            	{
            		victory();
            	}
        		
        	}
        }
        
        if(spawnLeft > 0 && currentWave > 0)
        {
    		if(!defeat && !victory && !isPaused)
    			spawnTime += delta;
            if(spawnTime > spawnDelay)
            {
            	spawnTime = 0;
        		
                // TO DO : Spawn from selected path
                for(Array<Vector2> path : paths)
                {
                	String currentEnemy = waves.get(currentWave-1).getEnemies().get(waves.get(currentWave-1).getEnemies().size()-spawnLeft);
                	Enemy e = new Enemy(path, enemyTypes.get(currentEnemy));
	                stage.addActor(e);
                }
                
                --spawnLeft;
                --totalSpawnLeft;
            }
        }
        
        if(towerUpgradeButton != null)
        {
			if(gold.hasEnoughGold(hoveredTower.getUpgradeCost()))
			{
				towerUpgradeButton.setEnoughGold(true);
			} else {
				towerUpgradeButton.setEnoughGold(false);
			}
        }
	}
	
	private void sortActors()
	{
        for(int ctr = 0; ctr < stage.getActors().size; ++ctr)
        {
    		int maxIndex = ctr;
    		Actor actor = stage.getActors().get(ctr);
    		
        	for(int ctr2 = ctr; ctr2 < stage.getActors().size; ++ctr2)
        	{
        		Actor newActor = stage.getActors().get(ctr2);
        		
        		// Other objects
        		if(!(newActor instanceof Tower) &&
    			   !(newActor instanceof Enemy) && !(newActor instanceof AbstractProjectile))
        		{
        			continue;
        		}
        		
        		if(newActor.getY() < actor.getY())
        		{
        			maxIndex = ctr2;
        			actor = newActor;
        		}
        	}
        	stage.getActors().swap(ctr, maxIndex);
        }
        for(int ctr = 0; ctr < stage.getActors().size; ++ctr)
        {
    		Actor newActor = stage.getActors().get(ctr);

    		if(newActor instanceof Base)
    		{
    			stage.getActors().removeIndex(ctr);
    			stage.getActors().add(newActor);
    			break;
    		}
        }
        for(int ctr = 0; ctr < stage.getActors().size; ++ctr)
        {
    		Actor newActor = stage.getActors().get(ctr);

    		if(newActor instanceof PauseMenu)
    		{
    			stage.getActors().removeIndex(ctr);
    			stage.getActors().add(newActor);
    			break;
    		}
        }
        for(int ctr = stage.getActors().size-1; ctr >= 0; --ctr)
        {
    		Actor newActor = stage.getActors().get(ctr);

    		if(newActor instanceof MenuButton)
    		{
    			stage.getActors().removeIndex(ctr);
    			stage.getActors().add(newActor);
    		}
        }
        for(int ctr = 0; ctr < stage.getActors().size; ++ctr)
        {
    		Actor newActor = stage.getActors().get(ctr);

    		if(newActor instanceof Tower)
    		{
    			Tower t = (Tower) newActor;
    			if(t.isHovered())
    			{
        			stage.getActors().removeIndex(ctr);
        			stage.getActors().add(newActor);
        			break;
    			}
    		}
        }
        for(int ctr = 0; ctr < stage.getActors().size; ++ctr)
        {
    		Actor newActor = stage.getActors().get(ctr);

    		if(newActor instanceof TowerConstructButton)
    		{
    			TowerConstructButton t = (TowerConstructButton) newActor;
	    		if(selectedTower == t)
	    		{
	    			stage.getActors().removeIndex(ctr);
	    			stage.getActors().add(newActor);
	    			break;
	    		}
    		}
        }
        for(int ctr = 0; ctr < stage.getActors().size; ++ctr)
        {
    		Actor newActor = stage.getActors().get(ctr);

    		if(newActor instanceof TowerRemoveButton)
    		{
    			stage.getActors().removeIndex(ctr);
    			stage.getActors().add(newActor);
    			break;
    		}
        }
        for(int ctr = 0; ctr < stage.getActors().size; ++ctr)
        {
    		Actor newActor = stage.getActors().get(ctr);

    		if(newActor instanceof TowerUpgradeButton)
    		{
    			stage.getActors().removeIndex(ctr);
    			stage.getActors().add(newActor);
    			continue;
    		}
        }
	}

	private void victory()
	{
		splashImage = new Image(new Texture(Gdx.files.internal("data/game/victory.png")));
		splashImage.setPosition(tdGameMapHelper.getWidth()*0.25f, tdGameMapHelper.getHeight()*0.25f);

		stage.addActor(splashImage);
		if(!victory && game.getUnlockedLevels() <= this.levelIndex)
		{
			game.unlockLevels(this.levelIndex+1);
		}
		
		victory = true;
	}

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void show()
	{
		Gdx.input.setInputProcessor(this);
		// Map load
		tdGameMapHelper = new TDGameMapHelper();
		tdGameMapHelper.setPackerDirectory("data/world/packer");
		tdGameMapHelper.loadMap(levelModel.getMapPath());
		tileSize = new Vector2(tdGameMapHelper.getMap().tileWidth,tdGameMapHelper.getMap().tileHeight);

		// Set paths
		Array<Vector2> spawnPoints = tdGameMapHelper.getStartPoints();
		paths = new Array<Array<Vector2>>();
		for(Vector2 spawnPoint : spawnPoints)
		{
			paths.add(tdGameMapHelper.getPath(spawnPoint));
		}
		
		// Camera configuration
		tdGameMapHelper.prepareCamera(game.getScreenWidth(), game.getScreenHeight());
		tdGameMapHelper.getCamera().viewportWidth = tdGameMapHelper.getWidth();
		tdGameMapHelper.getCamera().viewportHeight = tdGameMapHelper.getHeight();
		tdGameMapHelper.getCamera().position.x = tdGameMapHelper.getWidth()/2;
		tdGameMapHelper.getCamera().position.y = tdGameMapHelper.getHeight()/2;
		tdGameMapHelper.getCamera().update();

		// Stage configuration
		stage = new Stage();
		stage.setCamera(new OrthographicCamera(game.getScreenWidth(),game.getScreenHeight()));
		stage.getCamera().rotate(180,1,0,0);
		stage.setViewport(tdGameMapHelper.getWidth(), tdGameMapHelper.getHeight(), false);
		stage.getCamera().update();
		
		gold = new Gold(new Vector2(0,(tdGameMapHelper.getMap().height-1)*tileSize.y), levelModel.getGold());
		stage.addActor(gold);
		
		Vector2 endPoint = tdGameMapHelper.getEndPoint();
		stage.addActor(new Base(new Vector2(endPoint.x*tileSize.x,endPoint.y*tileSize.y),this, levelModel.getBaseHealth()));
		
		// Display
		infoDisplay = new InfoDisplay(towerTypes.keySet().size()*64);
		infoDisplay.setSize(stage.getWidth(), 70);
		infoDisplay.setPosition(0, 0);
		stage.addActor(infoDisplay);
		
		int guiPosition = 0;
		for( String key : towerTypes.keySet()  )
		{
			TowerConstructButton btn = new TowerConstructButton(towerTypes.get(key).get("texturePath"), key, Integer.valueOf(towerTypes.get(key).get("range")),Integer.valueOf(towerTypes.get(key).get("cost")));
			btn.setPosition(guiPosition, 0);
			
			guiPosition += 64;
			
			stage.addActor( btn );
		}		
		
		if(waves.size()>currentWave)
		{
			waveDelay = waves.get(currentWave).getDelay();
		}
		totalSpawnLeft = 0;
		for(Wave w : waves)
		{
			totalSpawnLeft += w.getEnemies().size();
		}
		
		music = Gdx.audio.newMusic(Gdx.files.internal("data/game/crusade.mp3"));
		music.play();
		
		PauseButton pauseBtn = new PauseButton();
		pauseBtn.setPosition(stage.getWidth()-64, 0);
		stage.addActor(pauseBtn);
		
		isPaused = false;
		
	}
	
	public void defeat()
	{
		splashImage = new Image(new Texture(Gdx.files.internal("data/game/defeat.png")));
		splashImage.setPosition(tdGameMapHelper.getWidth()*0.25f, tdGameMapHelper.getHeight()*0.25f);

		stage.addActor(splashImage);
		defeat = true;
	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void pause()
	{
		pauseGame();
	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose()
	{
		music.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(defeat || victory || isPaused)
			return false;
		Vector2 hover = stage.screenToStageCoordinates(new Vector2(screenX,screenY));
		Actor a = stage.hit(hover.x,hover.y,true);
		
		if(a instanceof Tower)
		{
			hoveredTower = (Tower) a;
			
			// Give display tower info
			infoDisplay.setSelectedActor(a);
			
			hoveredTower.setHovered(true);
			
			if(!hoveredTower.isMaxLevel())
			{
				towerUpgradeButton = new TowerUpgradeButton(hoveredTower);
				towerUpgradeButton.setPosition(hoveredTower.getX() + 32, hoveredTower.getY());
				towerUpgradeButton.setCost(hoveredTower.getUpgradeCost());
				if(gold.hasEnoughGold(hoveredTower.getUpgradeCost()))
				{
					towerUpgradeButton.setEnoughGold(true);
				} else {
					towerUpgradeButton.setEnoughGold(false);
				}
				stage.addActor( towerUpgradeButton );
			}
			
			towerRemoveButton = new TowerRemoveButton(hoveredTower);
			towerRemoveButton.setPosition(hoveredTower.getX() + 32, hoveredTower.getY() + 64);
			towerRemoveButton.setCost(hoveredTower.getRefund());
			
			stage.addActor( towerRemoveButton );
		} else if(a instanceof Enemy)
		{
			// Give display enemy info
			infoDisplay.setSelectedActor(a);
		} else if(a instanceof TowerConstructButton)
		{
			if( selectedTower == null )
			{
				selectedTower = new TowerConstructButton((TowerConstructButton) a);
				selectedTower.setPosition((int)(hover.x / tileSize.x) * tileSize.x, (int)(hover.y / tileSize.y) * tileSize.y);
				selectedTower.setHovered(true);
				stage.addActor(selectedTower);
			}
		} else if(a instanceof Base)
		{
			infoDisplay.setSelectedActor(a);
		}
		
		return false;
	}

	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(defeat || victory)
		{
			music.pause();
			game.goToLevelSelectScreen();
		}
		
		Vector2 hover = stage.screenToStageCoordinates(new Vector2(screenX,screenY));
		Actor a = stage.hit(hover.x,hover.y,true);
		
		if(a instanceof PauseButton && !isPaused)
		{
			pauseGame();
			return false;
		}
		
		if(a instanceof MenuButton)
		{
			MenuButton btn = (MenuButton) a;
			
			if(btn.getType() == ButtonType.RESUME)
			{
				resumeGame();
			}
			else if(btn.getType() == ButtonType.RESTART)
			{
				game.goToGameScreen();
				this.dispose();
			}
			else if(btn.getType() == ButtonType.QUIT)
			{
				game.goToLevelSelectScreen();
				this.dispose();
			}

			return false;
		}
		
		if(isPaused)
			return false;
		
		
		if(hoveredTower != null)
		{
			hoveredTower.setHovered(false);
		}

		if(a instanceof TowerUpgradeButton)
		{
			towerUpgradeButton = (TowerUpgradeButton) a;
			//upgrade tower
			Tower tower = towerUpgradeButton.getTower();
			tower.setUpgradeDisplay(false);
			if(gold.spentGold(tower.getUpgradeCost()))
			{
				tower.upgrade();
			}
			
		}
		
		if(a instanceof TowerRemoveButton)
		{
			towerRemoveButton = (TowerRemoveButton) a;
			Tower tower = towerRemoveButton.getTower();
			gold.addGold(tower.getRefund());
			tower.setSold(true);
			tower.remove();
		}
		
		if(selectedTower != null)
		{
			selectedTower.setPosition((int)(hover.x / tileSize.x) * tileSize.x, (int)(hover.y / tileSize.y) * tileSize.y);
			Vector2 constructionTile = new Vector2((int)(hover.x / tileSize.x), (int)(hover.y / tileSize.y));
			Vector2 constructionPixel = new Vector2(constructionTile.x * tileSize.x, constructionTile.y * tileSize.y);
			Tower newTower = new Tower(constructionPixel, towerTypes.get(selectedTower.getTowerName()));
			if(isConstructableTile(constructionTile) && gold.spentGold(newTower.getCost()))
			{
				stage.addActor(newTower);
			}
			selectedTower.setHovered(false);
			selectedTower.remove();
			selectedTower = null;
		}
		
		hidePopupButtons();
		return false;
	}

	private boolean isConstructableTile(Vector2 constructionTile)
	{
		int x = (int)constructionTile.x;
		int y = (int)constructionTile.y;
		
		// On first two row
		if(y <= 1)
			return false;
		
		// On path
		if(tdGameMapHelper.getTiles(tdGameMapHelper.getPATH_LAYER())[y][x] != 0)
			return false;
		
		// On other tower
		for(Actor a : stage.getActors())
		{
			if(a instanceof Tower)
			{
				Tower t = (Tower) a;
				if((int)(t.getX()/tileSize.x) == x && (int)(t.getY()/tileSize.y) == y)
					return false;
			}
		}
		return true;
	}

	private void hidePopupButtons()
	{
		if(towerUpgradeButton != null)
		{
			towerUpgradeButton.remove();
			towerUpgradeButton = null;	
		}
		if(towerRemoveButton != null)
		{
			towerRemoveButton.remove();
			towerRemoveButton = null;
		}
		
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		Vector2 hover = stage.screenToStageCoordinates(new Vector2(screenX,screenY));
		if(selectedTower != null)
		{
			selectedTower.setPosition((int)(hover.x / tileSize.x) * tileSize.x, (int)(hover.y / tileSize.y) * tileSize.y);
			if(!isConstructableTile(new Vector2(hover.x/tileSize.x, hover.y/tileSize.y)))
			{
				selectedTower.setRangeColor(new Color(1, 0, 0, 0.3f));
			} else {
				selectedTower.setRangeColor(new Color(0, 1, 0, 0.3f));
			}
		}
		
		Actor a = stage.hit(hover.x,hover.y,true);
		if(a instanceof TowerUpgradeButton)
		{
			if(hoveredTower.isHovered())
			{
				isUpgradeDisplay = true;
				hoveredTower.setUpgradeDisplay(true);
			}
		} else if(isUpgradeDisplay)
		{
			isUpgradeDisplay = false;
			hoveredTower.setUpgradeDisplay(false);
		}
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void pauseGame()
	{
		isPaused = true;
		stage.addActor(new PauseMenu());
		resumeButton = new MenuButton(ButtonType.RESUME);
		resumeButton.setPosition((stage.getWidth()-resumeButton.getWidth())/2, stage.getHeight()/5);
		stage.addActor(resumeButton);
		
		restartButton = new MenuButton(ButtonType.RESTART);
		restartButton.setPosition((stage.getWidth()-restartButton.getWidth())/2, stage.getHeight()*2/5);
		stage.addActor(restartButton);
		
		quitButton = new MenuButton(ButtonType.QUIT);
		quitButton.setPosition((stage.getWidth()-quitButton.getWidth())/2, stage.getHeight()*3/5);
		stage.addActor(quitButton);
	}
	
	public void resumeGame()
	{
		isPaused = false;
		Array<Actor> actors = stage.getActors();
    	for(Actor a: actors) {
    		if(a instanceof PauseMenu)
    			a.remove();
    	}
	
		resumeButton.remove();
		restartButton.remove();
		quitButton.remove();
	}
}
