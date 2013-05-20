package com.git.tdgame.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.git.tdgame.TDGame;
import com.git.tdgame.gameActor.LevelBox;


public class LevelSelectScreen implements Screen, InputProcessor{

	public TDGame game;
	private Stage stage;
	private int levelSize = 2;
	private ArrayList<String> levels = new ArrayList<String>();
	
	public LevelSelectScreen(TDGame game)
	{
		this.game = game;
		int ctr = 1;
		levels.add("data/world/level"+ctr+"/level.tmx");
		ctr++;
		levels.add("data/world/level"+ctr+"/level.tmx");
	}
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor( 1f, 1f, 1f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.setViewport(width, height, true);

	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(this);
		int width = (int)stage.getWidth() / (levelSize+1);
		int height = (int)stage.getHeight() / (levelSize+1);
		
		for(int ctr = 0; ctr < levelSize; ++ctr)
		{
			LevelBox l = new LevelBox(ctr * stage.getWidth() / levelSize + 10, stage.getHeight()/ (levelSize+1), width, height,levels.get(ctr));
			stage.addActor(l);
		}
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
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		Actor a = stage.hit(screenX, screenY, false); 
		if(a instanceof LevelBox)
		{
			LevelBox l = (LevelBox) a;
			game.setSelectedMap(l.getPath());
			game.goToGameScreen();
			this.dispose();
			Gdx.input.setInputProcessor(null);
		}
		return false;
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
