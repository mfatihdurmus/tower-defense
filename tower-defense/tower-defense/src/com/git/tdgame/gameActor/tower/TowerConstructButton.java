package com.git.tdgame.gameActor.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TowerConstructButton extends Actor {
	private String towerName;
	private Texture texture;
	private Sprite sprite;
	private boolean isHovered = false;
	private ShapeRenderer shapeRenderer;
	private int range;
	
	public TowerConstructButton( TowerConstructButton other) {
		towerName = other.towerName;
		texture = other.texture;
		setHeight(texture.getHeight());
		setWidth(texture.getWidth());
        sprite = new Sprite(texture);
        range = other.range;
	}
	
	public TowerConstructButton( String path, String name, int range ) {
		towerName = name;
		texture = new Texture(Gdx.files.internal(path));
		setHeight(texture.getHeight()*2);
		setWidth(texture.getWidth()*2);
        sprite = new Sprite(texture);
        this.range = range;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(sprite,getX(),getY()+getHeight(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,-1,0);
		super.draw(batch, parentAlpha);
		
		if(isHovered)
		{
	    	batch.end();
	    	Gdx.gl.glEnable(GL10.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	        shapeRenderer = new ShapeRenderer();
			shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
		    shapeRenderer.begin(ShapeType.FilledCircle);
	   		shapeRenderer.setColor(new Color(0, 1, 0, 0.3f));
	   		shapeRenderer.filledCircle(getX()+getWidth()/2, getY()+getHeight()/2, range);
	   		shapeRenderer.end();
	   		Gdx.gl.glDisable(GL10.GL_BLEND);
	        batch.begin();
		}
	}
	
	public String getTowerName() {
		return towerName;
	}

	public boolean isHovered() {
		return isHovered;
	}

	public void setHovered(boolean isHovered) {
		this.isHovered = isHovered;
	}
	
}
