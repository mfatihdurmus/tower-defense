package com.git.tdgame.gameActor.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TowerConstructButton extends Actor {
	private String towerName;
	private Texture texture;
	private Sprite sprite;
	
	public TowerConstructButton( TowerConstructButton other) {
		towerName = other.towerName;
		texture = other.texture;
		setHeight(texture.getHeight());
		setWidth(texture.getWidth());
        sprite = new Sprite(texture);
	}
	
	public TowerConstructButton( String path, String name ) {
		towerName = name;
		texture = new Texture(Gdx.files.internal(path));
		setHeight(texture.getHeight());
		setWidth(texture.getWidth());
        sprite = new Sprite(texture);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(sprite,getX(),getY()+getHeight(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,-1,0);
		super.draw(batch, parentAlpha);
	}
	
	public String getTowerName() {
		return towerName;
	}
	
}
