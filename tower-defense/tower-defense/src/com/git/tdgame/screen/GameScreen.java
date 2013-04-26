package com.git.tdgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.git.tdgame.TDGame;
import com.git.tdgame.gameActor.BasicTower;
import com.git.tdgame.gameActor.Enemy;
import com.git.tdgame.map.TDGameMapHelper;


public class GameScreen implements Screen{

	// To access game functions
	private TDGame game;

	// Stage
	private Stage stage;

	// Map variables
	private TDGameMapHelper tdGameMapHelper;
	private Array<Array<Vector2>> paths;
	private Vector2 tileSize;
	
	// Wave variables
	private float spawnTime = 0;
	private int spawnLeft = 10;
	private final float spawnDelay = 0.5f;
	
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
		tdGameMapHelper.render();

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
        		
                // TO DO : Spawn from selected path
                for(Array<Vector2> path : paths)
                {
                	Enemy e = new Enemy(path);
	                e.setName(""+spawnLeft);
	                stage.addActor(e);
                }
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
		tdGameMapHelper = new TDGameMapHelper();
		tdGameMapHelper.setPackerDirectory("data/packer");
		tdGameMapHelper.loadMap("data/world/level1/level.tmx");
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
		
		stage.addActor(new BasicTower(new Vector2(16*tileSize.x,16*tileSize.y)));
		stage.addActor(new BasicTower(new Vector2(10*tileSize.x,16*tileSize.y)));
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
