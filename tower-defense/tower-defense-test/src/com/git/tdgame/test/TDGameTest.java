package com.git.tdgame.test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import android.test.ActivityInstrumentationTestCase2;

import com.badlogic.gdx.Screen;
import com.git.tdgame.MainActivity;
import com.git.tdgame.TDGame;
import com.git.tdgame.screen.LevelSelectScreen;
import com.git.tdgame.screen.MenuScreen;

public class TDGameTest extends ActivityInstrumentationTestCase2<MainActivity> {

	TDGame game;
	public TDGameTest() {
		super("com.git.tdgame", MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		game = getActivity().getGameInstance();
	}
	
	public void testCreate(){
		game.create();
		
		Screen currentScreen = game.getScreen();
		
		MenuScreen menuScreen = (MenuScreen)currentScreen;
		
		menuScreen.touchUp(20, 20, 20, 20);
		
		assertThat(game.getScreen(), instanceOf(LevelSelectScreen.class));
		
	}

}
