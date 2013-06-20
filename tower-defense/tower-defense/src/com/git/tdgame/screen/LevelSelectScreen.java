package com.git.tdgame.screen;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.git.tdgame.TDGame;
import com.git.tdgame.data.DataProvider;
import com.git.tdgame.gameActor.level.Footprint;
import com.git.tdgame.gameActor.level.LevelBox;
import com.git.tdgame.gameActor.level.LevelModel;


public class LevelSelectScreen implements Screen, InputProcessor{

	public TDGame game;
	private Stage stage;
	private List<LevelModel> levels;
	private int unlockedLevels;
	private Music levelScreenMusic;
	private float volume;
	
	public LevelSelectScreen(TDGame game, int unlockedLevels)
	{
		levels = DataProvider.getLevels();
		this.game = game;
		this.unlockedLevels = unlockedLevels;
	}
	
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor( 1f, 1f, 1f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
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
		Gdx.input.setInputProcessor(this);

		Image world = new Image(new Texture(Gdx.files.internal("data/levelSelect/world.png")));
		world.setPosition(0, 0);
		world.setSize(stage.getWidth(), stage.getHeight());
		stage.addActor(world);
		int levelIndex = 0;
		int boxWidth = (int)stage.getWidth() / 12;
		int boxHeight = (int)stage.getHeight()/12;
		int gapX = (int)((stage.getWidth() - 3 * boxWidth) / 11.7);
		int gapY = (int)((stage.getHeight() - 3 * boxHeight) / 11.5);
		int footprintRadius = boxWidth/5;
		
		/*Level1*/
		levels.get(levelIndex).setLevelIndex(levelIndex);
		LevelBox l = new LevelBox(4 * gapX, 3 * gapY, boxWidth, boxWidth, levels.get(levelIndex), stage.getHeight());
		Footprint f = new Footprint(6 * gapX, 4 * gapY, footprintRadius, footprintRadius, stage.getHeight(), levelIndex);
		
		if(levelIndex > unlockedLevels)
		{
			l.setTouchable(Touchable.disabled);
			l.setActive(false);
		}
		if(levelIndex > unlockedLevels-1)
		{
			f.setActive(false);
		}
		
		stage.addActor(l);
		stage.addActor(f);
		
		++levelIndex;

		/*Level2*/
		levels.get(levelIndex).setLevelIndex(levelIndex);
		l = new LevelBox(7 * gapX, 3.5f * gapY, boxWidth, boxWidth, levels.get(levelIndex), stage.getHeight());
		f = new Footprint(9 * gapX, 3.5f * gapY, footprintRadius, footprintRadius, stage.getHeight(), levelIndex);
		
		if(levelIndex > unlockedLevels)
		{
			l.setTouchable(Touchable.disabled);
			l.setActive(false);
		}
		if(levelIndex > unlockedLevels-1)
		{
			f.setActive(false);
		}
		
		stage.addActor(l);
		stage.addActor(f);
		
		++levelIndex;

		/*Level3*/
		levels.get(levelIndex).setLevelIndex(levelIndex);
		l = new LevelBox(10 * gapX, 2 * gapY, boxWidth, boxWidth, levels.get(levelIndex), stage.getHeight());
		f = new Footprint(10.7f * gapX, 4.6f * gapY, footprintRadius, footprintRadius, stage.getHeight(), levelIndex);
		
		if(levelIndex > unlockedLevels)
		{
			l.setTouchable(Touchable.disabled);
			l.setActive(false);
		}
		if(levelIndex > unlockedLevels-1)
		{
			f.setActive(false);
		}
		
		stage.addActor(l);
		stage.addActor(f);
		
		++levelIndex;

		/*Level4*/
		levels.get(levelIndex).setLevelIndex(levelIndex);
		l = new LevelBox(10.5f * gapX, 5.5f * gapY, boxWidth, boxWidth, levels.get(levelIndex), stage.getHeight());
		f = new Footprint(10 * gapX, 7.6f * gapY, footprintRadius, footprintRadius, stage.getHeight(), levelIndex);
		
		if(levelIndex > unlockedLevels)
		{
			l.setTouchable(Touchable.disabled);
			l.setActive(false);
		}
		if(levelIndex > unlockedLevels-1)
		{
			f.setActive(false);
		}
		
		stage.addActor(l);
		stage.addActor(f);
		
		++levelIndex;

		/*Level5*/
		levels.get(levelIndex).setLevelIndex(levelIndex);
		l = new LevelBox(8.5f * gapX, 8 * gapY, boxWidth, boxWidth, levels.get(levelIndex), stage.getHeight());
		f = new Footprint(7.7f * gapX, 7.7f * gapY, footprintRadius, footprintRadius, stage.getHeight(), levelIndex);
		
		if(levelIndex > unlockedLevels)
		{
			l.setTouchable(Touchable.disabled);
			l.setActive(false);
		}
		if(levelIndex > unlockedLevels-1)
		{
			f.setActive(false);
		}
		
		stage.addActor(l);
		stage.addActor(f);
		
		++levelIndex;

		/*Level6*/
		levels.get(levelIndex).setLevelIndex(levelIndex);
		l = new LevelBox(5.7f * gapX, 6.5f * gapY, boxWidth, boxWidth, levels.get(levelIndex), stage.getHeight());
		f = new Footprint(5.1f * gapX, 8.1f * gapY, footprintRadius, footprintRadius, stage.getHeight(), levelIndex);
		
		if(levelIndex > unlockedLevels)
		{
			l.setTouchable(Touchable.disabled);
			l.setActive(false);
		}
		if(levelIndex > unlockedLevels-1)
		{
			f.setActive(false);
		}
		
		stage.addActor(l);
		stage.addActor(f);
		
		++levelIndex;

		/*Level7*/
		levels.get(levelIndex).setLevelIndex(levelIndex);
		l = new LevelBox(3.5f * gapX, 8.5f * gapY, boxWidth, boxWidth, levels.get(levelIndex), stage.getHeight());
		f = new Footprint(5.7f * gapX, 10.3f * gapY, footprintRadius, footprintRadius, stage.getHeight(), levelIndex);
		Footprint f2 = new Footprint(6.6f * gapX, 10.9f * gapY, footprintRadius, footprintRadius, stage.getHeight(), levelIndex);
		
		if(levelIndex > unlockedLevels)
		{
			l.setTouchable(Touchable.disabled);
			l.setActive(false);
		}
		if(levelIndex > unlockedLevels-1)
		{
			f.setActive(false);
			f2.setActive(false);
		}
		
		stage.addActor(l);
		stage.addActor(f);
		stage.addActor(f2);
		
		++levelIndex;

		/*Level8*/
		levels.get(levelIndex).setLevelIndex(levelIndex);
		l = new LevelBox(7.5f * gapX, 11 * gapY, boxWidth, boxWidth, levels.get(levelIndex), stage.getHeight());
		f = new Footprint(9.7f * gapX, 11.3f * gapY, footprintRadius, footprintRadius, stage.getHeight(), levelIndex);
		f2 = new Footprint(10.6f * gapX, 10.8f * gapY, footprintRadius, footprintRadius, stage.getHeight(), levelIndex);
		
		if(levelIndex > unlockedLevels)
		{
			l.setTouchable(Touchable.disabled);
			l.setActive(false);
		}
		if(levelIndex > unlockedLevels-1)
		{
			f.setActive(false);
			f2.setActive(false);
		}
		
		stage.addActor(l);
		stage.addActor(f);
		stage.addActor(f2);
		++levelIndex;

		/*Level9*/
		levels.get(levelIndex).setLevelIndex(levelIndex);
		l = new LevelBox(11.5f * gapX, 9 * gapY, boxWidth, boxWidth, levels.get(levelIndex), stage.getHeight());
		
		if(levelIndex > unlockedLevels)
		{
			l.setTouchable(Touchable.disabled);
			l.setActive(false);
		}
		stage.addActor(l);
		
		++levelIndex;

		/* Standard alignment
		for(int j = 1; j< (levels.size()-1)/3+1; j++)
		{
			for(int i = 0; i < (((j+1)*3 > levels.size() ? levels.size()%3 : 3)); ++i)
			{
				levels.get(j*3+i).setLevelIndex(levelIndex);
				l = new LevelBox(i * (boxWidth + gapX)+gapX, j * (boxHeight + gapY)+gapY, boxWidth, boxHeight, levels.get(j*3+i), stage.getHeight());
				
				if(levelIndex > unlockedLevels)
				{
					l.setTouchable(Touchable.disabled);
					l.setActive(false);
				}
				
				stage.addActor(l);
				++levelIndex;
			}
		}*/
		
		Preferences prefs = Gdx.app.getPreferences("TowerDefenceProperties");
		volume = prefs.getFloat("volume", 1);
		
		levelScreenMusic = Gdx.audio.newMusic(Gdx.files.internal("data/levelSelect/levelScreenMusic.wav"));
		levelScreenMusic.setLooping(true);
		levelScreenMusic.setVolume(volume);
		levelScreenMusic.play();
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
		levelScreenMusic.dispose();
	}
	@Override
	public boolean keyDown(int keycode) {
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
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		Actor a = stage.hit(screenX, screenY, true); 
		if(a instanceof LevelBox)
		{
			LevelBox l = (LevelBox) a;
			game.setSelectedLevel(l.getLevel());
			levelScreenMusic.pause();
			game.goToGameScreen();
			this.dispose();
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
