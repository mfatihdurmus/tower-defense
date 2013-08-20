package com.git.tdgame.guiActor;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.git.tdgame.gameActor.Effect;
import com.git.tdgame.gameActor.projectile.ProjectileModel;
import com.google.gson.Gson;
public class GuideElement extends Actor
{
	public enum ElementType { ENEMY, TOWER};
	
	private Texture texture;
	private Sprite sprite;
	private Texture background;
	private Sprite backgroundSprite;
	private Texture border;
	private Sprite borderSprite;
	private Texture infoBorder;
	private Sprite infoBorderSprite;
	private Texture borderHovered;
	private Sprite borderHoveredSprite;
	private ElementType type;
	private boolean isHovered;
	HashMap<String,String> properties;
    private ProjectileModel projectileModel;
    ArrayList<String> elementInfo;
    private BitmapFont font;
	private Sprite animatedSprite;
    
    
    private double spritePos = 0;
    private int numberOfFrames = 0;
    private float spriteStep = 10;
    private float fireRate = 0;
    private float timeToFire = 0;
	
	public GuideElement(ElementType type, HashMap<String,String> properties) {
        font = new BitmapFont(true);
        font.setColor(1f, 0.5f, 0, 1);
    	font.setScale(1.7f, -1.7f);
    	Gson gson = new Gson();
		this.properties = properties;
	    projectileModel = gson.fromJson(properties.get("projectile"), ProjectileModel.class);
		texture = new Texture(Gdx.files.internal(properties.get("texturePath")));
		setHeight(Integer.valueOf(properties.get("height"))*2f);
		setWidth(Integer.valueOf(properties.get("width"))*2f);
        sprite = new Sprite(texture);
        animatedSprite = new Sprite(texture);
    	sprite.setRegion(0, 0, Integer.valueOf(properties.get("width")), Integer.valueOf(properties.get("height")));
    	animatedSprite.setRegion(0, 0, Integer.valueOf(properties.get("width")), Integer.valueOf(properties.get("height")));
    	this.type = type;
        if(type == ElementType.ENEMY)
        {
        	background = new Texture(Gdx.files.internal("data/guide/path.png"));
        } else {
        	background = new Texture(Gdx.files.internal("data/guide/grass.png"));
        	fireRate = Float.valueOf(properties.get("fireRate"));
        	timeToFire = fireRate;
        }
        backgroundSprite = new Sprite(background);
        border = new Texture(Gdx.files.internal("data/guide/border.png"));
        borderSprite = new Sprite(border);
        borderHovered = new Texture(Gdx.files.internal("data/guide/borderHovered.png"));
        borderHoveredSprite = new Sprite(borderHovered);
        infoBorder = new Texture(Gdx.files.internal("data/guide/infoBorder.png"));
        infoBorderSprite = new Sprite(infoBorder);
        
		elementInfo = getElementInfo();
    	numberOfFrames = (int)(texture.getWidth()/Integer.valueOf(properties.get("width")));
	}
	
    public void act (float delta)
    {
    	super.act(delta);
    	
    	if(isVisible())
    	{
	    	if(type == ElementType.ENEMY) {
		    	// Move sprite region
				spritePos = (spritePos+delta*(int)spriteStep) % numberOfFrames;
		    	animatedSprite.setRegion((int)spritePos*32, 0, 32, 32);
	    	} else {
	    		
	    		if(isHovered)
	    		{
		    		timeToFire -= delta;
		    		
		    		if(timeToFire < 0)
		    		{
		    			timeToFire = fireRate;
		            	if(!properties.get("effect").isEmpty())
		            	{
		            		getStage().addActor(createEffect());
		            	}
		    		}
	    		}
	    	}
    	}
    }

	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		batch.draw(backgroundSprite,getX()+4,getY()+4,getOriginX(),getOriginY(),getWidth()-8,getHeight()-8,1,1,0);
		batch.draw(sprite,getX()+8,getY()+8,getOriginX(),getOriginY(),getWidth()-16,getHeight()-16,1,1,0);
		if(!isHovered)
		{
			batch.draw(borderSprite,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,1,0);
		} else {
			batch.draw(borderHoveredSprite,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,1,0);
			
	        int line = 0;
			for(String str : elementInfo)
			{
		    	font.draw(batch, new String(str), 555, 270 - line * 30);
		    	++line;
			}
			batch.draw(infoBorderSprite,540,285,getOriginX(),getOriginY(),330,-250,1,1,0);
			
			batch.draw(backgroundSprite,560+10,392+10-getHeight(),getOriginX(),getOriginY(),getWidth()*2-20,getHeight()*2-20,1,1,0);
			batch.draw(animatedSprite,560+16,392+16-getHeight(),getOriginX(),getOriginY(),getWidth()*2-32,getHeight()*2-32,1,1,0);
			batch.draw(borderSprite,560,392-getHeight(),getOriginX(),getOriginY(),getWidth()*2,getHeight()*2,1,1,0);
		}
	}
	
    Effect createEffect(){
    	Effect effect = new Effect(new Vector2(560+16, 392+16), properties.get("effect"));
    	effect.setWidth(getWidth()*2-32);
    	effect.setHeight(getHeight()*2-32);
    	effect.setFliped(true);
    	return effect;
    }

	public ArrayList<String> getElementInfo()
	{
		ArrayList<String> elementInfo = new ArrayList<String>();
		
		String name = properties.get("name");
		elementInfo.add("Name : " + name);
		if(this.type == ElementType.ENEMY) {
			String speed = properties.get("defaultSpeed");
			elementInfo.add("Speed : " + speed);
			String health = properties.get("maxHealth");
			elementInfo.add("Health : " + health);
			String wealth = properties.get("gold");
			elementInfo.add("Wealth : " + wealth + " gold");
			String damage = properties.get("damage");
			elementInfo.add("Damage : " + damage);
		} else {
			String fireRate = properties.get("fireRate");
			elementInfo.add("Reload Time : " + fireRate + " sec");
			String range = properties.get("range");
			elementInfo.add("Range : " + range);
			String cost = properties.get("cost");
			elementInfo.add("Build Cost : " + cost + " gold");
			String damage = String.valueOf((int)projectileModel.getDamage());
			elementInfo.add("Damage : " + damage);
			if(projectileModel.getDamageRadius() > 0)
			{
				String damageRadius = String.valueOf((int)projectileModel.getDamageRadius());
				elementInfo.add("Damage Radius : " + damageRadius);
			}
			if(projectileModel.getSlowAmount() > 0)
			{
				String slowAmount = String.valueOf(projectileModel.getSlowAmount());
				elementInfo.add("Slow Ratio : " + slowAmount);
				String slowDuration = String.valueOf(projectileModel.getSlowDuration());
				elementInfo.add("Slow Duration : " + slowDuration + " sec");
			}
		}
		
		return elementInfo;
	}

	public ElementType getType() {
		return type;
	}

	public void setHover(boolean b) {
		isHovered = b;
	}

	public boolean isHovered() {
		return isHovered;
	}
}
