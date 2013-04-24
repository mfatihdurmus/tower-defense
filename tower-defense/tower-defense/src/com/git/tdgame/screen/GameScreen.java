package com.git.tdgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.git.tdgame.TDGame;
import com.git.tdgame.TiledMapHelper;
import com.git.tdgame.gameActor.BasicTower;
import com.git.tdgame.gameActor.Enemy;


public class GameScreen implements Screen{

	// To access game functions
	private TDGame game;

	// Stage
	private Stage stage;

	// Map variables
	private TiledMapHelper tiledMapHelper;
	private Array<Vector2> path;
	private Vector2 tileSize;
	private int mapHeight;
	
	// Wave variables
	private float spawnTime = 0;
	private int spawnLeft = 10;
	private final float spawnDelay = 0.7f; 
	
	public GameScreen(TDGame game)
	{
		this.game = game;
	}
	
	@Override
	public void render(float delta)
	{
		// Clear screen
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // Map render
		tiledMapHelper.render();

		// Stage update
        stage.act(delta);
        stage.draw();
        
        // Spawn enemies
        if(spawnLeft > 0)
        {
        	spawnTime += delta;
            if(spawnTime > spawnDelay)
            {
            	spawnTime = 0;
                --spawnLeft;
        		
                Enemy e = new Enemy(path, tileSize, mapHeight);
                e.setName(""+spawnLeft);
                stage.addActor(e);
                
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
		// Map load
		tiledMapHelper = new TiledMapHelper();
		tiledMapHelper.setPackerDirectory("data/packer");
		tiledMapHelper.loadMap("data/world/level1/level.tmx");
		tileSize = new Vector2(tiledMapHelper.getMap().tileWidth,tiledMapHelper.getMap().tileHeight);
		path = tiledMapHelper.getPath();
		mapHeight = tiledMapHelper.getMap().height;
		
		// Camera configuration
		tiledMapHelper.prepareCamera(game.getScreenWidth(), game.getScreenHeight());
		tiledMapHelper.getCamera().viewportWidth = tiledMapHelper.getWidth();
		tiledMapHelper.getCamera().viewportHeight = tiledMapHelper.getHeight();
		tiledMapHelper.getCamera().position.x = tiledMapHelper.getWidth()/2;
		tiledMapHelper.getCamera().position.y = tiledMapHelper.getHeight()/2;
		tiledMapHelper.getCamera().update();

		// Stage configuration
		stage = new Stage();
		stage.setViewport(tiledMapHelper.getWidth(), tiledMapHelper.getHeight(), false);
		//stage.addActor(new Tower(new Vector2(128,64),tileSize,mapHeight));
		
		stage.addActor(new BasicTower(new Vector2(16*tileSize.x,16*tileSize.y),tileSize,mapHeight));
		stage.addActor(new BasicTower(new Vector2(10*tileSize.x,16*tileSize.y),tileSize,mapHeight));
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
