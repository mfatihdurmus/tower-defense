package com.git.tdgame.gameActor.level;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.git.tdgame.gameActor.Base;
import com.git.tdgame.gameActor.Gold;
import com.git.tdgame.gameActor.HealthBar;


public class Enemy extends Actor
{
	// Actor variables
	private Vector2 direction;
	private int width;
	private int height;
	private int defaultSpeed;
	private int gold;
    private float speed = 128;
    private boolean alive = true;
    private float traveledDist = 0;
    private float slowTime = 0;
    private int damage = 5;
    
    private HealthBar healthBar;
    
    // Path variables
    private Array<Vector2> path;
    private int currentPath = 0;
    
    // Sprite variables
    private Texture texture;
    private Sprite sprite;
    private double spritePos = 0;
    private int numberOfFrames = 0;
	private int drawDirection = 1;

    public Enemy (Array<Vector2>path, HashMap<String,String> properties)
    {
    	//set properties
    	this.width = Integer.valueOf(properties.get("width"));
    	this.height = Integer.valueOf(properties.get("height"));
    	this.defaultSpeed = Integer.valueOf(properties.get("defaultSpeed"));
    	this.gold = Integer.valueOf(properties.get("gold"));
    	this.damage = Integer.valueOf(properties.get("damage"));
    	this.path = path;
    	this.setWidth(this.width);
    	this.setHeight(this.height);
    	
    	Vector2 position = new Vector2(path.get(currentPath).x, path.get(currentPath).y);
    	setPosition(position.x, position.y);
        healthBar = new HealthBar(Integer.valueOf(properties.get("maxHealth")), position, this.width, this.height);
        
    	++currentPath;
    	
    	direction = new Vector2();
    	direction = findNewDirection();
    	
    	texture = new Texture(Gdx.files.internal(properties.get("texturePath")));
    	numberOfFrames = (int)(texture.getWidth()/this.width);
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,this.width,this.height);
        
    }

    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	if(drawDirection > 0)
    	{
    		batch.draw(sprite,getX(),getY()+this.height,getOriginX(),getOriginY(),this.width,this.height,drawDirection,-1,0);
    	} else if(drawDirection < 0)
    	{
    		batch.draw(sprite,getX()+getWidth(),getY()+this.height,getOriginX(),getOriginY(),this.width,this.height,drawDirection,-1,0);
    	}
    	
    	getStage().getCamera().update();
    	healthBar.draw(getStage(), batch);
    }

    public void act (float delta)
    {
    	super.act(delta);
    	
    	// Move sprite region
    	spritePos = (spritePos+0.2) % numberOfFrames;
    	sprite.setRegion((int)spritePos*this.width, 0, this.width, this.height);
    	
    	
    	slowTime -= delta;
    	if(slowTime <= 0)
    	{
    		speed = defaultSpeed;
    	}
    	
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
    	
    	healthBar.setPosition(new Vector2(targetX, targetY));
    	
    	traveledDist += speed*delta;
    }
    
    private Vector2 findNewDirection()
    {
    	Vector2 newPosition = new Vector2();
    	if(currentPath >= path.size)
    	{
        	Array<Actor> actors=getStage().getActors();
        	for(Actor a: actors){
        		if(a instanceof Base)
        		{
        			Base b = (Base)a;
        			if(b.isAlive())
        				b.takeDamage(damage);
        		}
        	}

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
		
    	if(newPosition.x != 0)
    	{
    		drawDirection = (int)newPosition.x;
    	}

		return newPosition;
    }
    
    public boolean isAlive(){
    	return alive;
    }

	public float getTraveledDist() {
		return traveledDist;
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
	
	public void setProperty(float property, float time)
	{
		if(slowTime <= 0)
		{
			slowTime = time;
			speed *= (1-property);
		}
	}
	
	public float getSpeed()
	{
		return speed;
	}

	public Vector2 getDirection()
	{
		return direction;
	}

	private void die()
	{
		if(alive)
		{
			Array<Actor> actors = getStage().getActors();
			for(Actor a : actors)
			{
				if(a instanceof Gold)
				{
					Gold g = (Gold) a;
					g.addGold(gold);
					break;
				}
			}
		}
		alive=false;
		this.remove();
	}
    
}
