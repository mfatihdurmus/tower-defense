package com.git.tdgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.git.tdgame.TDGame;
import com.git.tdgame.guiActor.OptionsButton;


public class OptionsScreen implements Screen, InputProcessor{

	public TDGame game;
	private Stage stage;
	private Image splashImage;
	
	private OptionsButton musicButton;
	private OptionsButton soundButton;
	private OptionsButton resetButton;
	private OptionsButton backButton;
	private OptionsButton hoveredButton;
	
	public OptionsScreen(TDGame game)
	{
		this.game = game;
	}
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(1024, 512, false);
	}

	@Override
	public void show() {
		stage = new Stage();
		stage.setViewport(game.getScreenWidth(), game.getScreenHeight(), false);
		splashImage = new Image(new Texture(Gdx.files.internal("data/options/background.png")));

		musicButton = new OptionsButton(com.git.tdgame.guiActor.OptionsButton.ButtonType.MUSIC, 0, 0);
		musicButton.setX(1024/2-(musicButton.getWidth()/2));
		musicButton.setY(512-musicButton.getHeight()-40);
		
		Preferences prefs = Gdx.app.getPreferences("TowerDefenceProperties");
		float volume = prefs.getFloat("volume", 1);
		if(volume == 0)
		{
			musicButton.setHover(true);
		}

		soundButton = new OptionsButton(com.git.tdgame.guiActor.OptionsButton.ButtonType.SOUND, 0, 0);
		soundButton.setX(1024/2-(soundButton.getWidth()/2));
		soundButton.setY(512-soundButton.getHeight()*2-20);
		
		float sound = prefs.getFloat("effectsVolume", 1);
		if(sound == 0)
		{
			soundButton.setHover(true);
		}

		resetButton = new OptionsButton(com.git.tdgame.guiActor.OptionsButton.ButtonType.RESET, 0, 0);
		resetButton.setX(1024/2-(resetButton.getWidth()/2));
		resetButton.setY(512-resetButton.getHeight()*3);
		
		backButton = new OptionsButton(com.git.tdgame.guiActor.OptionsButton.ButtonType.BACK, 0, 0);
		backButton.setX(1024/2-(backButton.getWidth()/2));
		backButton.setY(512-backButton.getHeight()*4+20);
		
		stage.addActor(splashImage);
		stage.addActor(musicButton);
		stage.addActor(soundButton);
		stage.addActor(resetButton);
		stage.addActor(backButton);
		Gdx.input.setInputProcessor(this);
		hoveredButton = null;
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
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector2 hover = stage.screenToStageCoordinates(new Vector2(screenX,screenY));
		Actor a = stage.hit(hover.x,hover.y,true);
		
		if(a instanceof OptionsButton)
		{
			Preferences prefs = Gdx.app.getPreferences("TowerDefenceProperties");
			OptionsButton o = (OptionsButton) a;
			if(o.getType() == com.git.tdgame.guiActor.OptionsButton.ButtonType.MUSIC)
			{
				if(o.isHovered())
				{
					prefs.putFloat("volume", 1);
					o.setHover(false);
				} else {
					prefs.putFloat("volume", 0);
					o.setHover(true);
				}
			} else if(o.getType() == com.git.tdgame.guiActor.OptionsButton.ButtonType.SOUND) {
				if(o.isHovered())
				{
					prefs.putFloat("effectsVolume", 1);
					o.setHover(false);
				} else {
					prefs.putFloat("effectsVolume", 0);
					o.setHover(true);
				}
			} else if(o.getType() == com.git.tdgame.guiActor.OptionsButton.ButtonType.RESET) {
				o.setHover(true);
			} else if(o.getType() == com.git.tdgame.guiActor.OptionsButton.ButtonType.BACK) {
				o.setHover(true);
			}
			prefs.flush();
			hoveredButton = o;
		}
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector2 hover = stage.screenToStageCoordinates(new Vector2(screenX,screenY));
		Actor a = stage.hit(hover.x,hover.y,true);
		
		if(a instanceof OptionsButton)
		{
			OptionsButton o = (OptionsButton) a;
			if(o.getType() == com.git.tdgame.guiActor.OptionsButton.ButtonType.RESET)
			{
				Preferences prefs = Gdx.app.getPreferences("TowerDefenceProperties");
				prefs.putInteger("unlockedLevels", 0);
				game.setUnlockedLevels(0);
				prefs.putFloat("volume", 1);
				prefs.putFloat("effectsVolume", 1);
				musicButton.setHover(false);
				soundButton.setHover(false);
				hoveredButton.setHover(false);
				prefs.flush();
			} else if(hoveredButton.getType() == com.git.tdgame.guiActor.OptionsButton.ButtonType.BACK) {
				game.goToMenuScreen();
			}
		} else {
			if(hoveredButton != null)
			{
				if(hoveredButton.getType() == com.git.tdgame.guiActor.OptionsButton.ButtonType.RESET ||
						hoveredButton.getType() == com.git.tdgame.guiActor.OptionsButton.ButtonType.BACK)
				{
					hoveredButton.setHover(false);
				}
			}
		}
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
