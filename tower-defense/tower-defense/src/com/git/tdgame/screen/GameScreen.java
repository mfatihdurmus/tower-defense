package com.git.tdgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.git.tdgame.TDGame;
import com.git.tdgame.TiledMapHelper;
import com.git.tdgame.gameActor.Ball;


public class GameScreen implements Screen{

	private TiledMapHelper tiledMapHelper;
	private TDGame game;
	private Stage stage;
	private long spawnTime = 0;
	
	public GameScreen(TDGame game)
	{
		this.game = game;
	}
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor( 1f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        
		tiledMapHelper.getCamera().position.x = 512;
		tiledMapHelper.getCamera().position.y = 512;
		tiledMapHelper.getCamera().update();
		tiledMapHelper.render();
        stage.act(delta);
        stage.draw();
        if(System.currentTimeMillis() - spawnTime > 2000)
        {
        	spawnTime = System.currentTimeMillis();
    		Vector2 startPoint = tiledMapHelper.getStartPoint();
    		Vector2 endPoint = tiledMapHelper.getEndPoint();
    		Ball newBall = new Ball((int)startPoint.x, (int)startPoint.y,tiledMapHelper.getPathTiles(),(int)endPoint.x,(int)endPoint.y);
    		
            stage.addActor(newBall);
        }
	}

	@Override
	public void resize(int width, int height)
	{
		stage.setViewport(1024, 1024, false);
		tiledMapHelper.getCamera().viewportWidth = 1024;
		tiledMapHelper.getCamera().viewportHeight = 1024;
		tiledMapHelper.getCamera().update();
	}

	@Override
	public void show() {
		stage = new Stage();
		tiledMapHelper = new TiledMapHelper();

		tiledMapHelper.setPackerDirectory("data/packer");

		tiledMapHelper.loadMap("data/world/level1/level.tmx");
		
		tiledMapHelper.prepareCamera(game.getScreenWidth(), game.getScreenHeight());
		
		Vector2 startPoint = tiledMapHelper.getStartPoint();
		Vector2 endPoint = tiledMapHelper.getEndPoint();
		Ball ball = new Ball((int)startPoint.x, (int)startPoint.y,tiledMapHelper.getPathTiles(),(int)endPoint.x,(int)endPoint.y);
		
        stage.addActor(ball);
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
