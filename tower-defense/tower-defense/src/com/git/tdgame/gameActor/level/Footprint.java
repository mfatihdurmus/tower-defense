package com.git.tdgame.gameActor.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Footprint extends Actor {

	private ShapeRenderer shapeRenderer;
	private int width;
	private int height;
	private int index;
    private boolean isActive = true;
    private float viewPortHeight;
	    
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Footprint (float x, float y, int width, int height, float viewPortHeight, int index)
    {
		this.viewPortHeight = viewPortHeight;
    	this.setPosition(x, y);
    	this.width = width;
    	this.height = height;
    	this.setSize(this.width, this.height);
    	this.index = index;
    	
        shapeRenderer = new ShapeRenderer();
    }

    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	if(isActive)
    	{
        	batch.end();
    		shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
            shapeRenderer.begin(ShapeType.FilledCircle);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.filledCircle(getX()+(int)(getWidth()/2), viewPortHeight-getY()-(int)(getHeight()/2+1), (int)(getWidth()/2 + 5));
            shapeRenderer.end();
            shapeRenderer.begin(ShapeType.FilledCircle);
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.filledCircle(getX()+(int)(getWidth()/2), viewPortHeight-getY()-(int)(getHeight()/2+1), (int)(getWidth()/2 + 4));
            shapeRenderer.end();
            shapeRenderer.begin(ShapeType.FilledCircle);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.filledCircle(getX()+(int)(getWidth()/2), viewPortHeight-getY()-(int)(getHeight()/2+1), (int)(getWidth()/2 + 3));
            shapeRenderer.end();
            shapeRenderer.begin(ShapeType.FilledCircle);
            shapeRenderer.setColor(new Color(0, 0.5f, 1, 1));
            shapeRenderer.filledCircle(getX()+(int)(getWidth()/2), viewPortHeight-getY()-(int)(getHeight()/2+1), (int)(getWidth()/2 + 2));
            shapeRenderer.end();
            batch.begin();
    	} else {
        	batch.end();
    		shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
            shapeRenderer.begin(ShapeType.FilledCircle);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.filledCircle(getX()+(int)(getWidth()/2), viewPortHeight-getY()-(int)(getHeight()/2+1), (int)(getWidth()/2 + 5));
            shapeRenderer.end();
            shapeRenderer.begin(ShapeType.FilledCircle);
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.filledCircle(getX()+(int)(getWidth()/2), viewPortHeight-getY()-(int)(getHeight()/2+1), (int)(getWidth()/2 + 4));
            shapeRenderer.end();
            shapeRenderer.begin(ShapeType.FilledCircle);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.filledCircle(getX()+(int)(getWidth()/2), viewPortHeight-getY()-(int)(getHeight()/2+1), (int)(getWidth()/2 + 3));
            shapeRenderer.end();
            shapeRenderer.begin(ShapeType.FilledCircle);
            shapeRenderer.setColor(new Color(0.5f, 0, 0, 1));
            shapeRenderer.filledCircle(getX()+(int)(getWidth()/2), viewPortHeight-getY()-(int)(getHeight()/2+1), (int)(getWidth()/2 + 2));
            shapeRenderer.end();
            batch.begin();
    		batch.setColor(1, 1, 1, 0.5f);
    		batch.setColor(1, 1, 1, 1);
    	}
    	
    }

    public void act (float delta)
    {
    	super.act(delta);
    }
}
