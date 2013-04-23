package com.git.tdgame.gameActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;


public class Ball extends Actor
{

	// Actor variables
	private Vector2 direction;
    private int speed = 128;		//pixel per second
    private int currentPath = 0;
    
    private Array<Vector2> path;
    
    // Sprite variables
    private Texture texture;
    private Sprite sprite;
    private double spritePos = 0;
    private int numberOfFrames = 0;

    public Ball (Array<Vector2>path)
    {
    	this.path = path;
    	direction = new Vector2();
    	setPosition(path.get(currentPath).x*32, (32 - (path.get(currentPath).y+1))*32);
    	++currentPath;
    	direction = findPosition();
    	
    	texture = new Texture(Gdx.files.internal("data/game/ball.png"));
    	numberOfFrames = texture.getWidth()/32;
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,32,32);
    }

    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	// Move sprite draw region
    	spritePos = (spritePos+0.2) % numberOfFrames;
    	
    	sprite.setPosition(getX(), getY());
    	sprite.setRegion((int)spritePos*32, 0, 32, 32);
    	sprite.draw(batch);
    }

    public void act (float delta)
    {
    	super.act(delta);
    	
    	float targetX = getX() + direction.x*speed*delta;
    	float targetY = getY() + direction.y*speed*delta;

    	// Target Tile Reached
    	if(direction.x > 0 && targetX >= 32*path.get(currentPath).x)
		{
            	setX(32*path.get(currentPath).x);
        		++currentPath;
            	direction = findPosition();
        		return;
    	} else if(direction.x < 0 && targetX <= 32*path.get(currentPath).x)
		{
        	setX(32*path.get(currentPath).x);
    		++currentPath;
        	direction = findPosition();
    		return;
    	}
    	
    	if(direction.y > 0 && targetY >= 32*(31-path.get(currentPath).y))
		{
        	setY(32*(31-path.get(currentPath).y));
    		++currentPath;
        	direction = findPosition();
    		return;
    	} else if(direction.y < 0 && targetY <= 32*(31-path.get(currentPath).y))
		{
        	setY(32*(31-path.get(currentPath).y));
    		++currentPath;
        	direction = findPosition();
    		return;
    	}
    	
    	setX(targetX);
    	setY(targetY);

    }
    
    private Vector2 findPosition()
    {
    	Vector2 newPosition = new Vector2();
    	if(currentPath >= path.size)
    	{
    		this.remove();
    		return newPosition;
    	}
    	
		if(path.get(currentPath).x > path.get(currentPath-1).x)
		{
			newPosition.set(1,0);
		} else if(path.get(currentPath).x < path.get(currentPath-1).x)
		{
			newPosition.set(-1,0);
		} else if(path.get(currentPath).y < path.get(currentPath-1).y)
		{
			newPosition.set(0,1);
		} else if(path.get(currentPath).y > path.get(currentPath-1).y)
		{
			newPosition.set(0,-1);
		}
		return newPosition;
    }
}
