package com.git.tdgame.gameActor.tower;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.git.tdgame.gameActor.level.Enemy;
import com.git.tdgame.gameActor.projectile.AbstractProjectile;
import com.git.tdgame.gameActor.projectile.HomingProjectile;
import com.git.tdgame.gameActor.projectile.ProjectileModel;
import com.google.gson.Gson;

public class Tower extends Actor
{
	int towerLevel = 1;
	
    // Tower Variables
    private int width 	= 32;
    private int height 	= 32;
    float fireRate = 1f;
    float range = 300;
    ProjectileModel projectileModel;
    int cost;
    float upgradeRatio;
    String name;
    
    float timeToFire = 0;
    Enemy target;
    Texture texture;
    Sprite sprite;
    
    public Tower (Vector2 position, HashMap<String, String> properties)
    {
    	Gson gson = new Gson();
    	//properties
    	this.width = Integer.valueOf(properties.get("width"));
    	this.height = Integer.valueOf(properties.get("height"));
    	this.fireRate = Integer.valueOf(properties.get("fireRate"));
    	this.range = Integer.valueOf(properties.get("range"));
    	this.cost = Integer.valueOf(properties.get("cost"));
    	this.projectileModel = gson.fromJson(properties.get("projectile"), ProjectileModel.class);
    	this.upgradeRatio = Float.valueOf(properties.get("upgradeRatio"));
    	this.name = properties.get("name");
    	
    	setPosition(position.x, position.y);
    	setHeight(32);
    	setWidth(32);
    	this.setTouchable(Touchable.enabled);
    	
    	texture = new Texture(properties.get("texturePath"));
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,width,height);
        
    }

    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	batch.draw(sprite,getX(),getY()+height,getOriginX(),getOriginY(),width,height,1,-1,0);
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
    
    public int getRefund()
    {
    	return cost / 2;
    }

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
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
    
    AbstractProjectile createProjectile(){
    	return new HomingProjectile(this, target, projectileModel);
    }
    
    public int getUpgradeCost(){
    	return (towerLevel+1)*this.cost;
    }
    
    public void upgrade(){
    	towerLevel+=1;
    	this.projectileModel.setDamage((int)(this.projectileModel.getDamage()*this.upgradeRatio));
    	this.projectileModel.setDamageRadius(this.projectileModel.getDamageRadius()*this.upgradeRatio);
    	this.range = this.range * this.upgradeRatio;
    	this.fireRate = this.fireRate * this.upgradeRatio;
    }
}
