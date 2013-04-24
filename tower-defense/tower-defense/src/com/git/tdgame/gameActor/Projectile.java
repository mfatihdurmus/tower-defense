package com.git.tdgame.gameActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Projectile extends Actor
{
	private float speed = 512;
	Actor source;
	Actor target;
	int damage;
	
    // Sprite variables
    private Texture texture;
    private Sprite sprite;
    private Vector2 tileSize; 
	
	public Projectile(Actor source, Actor target, Vector2 tileSize)
	{
		this.source = source;
		this.target = target;
		this.tileSize = tileSize;
		this.setPosition(this.source.getX(), this.source.getY());
		
    	texture = new Texture(Gdx.files.internal("data/game/arrow.png"));
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,(int)this.tileSize.x,(int)this.tileSize.y);
	}
	
    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	sprite.setPosition(getX(), getY());
    	sprite.draw(batch);
    }

    public void act (float delta)
    {
    	super.act(delta);
    	
    	Vector2 d= new Vector2(target.getX()-getX(), target.getY()-getY());
    	float degree = (float) (Math.atan(d.x/d.y)*180/Math.PI);
    	
    	if(d.y > 0)
    	{
    		degree += 180;
    	}
    	sprite.setRotation(270-degree);
    	Vector2 vel= d.cpy().nor().mul(speed*delta);
    	// chyek if finished
    	
    	if(d.len()<=speed*delta){
    		finish();
    		return;
    	}
    	setPosition(getX()+vel.x, getY()+vel.y);
    }
    
    abstract void finish();
}
