package com.git.tdgame.gameActor.level;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.git.tdgame.gameActor.Base;
import com.git.tdgame.gameActor.Gold;


public class Enemy extends Actor
{
	// Actor variables
	private Vector2 direction;
	private int width;
	private int height;
	private int defaultSpeed;
	private int maxHealth;
	private int gold;
    private float speed = 128;
    private boolean alive = true;
    private float traveledDist = 0;
    private int currentHealth = 0;
    private float slowTime = 0;
    private final int healthBarHeight = 10;
    private int damage = 5;
    
    // Path variables
    private Array<Vector2> path;
    private int currentPath = 0;
    
    // Sprite variables
    private Texture texture;
    private Sprite sprite;
    private double spritePos = 0;
    private int numberOfFrames = 0;
	private ShapeRenderer shapeRenderer;

    public Enemy (Array<Vector2>path, HashMap<String,String> properties)
    {
    	//set properties
    	this.width = Integer.valueOf(properties.get("width"));
    	this.height = Integer.valueOf(properties.get("height"));
    	this.defaultSpeed = Integer.valueOf(properties.get("defaultSpeed"));
    	this.maxHealth = Integer.valueOf(properties.get("maxHealth"));
    	this.gold = Integer.valueOf(properties.get("gold"));
    	this.damage = Integer.valueOf(properties.get("damage"));
    	
    	this.path = path;
    	this.setWidth(this.width);
    	this.setHeight(this.height);
    	this.currentHealth = this.maxHealth;
    	
    	setPosition(path.get(currentPath).x, path.get(currentPath).y);
    	++currentPath;
    	
    	direction = new Vector2();
    	direction = findNewDirection();
    	
    	texture = new Texture(Gdx.files.internal("data/game/enemy/ball.png"));
    	numberOfFrames = (int)(texture.getWidth()/this.width);
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,this.width,this.height);
        shapeRenderer = new ShapeRenderer();
    }

    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	batch.draw(sprite,getX(),getY()+this.height,getOriginX(),getOriginY(),this.width,this.height,1,-1,0);
    	
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
		currentHealth -= d;
		
		if(currentHealth <= 0)
		{
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
