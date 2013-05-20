package com.git.tdgame.gameActor.projectile;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
	
	
	public AbstractProjectile(Actor source, String propertiesJson)
	{
		Gson gson = new Gson();
		HashMap<String, String> properties = gson.fromJson(propertiesJson, new TypeToken<HashMap<String, String>>(){}.getType());
		
		//set properties
		this.WIDTH = Integer.valueOf(properties.get("width"));
		this.HEIGHT = Integer.valueOf(properties.get("height"));
		this.speed = Float.valueOf(properties.get("speed"));
		this.damage = Integer.valueOf(properties.get("damage"));
		this.damageRadius = Float.valueOf(properties.get("damageRadius"));
		this.slowAmount = Float.valueOf(properties.get("slowAmount"));
		this.slowDuration = Float.valueOf(properties.get("slowDuration"));
		
		this.source = source;
		setPosition(this.source.getX(), this.source.getY());
		
		setWidth(Integer.valueOf(this.WIDTH));
		setHeight(Integer.valueOf(this.HEIGHT));
		
    	texture = new Texture(Gdx.files.internal(properties.get("texturePath")));
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,WIDTH,HEIGHT);
	}
	
    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	sprite.setPosition(getX(), getY());
    	sprite.draw(batch);
    }
    
    void finish(){
    	
    }
}
