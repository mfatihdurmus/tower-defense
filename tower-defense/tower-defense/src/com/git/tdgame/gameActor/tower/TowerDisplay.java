package com.git.tdgame.gameActor.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TowerDisplay extends Actor {
	
	private Tower mTower;
	private Texture texture;
	private Texture atkspeed;
	private Texture damage;
	private Texture range;
	private Sprite sprite;
	private int guiPosition;
	private BitmapFont font;
	
	
	public TowerDisplay( int guiPosition ) {
		mTower = null;
		
		texture = new Texture(Gdx.files.internal("data/menu/displaybackground.jpg"));
		atkspeed = new Texture(Gdx.files.internal("data/menu/atkspeed.png"));
		damage = new Texture(Gdx.files.internal("data/menu/damage.png"));
		range = new Texture(Gdx.files.internal("data/menu/range.png"));
		
		texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        sprite = new Sprite(texture);
        
        this.guiPosition = guiPosition;
        
        font = new BitmapFont(Gdx.files.internal("data/menu/font.fnt"), true);
        font.setColor(0, 0, 0, 1);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		for (int i = 0; i < getWidth() / 128; i++) {
			batch.draw(sprite,i*128,getY()+getHeight(),getOriginX(),getOriginY(),128,getHeight(),1,-1,0);
		}
		
		if(mTower != null)
		{
			// Damage
			batch.draw(damage, guiPosition, 0, atkspeed.getWidth(), atkspeed.getHeight(), 0, 0, atkspeed.getWidth(), atkspeed.getHeight(), false, true);
			String damageString = String.format("Damage:%d", mTower.getDamage());
			font.draw(batch,damageString, guiPosition + 64, 16);
			
			// Attack Speed
			batch.draw(atkspeed, guiPosition + (getStage().getWidth() - guiPosition-90)/3, 0, atkspeed.getWidth(), atkspeed.getHeight(), 0, 0, atkspeed.getWidth(), atkspeed.getHeight(), false, true);
			String speedString = String.format("Speed:%.2f", (1/mTower.getFireRate()));
			font.draw(batch,speedString, guiPosition + (getStage().getWidth() - guiPosition-90)/3 + 64, 16);

			// Range
			batch.draw(range, guiPosition + (getStage().getWidth() - guiPosition-90)*2/3, 0, atkspeed.getWidth(), atkspeed.getHeight(), 0, 0, atkspeed.getWidth(), atkspeed.getHeight(), false, true);
			String rangeString = String.format("Range:%.0f", mTower.getRange());
			font.draw(batch,rangeString, guiPosition + (getStage().getWidth() - guiPosition-90)*2/3 + 64, 16);
		}
	}

	public Tower getSelectedTower() {
		return mTower;
	}

	public void setSelectedTower(Tower mTower) {
		this.mTower = mTower;
	}
	
	
}
