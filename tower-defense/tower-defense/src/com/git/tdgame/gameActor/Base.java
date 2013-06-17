package com.git.tdgame.gameActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.git.tdgame.screen.GameScreen;

public class Base extends Actor
{
	// Actor variables
    private final int WIDTH 	= 32;
    private final int HEIGHT 	= 32;
    private GameScreen gameScreen;
    boolean alive = true;
    private HealthBar healthBar;

    // Sprite variables
    private Texture texture;
    Sprite sprite;
    
    public Base (Vector2 position, GameScreen gameScreen, int health)
    {
    	this.setWidth(WIDTH);
    	this.setHeight(HEIGHT);
    	this.gameScreen = gameScreen;
    	
    	healthBar = new HealthBar(health, position, WIDTH, 10);
    	
    	setPosition(position.x, position.y);
    	texture = new Texture(Gdx.files.internal("data/game/base/base.png"));
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,WIDTH,HEIGHT);
    }

    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	batch.draw(sprite,getX(),getY()+HEIGHT,getOriginX(),getOriginY(),WIDTH,HEIGHT,1,-1,0);
    	
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
