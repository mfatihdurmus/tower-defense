package com.git.tdgame;

import com.badlogic.gdx.Game;
import com.git.tdgame.gameActor.level.LevelModel;
import com.git.tdgame.screen.GameScreen;
import com.git.tdgame.screen.LevelSelectScreen;
import com.git.tdgame.screen.MenuScreen;

public class TDGame extends Game {
	private MenuScreen menuScreen;
	private LevelSelectScreen levelSelectScreen;
	private GameScreen gameScreen;

	private int screenWidth = 0;
	private int screenHeight = 0;
	private LevelModel selectedLevel;

	public TDGame()
	{
	}

	public TDGame(int width, int height)
	{
		super();
		screenWidth = width;
		screenHeight = height;
	}

	@Override
	public void create()
	{
		menuScreen = new MenuScreen(this);
		
		setScreen(menuScreen);
	}
	
	public void goToGameScreen()
	{
		gameScreen = new GameScreen(this, selectedLevel);
		setScreen(gameScreen);
	}
	
	public void setSelectedLevel(LevelModel selectedLevel)
	{
		this.selectedLevel = selectedLevel;
	}

	public void goToMenuScreen()
	{
		setScreen(menuScreen);
	}

	public void goToLevelSelectScreen()
	{
		levelSelectScreen = new LevelSelectScreen(this);
		setScreen(levelSelectScreen);
	}

	@Override
	public void resume()
	{
	}

	@Override
	public void render()
	{
		super.render();
	}

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void dispose()
	{
	}
	
	public int getScreenWidth()
	{
		return screenWidth;
	}
	
	public int getScreenHeight()
	{
		return screenHeight;
	}
}