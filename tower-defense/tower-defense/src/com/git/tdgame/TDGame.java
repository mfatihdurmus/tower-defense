package com.git.tdgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.git.tdgame.gameActor.level.LevelModel;
import com.git.tdgame.screen.GameScreen;
import com.git.tdgame.screen.LevelSelectScreen;
import com.git.tdgame.screen.MenuScreen;
import com.git.tdgame.screen.OptionsScreen;

public class TDGame extends Game {
	private MenuScreen menuScreen;
	private LevelSelectScreen levelSelectScreen;
	private GameScreen gameScreen;
	private OptionsScreen optionsScreen;

	private int screenWidth = 0;
	private int screenHeight = 0;
	private LevelModel selectedLevel;
	private int unlockedLevels = 0;

	public TDGame()
	{
		super();
	}

	
	public void setUnlockedLevels(int unlockedLevels) {
		this.unlockedLevels = unlockedLevels;
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
		Preferences prefs = Gdx.app.getPreferences("TowerDefenceProperties");
		this.unlockedLevels = prefs.getInteger("unlockedLevels", 0);
		prefs.getFloat("volume", 1);
	}
	
	public void goToGameScreen()
	{
		gameScreen = new GameScreen(this, selectedLevel);
		setScreen(gameScreen);
	}
	
	public void goToOptionsScreen()
	{
		optionsScreen = new OptionsScreen(this);
		setScreen(optionsScreen);
	}
	
	public void setSelectedLevel(LevelModel selectedLevel)
	{
		this.selectedLevel = selectedLevel;
	}

	public void goToMenuScreen()
	{
		setScreen(menuScreen);
	}
	
	public void unlockLevels(int lastLevelNumber)
	{
		unlockedLevels = lastLevelNumber;
		
		Preferences prefs = Gdx.app.getPreferences("TowerDefenceProperties");
		prefs.putInteger("unlockedLevels", unlockedLevels);
		prefs.flush();
	}

	
	public int getUnlockedLevels() {
		return unlockedLevels;
	}

	public void goToLevelSelectScreen()
	{
		levelSelectScreen = new LevelSelectScreen(this, unlockedLevels);
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