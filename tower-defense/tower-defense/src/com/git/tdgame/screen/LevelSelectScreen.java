package com.git.tdgame.screen;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.git.tdgame.TDGame;
import com.git.tdgame.data.DataProvider;
import com.git.tdgame.gameActor.level.LevelBox;
import com.git.tdgame.gameActor.level.LevelModel;


public class LevelSelectScreen implements Screen, InputProcessor{

	public TDGame game;
	private Stage stage;
	private List<LevelModel> levels;
	private int unlockedLevels;
	
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

		Image world = new Image(new Texture(Gdx.files.internal("data/menu/world.png")));
		world.setPosition(0, 0);
		world.setSize(stage.getWidth(), stage.getHeight());
		stage.addActor(world);
		int levelIndex = 0;
		int boxWidth = (int)stage.getWidth() / 4;
		int boxHeight = (int)stage.getHeight()/4;
		int gapX = (int)((stage.getWidth() - 3 * boxWidth) / 4);
		int gapY = (int)((stage.getHeight() - 3 * boxHeight) / 4);
		for(int j = 0; j< (levels.size()-1)/3+1; j++)
		{
			for(int i = 0; i < (((j+1)*3 > levels.size() ? levels.size()%3 : 3)); ++i)
			{
				LevelBox l = new LevelBox(i * (boxWidth + gapX)+gapX, j * (boxHeight + gapY)+gapY, boxWidth, boxHeight, levels.get(j*3+i), stage.getHeight());
				
				if(levelIndex > unlockedLevels)
				{
					l.setTouchable(Touchable.disabled);
					l.setActive(false);
				}
				
				stage.addActor(l);
				++levelIndex;
			}
		}
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
