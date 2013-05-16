package com.git.tdgame.gameActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.git.tdgame.screen.GameScreen;

public class Base extends Actor
{
	// Actor variables
    private final int WIDTH 	= 32;
    private final int HEIGHT 	= 32;
    private int maxHealth = 20;
    private int currentHealth = 20;
    private final int healthBarHeight = 10;
    private GameScreen gameScreen;
    boolean alive = true;

    // Sprite variables
    private Texture texture;
    Sprite sprite;
	private ShapeRenderer shapeRenderer;
    
    public Base (Vector2 position, GameScreen gameScreen)
    {
    	this.setWidth(WIDTH);
    	this.setHeight(HEIGHT);
    	this.gameScreen = gameScreen;
    	
    	setPosition(position.x, position.y);
    	texture = new Texture(Gdx.files.internal("data/game/base/base.png"));
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,WIDTH,HEIGHT);
        shapeRenderer = new ShapeRenderer();
    }

    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	batch.draw(sprite,getX(),getY()+HEIGHT,getOriginX(),getOriginY(),WIDTH,HEIGHT,1,-1,0);
    	
    	getStage().getCamera().update();
		shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);

		float healthLeftBar = getWidth() * ((float)currentHealth/maxHealth);
    	batch.end();
        shapeRenderer.begin(ShapeType.Rectangle);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(getX(), getY()-healthBarHeight, getWidth()+2, healthBarHeight+2);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeType.FilledRectangle);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.filledRect(getX()+1, getY()-healthBarHeight+1, getWidth(), healthBarHeight);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.filledRect(getX()+1, getY()-healthBarHeight+1, healthLeftBar, healthBarHeight);
        shapeRenderer.end();
        batch.begin();
    }
    
    public void act (float delta)
    {
    	super.act(delta);
    }
    
	public void takeDamage(int d)
	{
		currentHealth -= d;
		
		if(currentHealth <= 0)
		{
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

}
