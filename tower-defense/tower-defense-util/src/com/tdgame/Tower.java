package com.tdgame;

import java.util.List;

public class Tower {
	int x;
	int y;
	int range;
	int damage;
	int timeToFire;
	Enemy target;
	int fireRate;
	List<Enemy> enemies;
	
	public void fire(Enemy e){
		e.setHealth(e.getHealth()-damage);
		if(e.getHealth() <= 0)
			e.setAlive(false);
	}
	
	public void act(int delta){
		timeToFire += delta;
		
		target = findTarget();
		
		if(timeToFire >= fireRate){
			if(target != null){
				if(target.isAlive()){
					double dist = Math.sqrt(Math.pow(target.getX()-getX(), 2)+Math.pow(target.getY(),getY()));
					if(dist <= range){
						timeToFire = 0;
						fire(target);
					}else {
						target = null;
					}
				} else {
					target = null;
				}
			} else {
				target = findTarget();
			}
		}
	}

	public Enemy findTarget(){
    	float maxTraveled= 0;
    	Enemy bestTarget= null;
    	
    	for(Enemy e: enemies){
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
    	return bestTarget;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getTimeToFire() {
		return timeToFire;
	}

	public void setTimeToFire(int timeToFire) {
		this.timeToFire = timeToFire;
	}

	public Enemy getTarget() {
		return target;
	}

	public void setTarget(Enemy target) {
		this.target = target;
	}

	public int getFireRate() {
		return fireRate;
	}

	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	
	
}
