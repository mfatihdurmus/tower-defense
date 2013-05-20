package com.git.tdgame.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.git.tdgame.gameActor.enemy.Enemy;
import com.git.tdgame.gameActor.enemy.Wave;
import com.git.tdgame.gameActor.tower.Tower;
import com.git.tdgame.map.TDGameMapHelper;


public class GameScreen implements Screen{

	// To access game functions
	private TDGame game;

	// Stage
	private Stage stage;
	private Image splashImage;
	private boolean defeat = false;
	private ArrayList<Wave> waves = new ArrayList<Wave>();
	private int currentWave = 0;
	private String map;
	
	private HashMap<String, HashMap<String,String>> enemyTypes;
	private HashMap<String, HashMap<String,String>> towerTypes;
	private HashMap<String, HashMap<String,String>> base;
	private List<Object> waveList;

	// Map variables
	private TDGameMapHelper tdGameMapHelper;
	private Array<Array<Vector2>> paths;
	private Vector2 tileSize;
	
	// Wave variables
	private float spawnTime = 0;
	private int spawnLeft = 20;
	private final float spawnDelay = 0.5f;
	private float waveDelay;
	
	public GameScreen(TDGame game, String map)
	{
		this.game = game;
		this.map = map;
		this.enemyTypes = DataProvider.getEnemyTypes();
		this.towerTypes = DataProvider.getTowerTypes();
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
		if(!defeat)
			stage.act(delta);
        stage.draw();

        // Spawn enemies
        waveDelay -= delta;
        if(waveDelay < 0 && !defeat)
        {
        	++currentWave;
        	if(waves.size() > currentWave)
        	{
        		waveDelay = waves.get(currentWave).getDelay();
    			spawnLeft = waves.get(currentWave).getEnemies().size();
        	} else {
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
        
        if(spawnLeft > 0)
        {
        	spawnTime += delta;
            if(spawnTime > spawnDelay)
            {
            	spawnTime = 0;
                --spawnLeft;
        		
                // TO DO : Spawn from selected path
                for(Array<Vector2> path : paths)
                {
                	Enemy e = new Enemy(path, enemyTypes.get("pikeman"));
	                e.setName(""+spawnLeft);
	                stage.addActor(e);
                }
            }
        }
	}
	
	private void victory() {
		// TODO Auto-generated method stub
		splashImage = new Image(new Texture(Gdx.files.internal("data/game/victory.png")));
		splashImage.setPosition(tdGameMapHelper.getWidth()*0.25f, tdGameMapHelper.getHeight()*0.25f);

		stage.addActor(splashImage);
		defeat = true;
	}

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void show()
	{
		// Map load
		tdGameMapHelper = new TDGameMapHelper();
		tdGameMapHelper.setPackerDirectory("data/packer");
		tdGameMapHelper.loadMap(map);
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
		stage.getCamera().update();
		stage.setViewport(tdGameMapHelper.getWidth(), tdGameMapHelper.getHeight(), false);
		
		stage.addActor(new Gold(new Vector2(0,(tdGameMapHelper.getMap().height-1)*tileSize.y)));
		
		Vector2 endPoint = tdGameMapHelper.getEndPoint();
		stage.addActor(new Base(new Vector2(endPoint.x*tileSize.x,endPoint.y*tileSize.y),this));
		
		stage.addActor(new Tower(new Vector2(10*tileSize.x,16*tileSize.y), towerTypes.get("slowingTower")));
		stage.addActor(new Tower(new Vector2(16*tileSize.x,16*tileSize.y), towerTypes.get("singleTargetTower")));
		stage.addActor(new Tower(new Vector2(15*tileSize.x,9*tileSize.y), towerTypes.get("splashDamageTower")));
		
		waves.add(new Wave());
		waves.add(new Wave());
		
		if(waves.size()>currentWave)
		{
			waveDelay = waves.get(currentWave).getDelay();
			spawnLeft = waves.get(currentWave).getEnemies().size();
		}
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
		// TODO Auto-generated method stub
	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
	}
}
