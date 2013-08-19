package com.git.tdgame.guiActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class OptionsButton extends Actor
{
	private final String musicButtonFile = "data/options/music.png";
	private final String musicButtonFileClicked = "data/options/musicClicked.png";
	private final String soundButtonFile = "data/options/sound.png";
	private final String soundButtonFileClicked = "data/options/soundClicked.png";
	private final String resetButtonFile = "data/options/reset.png";
	private final String resetButtonFileClicked = "data/options/resetClicked.png";
	private final String backButtonFile = "data/options/back.png";
	private final String backButtonFileClicked = "data/options/backClicked.png";
	
	public enum ButtonType { MUSIC, SOUND, RESET, BACK};
	
	private Texture texture;
	private Texture textureClicked;
	private Sprite sprite;
	private Sprite spriteClicked;
	private ButtonType type;
	private boolean isHovered;
	
	public OptionsButton( ButtonType type, int x, int y ) {
		this.type = type;
		setX(x);
		setY(y);
		
		if(type == ButtonType.MUSIC ) {
			texture = new Texture(Gdx.files.internal(musicButtonFile));
			textureClicked = new Texture(Gdx.files.internal(musicButtonFileClicked));
		} else if(type == ButtonType.SOUND) {
			texture = new Texture(Gdx.files.internal(soundButtonFile));
			textureClicked = new Texture(Gdx.files.internal(soundButtonFileClicked));
		} else if(type == ButtonType.RESET) {
			texture = new Texture(Gdx.files.internal(resetButtonFile));
			textureClicked = new Texture(Gdx.files.internal(resetButtonFileClicked));
		} else if(type == ButtonType.BACK) {
			texture = new Texture(Gdx.files.internal(backButtonFile));
			textureClicked = new Texture(Gdx.files.internal(backButtonFileClicked));
		}
		
		setHeight(texture.getHeight());
		setWidth(texture.getWidth());
        sprite = new Sprite(texture);
        spriteClicked = new Sprite(textureClicked);
	}
	
	public void changeTexture(String newTexture, String newTextureClicked, int newHeight, int newWidth)
	{
		texture = new Texture(Gdx.files.internal(newTexture));
		textureClicked = new Texture(Gdx.files.internal(newTextureClicked));
		setHeight(newHeight);
		setWidth(newWidth);
        sprite = new Sprite(texture);
        spriteClicked = new Sprite(textureClicked);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		if(!isHovered)
		{
			batch.draw(sprite,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,1,0);
		} else {
			batch.draw(spriteClicked,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,1,0);
		}
		super.draw(batch, parentAlpha);
	}

	public ButtonType getType() {
		return type;
	}

	public void setHover(boolean b) {
		isHovered = b;
	}

	public boolean isHovered() {
		return isHovered;
	}
}
