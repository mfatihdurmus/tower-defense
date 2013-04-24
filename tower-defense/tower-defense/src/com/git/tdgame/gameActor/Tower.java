package com.git.tdgame.gameActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public abstract class Tower extends Actor
{
    // Sprite variables
    private Texture texture;
    private Sprite sprite;
    
    int mapHeight;
    Vector2 tileSize;
    
    //addition
    private float fireRate = 1;
    private float range = 300;
    private float timeToFire=0;
    
    Enemy target;
    
    
    public Tower (Vector2 position, Vector2 tileSize, int mapHeight)
    {
    	this.mapHeight = mapHeight;
    	this.tileSize=tileSize;
    	
    	setPosition(position.x, tileSize.y*(this.mapHeight-1) - position.y);
    	
    	texture = new Texture(Gdx.files.internal("data/game/tower.png"));
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,(int)tileSize.x,(int)tileSize.y);
    }

    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	sprite.setPosition(getX(), getY());
    	sprite.draw(batch);
    }

    public void act (float delta)
    {
    	super.act(delta);
    	
    	timeToFire -= delta;
    	
    	// check enemy is still alive
    	if(target!=null)
    	{
    		if(target.isAlive()){
            	// check enemy is still in range
        		float dx= target.getX() - getX();
        		float dy= target.getY() - getY();
        		if(Math.sqrt(dx*dx+dy*dy) <= range){
                	// check if can fire, if possible fire
        			if(timeToFire<=0){
        				fire();
        			}
        		}
        		else{
        			target=null;
        		}
    		}
    		else{
    			target=null;
    		}
    	}
    	else{
    		target= findTarget();
    	}
    }
    
    //find a best possible target realative to position
    public Enemy findTarget(){
    	//TODO: implement
    	
    	//ask for list of target in range
		//check distance of targets..
    	
    	Array<Actor> actors=getStage().getActors();
    	float maxTraveled= 0;
    	Enemy bestTarget= null;
    	for(Actor a: actors){
    		if(a instanceof Enemy){
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
    public void fire(){
    	getStage().addActor(createProjectile());
    	
    	timeToFire= fireRate;
    	
    }
    
    abstract Projectile createProjectile();
}
