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
    private int speed = 128;

    // Path variables
    private Array<Vector2> path;
    private int currentPath = 0;
    private Vector2 tileSize;
    private int mapHeight;
    
    // Sprite variables
    private Texture texture;
    private Sprite sprite;
    private double spritePos = 0;
    private int numberOfFrames = 0;

    public Ball (Array<Vector2>path, Vector2 tileSize, int mapHeight)
    {
    	this.path = path;
    	this.tileSize = tileSize;
    	this.mapHeight = mapHeight;
    	
    	setPosition(path.get(currentPath).x*tileSize.x, (mapHeight-1 - path.get(currentPath).y)*tileSize.y);
    	++currentPath;
    	
    	direction = new Vector2();
    	direction = findNewDirection();
    	
    	texture = new Texture(Gdx.files.internal("data/game/ball.png"));
    	numberOfFrames = (int)(texture.getWidth()/tileSize.x);
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,(int)tileSize.x,(int)tileSize.y);
    }

    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	// Move sprite region
    	spritePos = (spritePos+0.2) % numberOfFrames;
    	
    	sprite.setPosition(getX(), getY());
    	sprite.setRegion((int)spritePos*(int)tileSize.x, 0, (int)tileSize.x, (int)tileSize.x);
    	sprite.draw(batch);
    }

    public void act (float delta)
    {
    	super.act(delta);
    	
    	float targetX = getX() + direction.x*speed*delta;
    	float targetY = getY() + direction.y*speed*delta;

    	// Target Tile Reached
    	// Get new direction
    	if(direction.x > 0 && targetX >= tileSize.x*path.get(currentPath).x)
		{
            	setX(tileSize.x*path.get(currentPath).x);
        		++currentPath;
            	direction = findNewDirection();
        		return;
    	} else if(direction.x < 0 && targetX <= tileSize.x*path.get(currentPath).x)
		{
        	setX(tileSize.x*path.get(currentPath).x);
    		++currentPath;
        	direction = findNewDirection();
    		return;
    	}
    	
    	if(direction.y > 0 && targetY >= tileSize.y*(mapHeight-1-path.get(currentPath).y))
		{
        	setY(tileSize.y*(mapHeight-1-path.get(currentPath).y));
    		++currentPath;
        	direction = findNewDirection();
    		return;
    	} else if(direction.y < 0 && targetY <= tileSize.y*(mapHeight-1-path.get(currentPath).y))
		{
        	setY(tileSize.y*(mapHeight-1-path.get(currentPath).y));
    		++currentPath;
        	direction = findNewDirection();
    		return;
    	}
    	
    	setX(targetX);
    	setY(targetY);

    }
    
    private Vector2 findNewDirection()
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
