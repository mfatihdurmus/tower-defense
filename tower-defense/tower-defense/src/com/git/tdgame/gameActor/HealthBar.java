package com.git.tdgame.gameActor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class HealthBar {

	private float width;
	private float height;
	private int maxHealth;
    private int currentHealth;
    private final int healthBarHeight = 8;
    private Vector2 position;
	private ShapeRenderer shapeRenderer;
	
	public int getHealthRatio()
	{
		return (int)(((float)currentHealth / (float)maxHealth) * 100);
	}

	public HealthBar(int health, Vector2 position, float width, float height) {
		this.maxHealth = health;
		this.currentHealth = this.maxHealth;
		this.position = position;
		this.width = width;
		this.height = height;
		
        shapeRenderer = new ShapeRenderer();
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public void draw(Stage stage, SpriteBatch batch) {
		
		float y = position.y-healthBarHeight;
		if(y < 0)
			y = 0;
		float healthLeftBar = getWidth() * ((float)currentHealth/maxHealth);
    	batch.end();
		shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeType.FilledRectangle);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.filledRect(position.x, y, getWidth(), healthBarHeight);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.filledRect(position.x, y, healthLeftBar, healthBarHeight);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeType.Rectangle);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(position.x, y, getWidth(), healthBarHeight);
        shapeRenderer.end();
        batch.begin();
		
	}

}
