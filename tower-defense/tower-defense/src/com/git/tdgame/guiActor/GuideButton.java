package com.git.tdgame.guiActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GuideButton extends Actor
{
	private final String enemiesButtonFile = "data/guide/enemies.png";
	private final String enemiesButtonFileClicked = "data/guide/enemiesClicked.png";
	private final String towersButtonFile = "data/guide/towers.png";
	private final String towersButtonFileClicked = "data/guide/towersClicked.png";
	private final String backButtonFile = "data/guide/back.png";
	private final String backButtonFileClicked = "data/guide/backClicked.png";
	
	public enum ButtonType { ENEMIES, TOWERS, BACK};
	
	private Texture texture;
	private Texture textureClicked;
	private Sprite sprite;
	private Sprite spriteClicked;
	private ButtonType type;
	private boolean isHovered;
	
	public GuideButton( ButtonType type, int x, int y ) {
		this.type = type;
		setX(x);
		setY(y);
		
		if(type == ButtonType.ENEMIES ) {
			texture = new Texture(Gdx.files.internal(enemiesButtonFile));
			textureClicked = new Texture(Gdx.files.internal(enemiesButtonFileClicked));
		} else if(type == ButtonType.TOWERS) {
			texture = new Texture(Gdx.files.internal(towersButtonFile));
			textureClicked = new Texture(Gdx.files.internal(towersButtonFileClicked));
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
