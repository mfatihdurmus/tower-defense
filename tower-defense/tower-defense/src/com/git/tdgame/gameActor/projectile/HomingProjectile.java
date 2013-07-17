package com.git.tdgame.gameActor.projectile;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.git.tdgame.gameActor.Enemy;

public class HomingProjectile extends AbstractProjectile {

	Actor target;
	
	public HomingProjectile(Actor source, Actor target, ProjectileModel pm)
	{
		super(source, pm);
		this.target = target;
	}

	public void setSlowAmount(float slowAmount)
	{
		this.slowAmount = slowAmount;
	}
	
	public void setDamage(int damage)
	{
		this.damage = damage;
	}
	
	public void setSpeed(float speed)
	{
		this.speed = speed;
	}
	
	public void setDamageRadius(int damageRadius)
	{
		this.damageRadius = damageRadius;
	}
    public void act (float delta)
    {
    	super.act(delta);
    	
    	Vector2 d= new Vector2(target.getX()-getX(), target.getY()-getY());
    	float degree = (float) (Math.atan(d.x/d.y)*180/Math.PI);
    	
    		
    	if(d.y >= 0)
    	{
    		degree += 180;
    	}
    	sprite.setRotation(270-degree);
    	Vector2 vel= d.cpy().nor().mul(speed*delta);
    	
    	if(d.len() <= speed*delta)
    	{
    		finish();
    		return;
    	}
    	setPosition(getX()+vel.x, getY()+vel.y);
    }
    
	@Override
	void finish()
	{
    	Array<Actor> damagedEnemies = new Array<Actor>();
    	
		// Give damage to target
    	if(target instanceof Enemy)
    	{
    		Enemy e = (Enemy)target;
    		e.takeDamage((int)damage);
    		if(slowAmount > 0)
    			e.setProperty(slowAmount, slowDuration, "defaultSpeed");
    		damagedEnemies.add(e);
    	}

    	if(damageRadius == 0)
    	{
    		this.remove();
    		return;
    	}
    	
    	// TO DO : Control it for circle
    	// Control Rectangle Area For Damage
    	
    	// Give damage to others
    	for(int x = (int)getX() - (int)damageRadius; x <= (int)getX() + damageRadius; x += 16)
    	{
        	for(int y = (int)getY() - (int)damageRadius; y <= getY() + damageRadius; y += 16)
        	{
    	    	Actor a = getStage().hit(x, y, true);
        		if(a != null)
        		{
        	    	if(a instanceof Enemy && !damagedEnemies.contains(a, false))
        	    	{
        	    		Enemy e = (Enemy)a;
        	    		e.takeDamage((int)damage);
        	    		if(slowAmount > 0)
        	    			e.setProperty(slowAmount, slowDuration, "defaultSpeed");
        	    		damagedEnemies.add(e);
        	    	}
        		}
        	}
    	}
		this.remove();
	}

}
