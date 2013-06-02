package com.git.tdgame.gameActor.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.git.tdgame.gameActor.level.Enemy;

public class ConstantTargetProjectile extends AbstractProjectile
{
	Vector2 targetPoint;
	Actor target;
	
	Vector2 projectileBound;
	
	public ConstantTargetProjectile(Actor source, Actor target, ProjectileModel pm)
	{
		super(source, pm);
		this.target = target;
		
    	projectileBound = new Vector2(getX()+getWidth()/2-getX(),getY()+getHeight()/2-getY());
    	
		this.targetPoint = predictNextLocation(target);
		prepareSprite();
	}

    private Vector2 predictNextLocation(Actor target)
    {
    	if(target instanceof Enemy)
    	{
    		Enemy e = (Enemy) target;
        	Vector2 enemyVector = e.getDirection().cpy();
        	Vector2 distance= new Vector2(source.getX()-target.getX(), source.getY()-target.getY());
        	
        	Vector2 vel= distance.cpy().nor().mul(speed*Gdx.graphics.getDeltaTime());
        	
        	enemyVector = enemyVector.mul(e.getSpeed()*Gdx.graphics.getDeltaTime());
    		return new Vector2(target.getX()+enemyVector.x*distance.x/vel.x,target.getY()+enemyVector.y*distance.y/vel.y);
    	}
		return new Vector2(target.getX(),target.getY());
	}

	private void prepareSprite()
    {
    	Vector2 d= new Vector2(targetPoint.x-getX(), targetPoint.y-getY());
    	float degree = (float) (Math.atan(d.x/d.y)*180/Math.PI);
    	
    	if(d.y > 0)
    	{
    		degree += 180;
    	}
    	sprite.setRotation(270-degree);
	}

	public void act (float delta)
    {
    	super.act(delta);
    	
    	Vector2 d= new Vector2(targetPoint.x-getX(), targetPoint.y-getY());
    	
    	Vector2 vel= d.cpy().nor().mul(speed*delta);
    	
/*    	// Range bound reached
    	Vector2 sourceDistance = new Vector2(getX() - source.getX(),getY() - source.getY());
    	
    	if(source instanceof AbstractTower)
    	{
    		AbstractTower t = (AbstractTower)source;
        	if(sourceDistance.len() >= t.getRange())
        	{
        		this.remove();
        		return;
        	}
    	}*/
    	
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
    	Vector2 deflectionVector = new Vector2(target.getX()-getX(),target.getY()-getY());
    	
    	Array<Actor> damagedEnemies = new Array<Actor>();
    	
    	// Successful prediction
    	if(deflectionVector.len() <= projectileBound.len())
    	{
        	// Give damage to target
	    	if(target instanceof Enemy)
	    	{
	    		Enemy e = (Enemy)target;
	    		e.takeDamage((int)damage);
	    		e.setProperty(slowAmount, slowDuration);
	    		damagedEnemies.add(e);
	    	}
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
        	    		e.setProperty(slowAmount, slowDuration);
        	    		damagedEnemies.add(e);
        	    	}
        		}
        	}
    	}
		this.remove();
	}
}
