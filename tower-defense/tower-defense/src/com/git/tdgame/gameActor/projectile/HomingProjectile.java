package com.git.tdgame.gameActor.projectile;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.git.tdgame.gameActor.Enemy;
import com.git.tdgame.gameActor.tower.AbstractTower;

public class HomingProjectile extends AbstractProjectile {

	Actor target;
	
	public HomingProjectile(Actor source, Actor target)
	{
		super(source);
		this.target = target;
	}

    public void act (float delta)
    {
    	super.act(delta);
    	
    	Vector2 d= new Vector2(target.getX()-getX(), target.getY()-getY());
    	float degree = (float) (Math.atan(d.x/d.y)*180/Math.PI);
    	
    	
    	// Range bound reached
    	Vector2 sourceDistance = new Vector2(getX() - source.getX(),getY() - source.getY());
    	
    	if(source instanceof AbstractTower)
    	{
    		AbstractTower t = (AbstractTower)source;
        	if(sourceDistance.len() >= t.getRange())
        	{
        		this.remove();
        		return;
        	}
    	}
    		
    	if(d.y > 0)
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
		// Give damage to target
    	if(target instanceof Enemy)
    	{
    		Enemy e = (Enemy)target;
    		e.takeDamage(damage);
    	}

    	// TO DO : Control it for circle
    	// Control Rectangle Area For Damage
    	
    	// Give damage to others
    	for(int x = (int)getX() - (int)damageRadius; x <= (int)getX() + damageRadius; ++x)
    	{
        	for(int y = (int)getY() - (int)damageRadius; y <= getY() + damageRadius; ++y)
        	{
    	    	Actor a = getStage().hit(x, y, true);
        		if(a != null)
        		{
        	    	if(a instanceof Enemy && !a.equals(target))
        	    	{
        	    		Enemy e = (Enemy)a;
        	    		e.takeDamage(damage);
        	    	}
        		}
        	}
    	}
		this.remove();
	}

}
