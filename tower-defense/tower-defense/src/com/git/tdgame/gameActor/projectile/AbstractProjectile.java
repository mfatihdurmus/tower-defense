package com.git.tdgame.gameActor.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class AbstractProjectile extends Actor
{
	// Projectile Variables
    final int WIDTH 	= 32;
    final int HEIGHT 	= 32;
	float speed = 1024;
	int damage = 10;
    float damageRadius = 0;
    float slowAmount = 0f;
    float slowDuration = 1;
	Actor source;
    Texture texture;
    Sprite sprite;
	
	
	public AbstractProjectile(Actor source)
	{
		this.source = source;
		setPosition(this.source.getX(), this.source.getY());
		
		setWidth(WIDTH);
		setHeight(HEIGHT);
		
    	texture = new Texture(Gdx.files.internal("data/game/projectile/arrow.png"));
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,WIDTH,HEIGHT);
	}
	
    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	sprite.setPosition(getX(), getY());
    	sprite.draw(batch);
    }
    
    abstract void finish();
}
