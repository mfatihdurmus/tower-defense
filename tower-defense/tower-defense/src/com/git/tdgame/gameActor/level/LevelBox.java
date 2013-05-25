package com.git.tdgame.gameActor.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LevelBox extends Actor
{
	private ShapeRenderer shapeRenderer;
	private int width;
	private int height;
	private LevelModel level;
	private Texture texture;
    private Sprite sprite;
    private float viewPortHeight;
    private boolean isActive = true;
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}

	public void setShapeRenderer(ShapeRenderer shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
	}

	public LevelModel getLevel() {
		return level;
	}

	public void setLevel(LevelModel level) {
		this.level = level;
	}

	public LevelBox (float x, float y, int width, int height, LevelModel level, float viewPortHeight)
    {
		this.viewPortHeight = viewPortHeight;
    	this.level = level;
    	this.setPosition(x, y);
    	this.width = width;
    	this.height = height;
    	this.setSize(this.width, this.height);
    	
    	texture = new Texture(Gdx.files.internal(level.getSelectScreenImagePath()));
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,texture.getWidth(),texture.getHeight());
        
        shapeRenderer = new ShapeRenderer();
    }

    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	if(isActive)
    	{
        	batch.draw(sprite,getX(),viewPortHeight-getY()-this.height,getOriginX(),getOriginY(),this.width,this.height,1,1,0);
    	} else {
    		batch.setColor(1, 1, 1, 0.5f);
    		batch.draw(sprite,getX(),viewPortHeight-getY()-this.height,getOriginX(),getOriginY(),this.width,this.height,1,1,0);
    		batch.setColor(1, 1, 1, 1);
    	}
    	
    }

    public void act (float delta)
    {
    	super.act(delta);
    }
    
    
}
