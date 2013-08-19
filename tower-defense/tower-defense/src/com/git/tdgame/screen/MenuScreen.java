package com.git.tdgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.git.tdgame.TRGame;
import com.git.tdgame.guiActor.MenuButton;
import com.git.tdgame.guiActor.MenuButton.ButtonType;


public class MenuScreen implements Screen, InputProcessor{

	public TRGame game;
	private Stage stage;
	private Image splashImage;
	private MenuButton playButton;
	private MenuButton optionsButton;
	private MenuButton encyclopediaButton;
	private MenuButton creditsButton;
	private MenuButton hoveredButton;
	
	public MenuScreen(TRGame game)
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
		splashImage = new Image(new Texture(Gdx.files.internal("data/menu/splash.png")));
		playButton = new MenuButton(ButtonType.PLAY, 0, 0);
		playButton.setX(200);
		playButton.setY(130);

		optionsButton = new MenuButton(ButtonType.OPTIONS, 0, 0);
		optionsButton.setX(200);
		optionsButton.setY(20);
		
		encyclopediaButton = new MenuButton(ButtonType.ENCYCLOPEDIA, 0, 0);
		encyclopediaButton.setX(824-encyclopediaButton.getWidth());
		encyclopediaButton.setY(130);
		
		creditsButton = new MenuButton(ButtonType.CREDITS, 0, 0);
		creditsButton.setX(824-creditsButton.getWidth());
		creditsButton.setY(20);
		
		stage.addActor(splashImage);
		stage.addActor(playButton);
		stage.addActor(optionsButton);
		stage.addActor(encyclopediaButton);
		stage.addActor(creditsButton);
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
	public boolean keyDown(int arg0) {
		if(arg0 == Keys.ENTER)
		{
			Gdx.input.setInputProcessor(null);
			game.goToLevelSelectScreen();
			this.dispose();
		}
		return false;
	}
	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}
	@Override
	public boolean keyUp(int arg0) {
		return false;
	}
	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		return false;
	}
	@Override
	public boolean scrolled(int arg0) {
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		Vector2 hover = stage.screenToStageCoordinates(new Vector2(screenX,screenY));
		Actor a = stage.hit(hover.x,hover.y,true);
		
		if(a instanceof MenuButton)
		{
			MenuButton m = (MenuButton) a;
			m.setHover(true);
			hoveredButton = m;
		}
		return false;
	}
	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2)
	{
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		Vector2 hover = stage.screenToStageCoordinates(new Vector2(screenX,screenY));
		Actor a = stage.hit(hover.x,hover.y,true);
		
		if(a instanceof MenuButton)
		{
			MenuButton m = (MenuButton) a;
			
			if(m.getType() == ButtonType.PLAY)
			{
				game.goToLevelSelectScreen();
				this.dispose();
			} else if(m.getType() == ButtonType.OPTIONS)
			{
				game.goToOptionsScreen();
			} else if(m.getType() == ButtonType.ENCYCLOPEDIA)
			{
				game.goToGuideScreen();
			} else if(m.getType() == ButtonType.CREDITS)
			{
				game.goToCreditsScreen();
			}
		} else {
			if(hoveredButton != null)
			{
				hoveredButton.setHover(false);
			}
		}
		return false;
	}

}
