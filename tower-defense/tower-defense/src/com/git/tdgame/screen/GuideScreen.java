package com.git.tdgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.git.tdgame.TRGame;
import com.git.tdgame.guiActor.GuideButton;


public class GuideScreen implements Screen, InputProcessor{

	public TRGame game;
	private Stage stage;
	private Image splashImage;
	
	private GuideButton backButton;
	private GuideButton enemiesButton;
	private GuideButton towersButton;
	private GuideButton hoveredButton;
	
	public GuideScreen(TRGame game)
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
		splashImage = new Image(new Texture(Gdx.files.internal("data/guide/background.png")));

		backButton = new GuideButton(com.git.tdgame.guiActor.GuideButton.ButtonType.BACK, 0, 0);
		backButton.setX(95);
		backButton.setY(-1);
		
		enemiesButton = new GuideButton(com.git.tdgame.guiActor.GuideButton.ButtonType.ENEMIES, 0, 0);
		enemiesButton.setWidth(190);
		enemiesButton.setHeight(60);
		enemiesButton.setX(793);
		enemiesButton.setY(440);
		enemiesButton.setHover(true);
		
		towersButton = new GuideButton(com.git.tdgame.guiActor.GuideButton.ButtonType.TOWERS, 0, 0);
		towersButton.setWidth(190);
		towersButton.setHeight(60);
		towersButton.setX(793);
		towersButton.setY(380);
		
		stage.addActor(splashImage);
		stage.addActor(backButton);
		stage.addActor(enemiesButton);
		stage.addActor(towersButton);
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
		
		if(a instanceof GuideButton)
		{
			GuideButton g = (GuideButton) a;
			boolean isSelected;
			if(!g.isHovered())
			{
				g.setHover(true);
				isSelected = true;
			} else {
				g.setHover(false);
				isSelected = false;
			}
			if(g.equals(enemiesButton))
			{
				towersButton.setHover(!isSelected);
			} else {
				enemiesButton.setHover(!isSelected);
			}
			hoveredButton = g;
		}
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector2 hover = stage.screenToStageCoordinates(new Vector2(screenX,screenY));
		Actor a = stage.hit(hover.x,hover.y,true);
		
		if(a instanceof GuideButton)
		{
			if(hoveredButton.getType() == com.git.tdgame.guiActor.GuideButton.ButtonType.BACK) {
				game.goToMenuScreen();
			}
		} else {
			if(hoveredButton != null)
			{
				if(hoveredButton.getType() == com.git.tdgame.guiActor.GuideButton.ButtonType.BACK)
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
