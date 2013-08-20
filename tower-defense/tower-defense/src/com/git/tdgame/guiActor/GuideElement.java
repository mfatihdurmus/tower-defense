package com.git.tdgame.guiActor;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GuideElement extends Actor
{
	public enum ElementType { ENEMY, TOWER};
	
	private Texture texture;
	private Sprite sprite;
	private ElementType type;
	private boolean isHovered;
	
	public GuideElement(ElementType type, HashMap<String,String> properties) {
		texture = new Texture(Gdx.files.internal(properties.get("texturePath")));
		setHeight(Integer.valueOf(properties.get("height"))*1.5f);
		setWidth(Integer.valueOf(properties.get("width"))*1.5f);
        sprite = new Sprite(texture);
    	sprite.setRegion(0, 0, Integer.valueOf(properties.get("width")), Integer.valueOf(properties.get("height")));
    	this.type = type;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		if(!isHovered)
		{
			batch.draw(sprite,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,1,0);
		} else {
		}
		super.draw(batch, parentAlpha);
	}

	public ElementType getType() {
		return type;
	}

	public void setHover(boolean b) {
		isHovered = b;
	}

	public boolean isHovered() {
		return isHovered;
	}
}
