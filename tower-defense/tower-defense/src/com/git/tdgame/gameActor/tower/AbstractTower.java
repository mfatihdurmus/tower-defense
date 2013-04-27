package com.git.tdgame.gameActor.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.git.tdgame.gameActor.enemy.Enemy;
import com.git.tdgame.gameActor.projectile.AbstractProjectile;

public abstract class AbstractTower extends Actor
{
    // Tower Variables
    final int WIDTH 	= 32;
    final int HEIGHT 	= 32;
    float fireRate = 1f;
    float range = 300;
    float timeToFire = 0;
    Enemy target;
    Texture texture;
    Sprite sprite;
    
    
    public AbstractTower (Vector2 position)
    {
    	
    	setPosition(position.x, position.y);
    	
    	texture = new Texture(Gdx.files.internal("data/game/tower/tower.png"));
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,WIDTH,HEIGHT);
    }

    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	batch.draw(sprite,getX(),getY()+HEIGHT,getOriginX(),getOriginY(),WIDTH,HEIGHT,1,-1,0);
    }

    public void act (float delta)
    {
    	super.act(delta);
    	
    	timeToFire += delta;
    	
    	// check enemy is still alive
		target= findTarget();
    	// check if can fire, if possible fire
		if(timeToFire >= fireRate)
		{
	    	if(target != null)
	    	{
	    		if(target.isAlive())
	    		{
	            	// check enemy is still in range
	        		Vector2 dist = new Vector2(target.getX() - getX(),target.getY() - getY());
	        		if(dist.len() <= range)
	        		{
        				timeToFire = 0;
        				fire();
	        		} else {
	        			target=null;
	        		}
	    		} else {
	    			target=null;
	    		}
	    	} else {
	    		target= findTarget();
	    	}
		}
    }
    
    public float getRange() {
		return range;
	}

	//find a best possible target relative to position
    public Enemy findTarget()
    {
    	//TODO: implement
    	
    	//ask for list of target in range
		//check distance of targets..
    	
    	Array<Actor> actors=getStage().getActors();
    	float maxTraveled= 0;
    	Enemy bestTarget= null;
    	for(Actor a: actors){
    		if(a instanceof Enemy)
    		{
    			Enemy e= (Enemy)a;

        		float dx= e.getX() - getX();
        		float dy= e.getY() - getY();
        		if(Math.sqrt(dx*dx+dy*dy) <= range)
        		{
                	float t= e.getTraveledDist();
                	if( t > maxTraveled){
                		bestTarget= e;
                		maxTraveled= t;
                	}
        		}
    		}
    	}
    	return bestTarget;
    }
    
    //fire to target
    public void fire()
    {
    	getStage().addActor(createProjectile());
    }
    
    abstract AbstractProjectile createProjectile();
}
