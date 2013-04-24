package com.git.tdgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.git.tdgame.TDGame;


public class MenuScreen implements Screen, InputProcessor{

	public TDGame game;
	private Stage stage;
	private Image splashImage;
	public MenuScreen(TDGame game)
	{
		this.game = game;
	}
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor( 1f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.setViewport(1024, 512, false);

	}

	@Override
	public void show() {
		stage = new Stage();
		splashImage = new Image(new Texture(Gdx.files.internal("data/menu/splash.png")));

		stage.addActor(splashImage);
		Gdx.input.setInputProcessor(this);
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
	public boolean keyDown(int arg0) {
		if(arg0 == Keys.ENTER)
		{
			Gdx.input.setInputProcessor(null);
			game.goToGameScreen();
			this.dispose();
		}
		return false;
	}
	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}
	@Override
	public boolean keyUp(int arg0) {
		return false;
	}
	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		return false;
	}
	@Override
	public boolean scrolled(int arg0) {
		return false;
	}
	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}
	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		return false;
	}
	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3)
	{
		Gdx.input.setInputProcessor(null);
		game.goToGameScreen();
		this.dispose();
		return false;
	}

}
