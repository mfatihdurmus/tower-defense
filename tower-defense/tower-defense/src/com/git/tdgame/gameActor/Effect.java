package com.git.tdgame.gameActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Effect extends Actor
{
    private Texture texture;
    private Sprite sprite;
    private double spritePos = 0;
    private int numberOfFrames = 0;
    private float spriteStep = 10f;
    private boolean isFinished = false;
    private boolean isFliped = false;
    
    public Effect (Vector2 pos, String effect)
    {
    	this.setX(pos.x);
    	this.setY(pos.y);
    	
    	texture = new Texture(Gdx.files.internal(effect));
    	numberOfFrames = (int)(texture.getWidth()/texture.getHeight());
    	
    	this.setWidth(texture.getHeight());
    	this.setHeight(texture.getHeight());
    	
        sprite = new Sprite(texture, texture.getHeight(), texture.getHeight());
    }
    
    public void act (float delta)
    {
    	super.act(delta);
    	
    	// Move sprite region
		spritePos = (spritePos+delta*spriteStep) % numberOfFrames;
    	sprite.setRegion((int)spritePos*texture.getHeight(), 0, texture.getHeight(), texture.getHeight());
    	if((int)spritePos == numberOfFrames - 1)
    	{
    		isFinished = true;
    		return;
    	}
    	if(isFinished)
    	{
    		finish();
    	}
    }

    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	if(!isFliped)
    	{
    		batch.draw(sprite,getX(),getY()+this.getHeight(),getOriginX(),getOriginY(),this.getWidth(),this.getHeight(),1,-1,0);
    	} else {
    		batch.draw(sprite,getX(),getY()-this.getHeight()/3*2,getOriginX(),getOriginY(),this.getWidth(),this.getHeight(),1,1,0);
    	}
    }
    
    
    public boolean isFliped() {
		return isFliped;
	}

	public void setFliped(boolean isFliped) {
		this.isFliped = isFliped;
	}

	public void finish()
    {
		this.remove();
    }
}
