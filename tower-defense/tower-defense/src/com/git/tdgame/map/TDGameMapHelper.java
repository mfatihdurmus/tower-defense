package com.git.tdgame.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class TDGameMapHelper
{
	private FileHandle packFileDirectory;
	private OrthographicCamera camera;

	private TileAtlas tileAtlas;
	private TileMapRenderer tileMapRenderer;
	private TiledMap map;
	
	private final int START_POINT = 6;
	private final int END_POINT = 7;
	private final int PATH_LAYER = 1;
	
	public int[][] getTiles(int layer)
	{
		if(layer < map.layers.size())
			return map.layers.get(layer).tiles;
		return null;
	}

	public int getSTART_POINT() {
		return START_POINT;
	}

	public int getEND_POINT() {
		return END_POINT;
	}

	public int getPATH_LAYER() {
		return PATH_LAYER;
	}

	public Array<Vector2> getStartPoints()
	{
		Array<Vector2> startPoints = new Array<Vector2>();
		int[][] pathTiles = map.layers.get(PATH_LAYER).tiles;
		
		int y = 0;
		for(int[] col : pathTiles)
		{
			int x = 0;
			for(int row : col)
			{
				if(row == START_POINT)
					startPoints.add(new Vector2(x,y));
				++x;
			}
			++y;
		}
		return startPoints;
	}
	
	public Array<Vector2> getPath(Vector2 startPoint)
	{
		// TO DO : Backtracking for branching paths
		Array<Vector2> path = new Array<Vector2>();
		
		int[][] pathTiles = map.layers.get(PATH_LAYER).tiles;
		
		Vector2 endPoint = getEndPoint();
		int x = (int)startPoint.x;
		int y = (int)startPoint.y;
		int fX = (int)endPoint.x;
		int fY = (int)endPoint.y;
		
		int direction = 0;
		while(x != fX || y != fY)
		{
			if(y+1 < 32 && pathTiles[y+1][x] != 0 && direction != 2)
			{
				if(direction != 1)
				{
					Vector2 newVector = new Vector2(x*map.tileWidth,y*map.tileHeight);
					path.add(newVector);
					direction = 1;
				}
				y++;
			}
			else if(y-1 >= 0 && pathTiles[y-1][x] != 0 && direction != 1)
			{
				if(direction != 2)
				{
					Vector2 newVector = new Vector2(x*map.tileWidth,y*map.tileHeight);
					path.add(newVector);
					direction = 2;
				}
				y--;
		    }        
			else if(x+1 < 32 && pathTiles[y][x+1] != 0 && direction != 4)
			{
				if(direction != 3)
				{
					Vector2 newVector = new Vector2(x*map.tileWidth,y*map.tileHeight);
					path.add(newVector);
					direction = 3;
				}
				x++;
			} 
			else if(x-1 >= 0 && pathTiles[y][x-1] != 0 && direction != 3)
			{
				if(direction != 4)
				{
					Vector2 newVector = new Vector2(x*map.tileWidth,y*map.tileHeight);
					path.add(newVector);
					direction = 4;
				}
				x--;
			} else {
				// Dead end
				return path;
			}
		}
		path.add(new Vector2(fX*map.tileWidth,fY*map.tileHeight));
		return path;
	}
	
	public void render()
	{
		tileMapRenderer.render(getCamera());
	}

	/**
	 * Get the height of the map in pixels
	 * 
	 * @return y
	 */
	public int getHeight() {
		return map.height * map.tileHeight;
	}

	/**
	 * Get the width of the map in pixels
	 * 
	 * @return x
	 */
	public int getWidth() {
		return map.width * map.tileWidth;
	}

	public Vector2 getEndPoint()
	{
		int[][] pathTiles = map.layers.get(PATH_LAYER).tiles;
		
		int y = 0;
		for(int[] col : pathTiles)
		{
			int x = 0;
			for(int row : col)
			{
				if(row == END_POINT)
					return new Vector2(x,y);
				++x;
			}
			++y;
		}
		return null;
	}



	/**
	 * Get the map, useful for iterating over the set of tiles found within
	 * 
	 * @return TiledMap
	 */
	public TiledMap getMap() {
		return map;
	}

	/**
	 * Calls dispose on all disposable resources held by this object.
	 */
	public void dispose() {
		tileAtlas.dispose();
		tileMapRenderer.dispose();
	}

	/**
	 * Sets the directory that holds the game's pack files and tile sets.
	 * 
	 * @param packDirectory
	 */
	public void setPackerDirectory(String packDirectory) {
		packFileDirectory = Gdx.files.internal(packDirectory);
	}

	/**
	 * Loads the requested tmx map file in to the helper.
	 * 
	 * @param tmxFile
	 */
	public void loadMap(String tmxFile) {
		if (packFileDirectory == null) {
			throw new IllegalStateException("loadMap() called out of sequence");
		}

		map = TiledLoader.createMap(Gdx.files.internal(tmxFile));
		
		tileAtlas = new TileAtlas(map, packFileDirectory);

		tileMapRenderer = new TileMapRenderer(map, tileAtlas, 16, 16);
	}

	/**
	 * Prepares the helper's camera object for use.
	 * 
	 * @param screenWidth
	 * @param screenHeight
	 */
	public void prepareCamera(int screenWidth, int screenHeight) {
		camera = new OrthographicCamera(screenWidth, screenHeight);

		camera.position.set(0, 0, 0);
	}

	/**
	 * Returns the camera object created for viewing the loaded map.
	 * 
	 * @return OrthographicCamera
	 */
	public OrthographicCamera getCamera() {
		if (camera == null) {
			throw new IllegalStateException(
					"getCamera() called out of sequence");
		}
		return camera;
	}
}
