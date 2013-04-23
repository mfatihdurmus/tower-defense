package com.git.tdgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.git.tdgame.TDGame;
import com.git.tdgame.TiledMapHelper;
import com.git.tdgame.gameActor.Ball;


public class GameScreen implements Screen{

	private TiledMapHelper tiledMapHelper;
	private TDGame game;
	private Stage stage;
	
	// Wave variables
	private long spawnTime = 0;
	private int spawnLeft = 10;
	
	public GameScreen(TDGame game)
	{
		this.game = game;
	}
	
	@Override
	public void render(float delta)
	{
        Gdx.gl.glClearColor( 1f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        
		tiledMapHelper.getCamera().position.x = 512;
		tiledMapHelper.getCamera().position.y = 512;
		tiledMapHelper.getCamera().update();
		tiledMapHelper.render();
		
        stage.act(delta);
        stage.draw();
        
        // Spawn actors
        if(spawnLeft > 0)
        {
            if(System.currentTimeMillis() - spawnTime > 2000)
            {
            	spawnTime = System.currentTimeMillis();
        		Ball newBall = new Ball(tiledMapHelper.getPath());
        		
                stage.addActor(newBall);
                --spawnLeft;
            }
        }
	}

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void show()
	{
		stage = new Stage();
		
		tiledMapHelper = new TiledMapHelper();
		tiledMapHelper.setPackerDirectory("data/packer");
		tiledMapHelper.loadMap("data/world/level1/level.tmx");
		tiledMapHelper.prepareCamera(game.getScreenWidth(), game.getScreenHeight());
		tiledMapHelper.getCamera().viewportWidth = 1024;
		tiledMapHelper.getCamera().viewportHeight = 1024;
		tiledMapHelper.getCamera().update();
		
		stage.setViewport(1024, 1024, false);
		
        spawnTime = System.currentTimeMillis();
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
