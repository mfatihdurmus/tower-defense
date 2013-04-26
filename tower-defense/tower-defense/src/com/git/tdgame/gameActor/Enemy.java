package com.git.tdgame.gameActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;


public class Enemy extends Actor
{
	// Actor variables
	private Vector2 direction;
    private int speed = 128;
    private boolean alive = true;
    private final int WIDTH 	= 32;
    private final int HEIGHT 	= 32;
    

    // Path variables
    private Array<Vector2> path;
    private int currentPath = 0;
    
    // Sprite variables
    private Texture texture;
    private Sprite sprite;
    private double spritePos = 0;
    private int numberOfFrames = 0;
    
    private float traveledDist=0;
    
    private int health = 40;

    public Enemy (Array<Vector2>path)
    {
    	this.path = path;
    	this.setWidth(WIDTH);
    	this.setHeight(HEIGHT);
    	
    	setPosition(path.get(currentPath).x, path.get(currentPath).y);
    	++currentPath;
    	
    	direction = new Vector2();
    	direction = findNewDirection();
    	
    	texture = new Texture(Gdx.files.internal("data/game/ball.png"));
    	numberOfFrames = (int)(texture.getWidth()/WIDTH);
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,WIDTH,HEIGHT);
    }

    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	// Move sprite region
    	spritePos = (spritePos+0.2) % numberOfFrames;
    	sprite.setRegion((int)spritePos*WIDTH, 0, WIDTH, HEIGHT);
    	
    	batch.draw(sprite,getX(),getY()+HEIGHT,getOriginX(),getOriginY(),WIDTH,HEIGHT,1,-1,0);
    }

    public void act (float delta)
    {
    	super.act(delta);
    	
    	float targetX = getX() + direction.x*speed*delta;
    	float targetY = getY() + direction.y*speed*delta;

    	// Target Tile Reached
    	// Get new direction
    	if(direction.x > 0 && targetX >= path.get(currentPath).x)
		{
            	setX(path.get(currentPath).x);
        		++currentPath;
            	direction = findNewDirection();
        		return;
    	} else if(direction.x < 0 && targetX <= path.get(currentPath).x)
		{
        	setX(path.get(currentPath).x);
    		++currentPath;
        	direction = findNewDirection();
    		return;
    	}
    	
    	if(direction.y > 0 && targetY >= path.get(currentPath).y)
		{
        	setY(path.get(currentPath).y);
    		++currentPath;
        	direction = findNewDirection();
    		return;
    	} else if(direction.y < 0 && targetY <= path.get(currentPath).y)
		{
        	setY(path.get(currentPath).y);
    		++currentPath;
        	direction = findNewDirection();
    		return;
    	}
    	
    	setX(targetX);
    	setY(targetY);
    	
    	traveledDist += speed*delta;
    }
    
    private Vector2 findNewDirection()
    {
    	Vector2 newPosition = new Vector2();
    	if(currentPath >= path.size)
    	{
    		die();
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
			newPosition.set(0,-1);
		} else if(path.get(currentPath).y > path.get(currentPath-1).y)
		{
			newPosition.set(0,1);
		}
		return newPosition;
    }
    
    public boolean isAlive(){
    	return alive;
    }

	public float getTraveledDist() {
		return traveledDist;
	}
    
	public void takeDamage(int d){
		health-=d;
		
		if(health<=0){
			die();
		}
	}

	private void die() {
		this.remove();
		alive=false;
	}
    
}
