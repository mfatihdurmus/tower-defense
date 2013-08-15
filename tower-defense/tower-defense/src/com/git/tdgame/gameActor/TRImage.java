package com.git.tdgame.gameActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TRImage extends Actor {
	
    // Sprite variables
    private Sprite sprite;
    
    public TRImage (Vector2 position, Texture texture)
    {
    	setPosition(position.x,position.y);
    	
    	this.setWidth(texture.getWidth());
    	this.setHeight(texture.getHeight());
    	
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture, (int)getWidth(), (int)getHeight());
    }
    
    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	batch.draw(sprite,getX(),getY()+getHeight(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,-1,0);
    }


}
