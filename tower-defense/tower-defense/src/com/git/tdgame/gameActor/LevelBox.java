package com.git.tdgame.gameActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LevelBox extends Actor
{
	private ShapeRenderer shapeRenderer;
	private int width;
	private int height;
	private String path;

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public LevelBox (float x, float y, int width, int height, String path)
    {
    	this.path = path;
    	this.setPosition(x, y);
    	this.width = width;
    	this.setSize(width, height);
    	this.height = height;
        shapeRenderer = new ShapeRenderer();
    }

    public void draw (SpriteBatch batch, float parentAlpha)
    {
    	getStage().getCamera().update();
		shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);

    	batch.end();
        shapeRenderer.begin(ShapeType.FilledRectangle);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.filledRect(getX(), getY(), width, height);
        shapeRenderer.end();
        batch.begin();
    }

    public void act (float delta)
    {
    	super.act(delta);
    }
}
