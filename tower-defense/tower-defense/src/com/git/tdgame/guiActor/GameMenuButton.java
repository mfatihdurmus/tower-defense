package com.git.tdgame.guiActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameMenuButton extends Actor {
	
	private final String quitButtonFile = "data/game/gui/quit.png";
	private final String restartButtonFile = "data/game/gui/restart.png";
	private final String resumeButtonFile = "data/game/gui/resume.png";
	
	public enum ButtonType { QUIT, RESUME, RESTART };
	
	private Texture texture;
	private Sprite sprite;
	private ButtonType type;
	
	public GameMenuButton( ButtonType type ) {
		this.type = type;
		
		if(type == ButtonType.QUIT )
			texture = new Texture(Gdx.files.internal(quitButtonFile));
		else if(type == ButtonType.RESTART)
			texture = new Texture(Gdx.files.internal(restartButtonFile));
		else if(type == ButtonType.RESUME)
			texture = new Texture(Gdx.files.internal(resumeButtonFile));
		
		setHeight(texture.getHeight());
		setWidth(texture.getWidth());
        sprite = new Sprite(texture);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(sprite,getX(),getY()+getHeight(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,-1,0);
		super.draw(batch, parentAlpha);
	}

	public ButtonType getType() {
		return type;
	}
}
