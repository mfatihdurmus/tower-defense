package com.git.tdgame.gameActor.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AbstractProjectile extends Actor
{
	// Projectile Variables
    int WIDTH 	= 32;
    int HEIGHT 	= 32;
	float speed = 1024;
	int damage = 10;
    float damageRadius = 0;
    float slowAmount = 0f;
    float slowDuration = 1;
	Actor source;
    Texture texture;
    Sprite sprite;
	
	
	public AbstractProjectile(Actor source, ProjectileModel pm)
	{
		//set properties
		this.WIDTH = pm.getWidth();
		this.HEIGHT = pm.getHeight();
		this.speed = pm.getSpeed();
		this.damage = pm.getDamage();
		this.damageRadius = pm.getDamageRadius();
		this.slowAmount = pm.getSlowAmount();
		this.slowDuration = pm.getSlowDuration();
		
		this.source = source;
		setPosition(this.source.getX(), this.source.getY());
		
		setWidth(Integer.valueOf(this.WIDTH));
		setHeight(Integer.valueOf(this.HEIGHT));
		
    	texture = new Texture(Gdx.files.internal(pm.getTexturePath()));
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture, WIDTH, HEIGHT);
	}
	
    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	sprite.setPosition(getX(), getY());
    	sprite.draw(batch);
    }
    
    void finish(){
    	
    }
}
