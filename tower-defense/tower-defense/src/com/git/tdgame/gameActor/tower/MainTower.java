package com.git.tdgame.gameActor.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.git.tdgame.gameActor.HealthBar;
import com.git.tdgame.screen.GameScreen;

public class MainTower extends Actor
{
	// Actor variables
    private GameScreen gameScreen;
    boolean alive = true;
    private HealthBar healthBar;

    // Sprite variables
    Texture texture;
    Texture textureDamage;
    Sprite spriteDamage;
    Sprite sprite;
    
    public MainTower (Vector2 position, GameScreen gameScreen, int health)
    {
    	this.gameScreen = gameScreen;
    	
    	setPosition(position.x, position.y);
    	texture = new Texture(Gdx.files.internal("data/game/mainTower/mainTower.png"));
    	textureDamage = new Texture(Gdx.files.internal("data/game/mainTower/mainTowerDamage.png"));
    	this.setWidth(texture.getWidth());
    	this.setHeight(texture.getHeight());
    	healthBar = new HealthBar(health, position, (int)getWidth(), 10);
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,(int)getWidth(),(int)getHeight());
        spriteDamage = new com.badlogic.gdx.graphics.g2d.Sprite(textureDamage,(int)getWidth(),(int)getHeight());
    }

    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	if(healthBar.getHealthRatio() > 25)
    	{
        	batch.draw(sprite,getX(),getY()+(int)getHeight(),getOriginX(),getOriginY(),(int)getWidth(),(int)getHeight(),1,-1,0);
    	} else {
        	batch.draw(spriteDamage,getX(),getY()+(int)getHeight(),getOriginX(),getOriginY(),(int)getWidth(),(int)getHeight(),1,-1,0);
    	}
    	
    	getStage().getCamera().update();

		healthBar.draw(getStage(), batch);
    }
    
    public void act (float delta)
    {
    	super.act(delta);
    }
    
	public void takeDamage(int d)
	{
		if(healthBar.getCurrentHealth()-d > 0)
		{
			healthBar.setCurrentHealth(healthBar.getCurrentHealth()-d);
		} else {
			healthBar.setCurrentHealth(0);
			die();
		}
	}

	private void die()
	{
		// To Do : End Game
		this.gameScreen.defeat();
		alive = false;
	}
	
	public boolean isAlive()
	{
		return alive;
	}
	
	public int getCurrentHealth()
	{
		return this.healthBar.getCurrentHealth();
	}

}
