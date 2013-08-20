package com.git.tdgame.screen;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.git.tdgame.TRGame;
import com.git.tdgame.data.DataProvider;
import com.git.tdgame.guiActor.GuideButton;
import com.git.tdgame.guiActor.GuideElement;
import com.git.tdgame.guiActor.GuideElement.ElementType;


public class GuideScreen implements Screen, InputProcessor{

	public TRGame game;
	private Stage stage;
	private Image splashImage;
	
	private GuideButton backButton;
	private GuideButton enemiesButton;
	private GuideButton towersButton;
	private GuideButton hoveredButton;
	
	private HashMap<String, HashMap<String,String>> enemyTypes;
	private HashMap<String, HashMap<String,String>> towerTypes;
	
	private ArrayList<GuideElement> enemies;
	private ArrayList<GuideElement> towers;
	
	public GuideScreen(TRGame game)
	{
		this.game = game;
	}
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
		stage.act(delta);
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(1024, 512, false);
	}
	
	private void addEnemies() {
		int column = 0;
		int row = 0;
		for(GuideElement g : enemies)
		{
			g.setPosition(185+(row*100), 512-(120+column*100));
			if( row == 0 && column == 0)
				g.setHover(true);
			stage.addActor(g);
			++row;
			if(row >= 3)
			{
				row = 0;
				column++;
			}
		}
	}

	private void addTowers() {
		int column = 0;
		int row = 0;
		for(GuideElement g : towers)
		{
			g.setPosition(185+(row*100), 512-(120+column*100));
			if( row == 0 && column == 0)
				g.setHover(true);
			stage.addActor(g);
			++row;
			if(row >= 3)
			{
				row = 0;
				column++;
			}
		}
	}
	
	@Override
	public void show() {
		this.enemyTypes = DataProvider.getEnemyTypes();
		this.towerTypes = DataProvider.getTowerTypes();
		enemies = new ArrayList<GuideElement>();
		towers = new ArrayList<GuideElement>();
		
		for(Object value : enemyTypes.values()){
			@SuppressWarnings("unchecked")
			GuideElement enemy = new GuideElement(ElementType.ENEMY, (HashMap<String,String>)value);
			enemies.add(enemy);
		}
		
		for(Object value : towerTypes.values()){
			@SuppressWarnings("unchecked")
			GuideElement tower = new GuideElement(ElementType.TOWER, (HashMap<String,String>)value);
			towers.add(tower);
		}
		
		stage = new Stage();
		stage.setViewport(game.getScreenWidth(), game.getScreenHeight(), false);
		splashImage = new Image(new Texture(Gdx.files.internal("data/guide/background.png")));

		backButton = new GuideButton(com.git.tdgame.guiActor.GuideButton.ButtonType.BACK, 0, 0);
		backButton.setX(95);
		backButton.setY(-1);
		
		enemiesButton = new GuideButton(com.git.tdgame.guiActor.GuideButton.ButtonType.ENEMIES, 0, 0);
		enemiesButton.setWidth(190);
		enemiesButton.setHeight(60);
		enemiesButton.setX(793);
		enemiesButton.setY(440);
		enemiesButton.setHover(true);
		
		towersButton = new GuideButton(com.git.tdgame.guiActor.GuideButton.ButtonType.TOWERS, 0, 0);
		towersButton.setWidth(190);
		towersButton.setHeight(60);
		towersButton.setX(793);
		towersButton.setY(380);
		
		stage.addActor(splashImage);
		stage.addActor(backButton);
		stage.addActor(enemiesButton);
		stage.addActor(towersButton);
		addEnemies();
		addTowers();
		hideTowers();
		Gdx.input.setInputProcessor(this);
		hoveredButton = null;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
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
		Vector2 hover = stage.screenToStageCoordinates(new Vector2(screenX,screenY));
		Actor a = stage.hit(hover.x,hover.y,true);
		
		if(a instanceof GuideButton)
		{
			GuideButton g = (GuideButton) a;
			if(!g.isHovered())
				g.setHover(true);
			
			if(g.equals(enemiesButton))
			{
				towersButton.setHover(false);
			} else if(g.equals(towersButton)){
				enemiesButton.setHover(false);
			}
			hoveredButton = g;
		} else if(a instanceof GuideElement) {
			GuideElement e = (GuideElement) a;
			for(Actor t : stage.getActors())
			{
				if(t instanceof GuideElement)
				{
					GuideElement r = (GuideElement) t;
					if(r.getType() == e.getType())
						r.setHover(false);
				}
			}
			if(!e.isHovered())
				e.setHover(true);
		}
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector2 hover = stage.screenToStageCoordinates(new Vector2(screenX,screenY));
		Actor a = stage.hit(hover.x,hover.y,true);
		
		if(a instanceof GuideButton)
		{
			if(hoveredButton.getType() == com.git.tdgame.guiActor.GuideButton.ButtonType.BACK) {
				game.goToMenuScreen();
			} else if(hoveredButton.getType() == com.git.tdgame.guiActor.GuideButton.ButtonType.ENEMIES) {
				hideTowers();
			} else if(hoveredButton.getType() == com.git.tdgame.guiActor.GuideButton.ButtonType.TOWERS) {
				hideEnemies();
			}
		} else {
			if(hoveredButton != null)
			{
				if(hoveredButton.getType() == com.git.tdgame.guiActor.GuideButton.ButtonType.BACK)
				{
					hoveredButton.setHover(false);
				}
			}
		}
		return false;
	}
	private void hideEnemies() {
		for(Actor a : stage.getActors())
		{
			if(a instanceof GuideElement) {
				GuideElement g = (GuideElement) a;
				if(g.getType() == com.git.tdgame.guiActor.GuideElement.ElementType.ENEMY)
				{
					g.setVisible(false);
				} else if(g.getType() == com.git.tdgame.guiActor.GuideElement.ElementType.TOWER) {
					g.setVisible(true);
				}
			}
		}
	}
	private void hideTowers() {
		for(Actor a : stage.getActors())
		{
			if(a instanceof GuideElement) {
				GuideElement g = (GuideElement) a;
				if(g.getType() == com.git.tdgame.guiActor.GuideElement.ElementType.TOWER)
				{
					g.setVisible(false);
				} else if(g.getType() == com.git.tdgame.guiActor.GuideElement.ElementType.ENEMY)
					g.setVisible(true);
			}
		}
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
