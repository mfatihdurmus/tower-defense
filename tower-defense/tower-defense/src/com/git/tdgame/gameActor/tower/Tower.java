package com.git.tdgame.gameActor.tower;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.git.tdgame.gameActor.Enemy;
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
    private float fireRate = 1f;
    private float range = 300;
    private ProjectileModel projectileModel;
    private int cost;
    private float upgradeRatio;
    private Color rangeColor;
    private String name;
    
    private boolean isHovered = false;
    private boolean isUpgradeDisplay = false;
    private boolean isSold = false;
    
    private float timeToFire = 0;
    private Enemy target;
    private Texture texture;
    private Sprite sprite;
	private ShapeRenderer shapeRenderer;
	
	private Sound fireSound;
	private Sound fireSoundAlternative;
    
	public int getDamage()
	{
		return (int)projectileModel.getDamage();
	}
	
    public boolean isSold() {
		return isSold;
	}

	public void setSold(boolean isSold) {
		this.isSold = isSold;
	}

	public Color getRangeColor() {
		return rangeColor;
	}

	public void setRangeColor(Color rangeColor) {
		this.rangeColor = rangeColor;
	}

	public Tower (Vector2 position, HashMap<String, String> properties)
    {
    	Gson gson = new Gson();
    	//properties
    	this.width = Integer.valueOf(properties.get("width"));
    	this.height = Integer.valueOf(properties.get("height"));
    	this.fireRate = Float.valueOf(properties.get("fireRate"));
    	this.range = Integer.valueOf(properties.get("range"));
    	this.cost = Integer.valueOf(properties.get("cost"));
    	this.projectileModel = gson.fromJson(properties.get("projectile"), ProjectileModel.class);
    	this.upgradeRatio = Float.valueOf(properties.get("upgradeRatio"));
    	this.fireSound = Gdx.audio.newSound(Gdx.files.internal(properties.get("soundPath")));
    	this.fireSoundAlternative = Gdx.audio.newSound(Gdx.files.internal(properties.get("soundPathAlter")));
    	this.rangeColor = new Color(0, 1, 0, 0.3f);
    	this.name = properties.get("name");
    	
    	setPosition(position.x, position.y);
    	setHeight(32);
    	setWidth(32);
    	
    	texture = new Texture(properties.get("texturePath"));
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,width,height);
        shapeRenderer = new ShapeRenderer();
    }

    public boolean isUpgradeDisplay() {
		return isUpgradeDisplay;
	}

	public void setUpgradeDisplay(boolean isUpgradeDisplay) {
		this.isUpgradeDisplay = isUpgradeDisplay;
	}

	public void draw (SpriteBatch batch, float parentAlpha)
    {
    	batch.draw(sprite,getX(),getY()+height,getOriginX(),getOriginY(),width,height,1,-1,0);
    	
    	if(isHovered)
    	{
	    	batch.end();
	    	Gdx.gl.glEnable(GL10.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
		    shapeRenderer.begin(ShapeType.FilledCircle);
	   		shapeRenderer.setColor(rangeColor);
	   		if(isUpgradeDisplay)
	   		{
	   			shapeRenderer.filledCircle(getX()+width/2, getY()+height/2, range * upgradeRatio);
	   		} else {
	   			shapeRenderer.filledCircle(getX()+width/2, getY()+height/2, range);
	   		}
	   		shapeRenderer.end();
	   		Gdx.gl.glDisable(GL10.GL_BLEND);
	        batch.begin();
    	}
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
    	return (cost + getCurrentUpgradeCost()) / 2;
    }
    
    public int getCurrentUpgradeCost()
    {
    	int cost = 0;
    	for(int ctr = 1; ctr < towerLevel; ++ctr)
    		cost += (int)((ctr+1)*this.cost/2);
    	return cost;
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
    	Random generator = new Random();
    	if(generator.nextInt(2) == 0)
    		fireSound.play();
    	else
    		fireSoundAlternative.play();
    }
    
    AbstractProjectile createProjectile(){
    	return new HomingProjectile(this, target, projectileModel);
    }
    
    public int getUpgradeCost(){
    	return (int)((towerLevel+1)*this.cost/2);
    }
    
    public boolean isMaxLevel()
    {
    	if(towerLevel >= 5)
    		return true;
    	return false;
	}
    
    public void upgrade()
    {
    	towerLevel += 1;
    	if(this.projectileModel.getDamageRadius() > 0 )
    	{
        	this.projectileModel.setDamageRadius(this.projectileModel.getDamageRadius() * this.upgradeRatio);
    	} else if(this.projectileModel.getSlowAmount() > 0)
    	{
        	this.projectileModel.setSlowAmount(this.projectileModel.getSlowAmount() * this.upgradeRatio);
        	this.projectileModel.setSlowDuration(this.projectileModel.getSlowDuration() * this.upgradeRatio);
    	} else {
        	this.projectileModel.setDamage((this.projectileModel.getDamage() * this.upgradeRatio));
        	this.fireRate /= this.upgradeRatio;
    	}
    	this.range *= this.upgradeRatio;
    }

	public boolean isHovered() {
		return isHovered;
	}

	public void setHovered(boolean isHovered) {
		this.isHovered = isHovered;
	}

	public float getFireRate() {
		return fireRate;
	}

	@Override
	public String toString() {
		return "Tower [towerLevel=" + towerLevel + ", width=" + width
				+ ", height=" + height + ", fireRate=" + fireRate + ", range="
				+ range + ", projectileModel=" + projectileModel + ", cost="
				+ cost + ", upgradeRatio=" + upgradeRatio + ", rangeColor="
				+ rangeColor + ", name=" + name + ", isHovered=" + isHovered
				+ ", isUpgradeDisplay=" + isUpgradeDisplay + ", timeToFire="
				+ timeToFire + ", target=" + target + ", texture=" + texture
				+ ", sprite=" + sprite + ", shapeRenderer=" + shapeRenderer
				+ ", fireSound=" + fireSound + ", fireSoundAlternative="
				+ fireSoundAlternative + "]";
	}
}
