package com.git.tdgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.git.tdgame.TDGame;


public class MenuScreen implements Screen{

	public TDGame game;
	private Stage stage;
	public MenuScreen(TDGame game)
	{
		this.game = game;
	}
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor( 1f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        MoveToAction action = new MoveToAction();
        action.setPosition(0, 1000);
        action.setDuration(100);
        stage.act(delta);
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

}
