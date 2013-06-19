package com.git.tdgame.gameActor.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TowerRemoveButton extends Actor {
	
	private Tower mTower;
	private Texture texture;
	private Sprite sprite;
	private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private int cost;

	public TowerRemoveButton( Tower t ) {
		mTower = t;
		
		texture = new Texture(Gdx.files.internal("data/game/remove.png"));
		setHeight(texture.getHeight());
		setWidth(texture.getWidth());
        sprite = new Sprite(texture);
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont(true);
        font.setColor(0, 0, 0, 1);
        cost = 0;
	}
	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(sprite,getX(),getY()+getHeight(),getOriginX(),getOriginY(),getWidth(),getHeight(),1,-1,0);
		
    	batch.end();
        shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
	    shapeRenderer.begin(ShapeType.FilledRectangle);
   		shapeRenderer.setColor(new Color(1,1,1,1));
   		shapeRenderer.filledRect(getX()+7, getY()-13, getWidth()-15, 17);
   		shapeRenderer.end();
	    shapeRenderer.begin(ShapeType.Rectangle);
   		shapeRenderer.setColor(new Color(0,0,0,1));
   		shapeRenderer.rect(getX()+7, getY()-13, getWidth()-15, 17);
   		shapeRenderer.end();
        batch.begin();
		font.draw(batch, new String(cost + " g"), getX()+8, getY()-13);
	}
	
	
	public Tower getTower()
	{
		return mTower;
	}
}
