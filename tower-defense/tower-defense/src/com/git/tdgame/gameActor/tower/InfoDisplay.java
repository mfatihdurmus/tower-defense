package com.git.tdgame.gameActor.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.git.tdgame.gameActor.level.Enemy;

public class InfoDisplay extends Actor {
	
	private Texture texture;
	private Texture attackSpeed;
	private Texture movementSpeed;
	private Texture damage;
	private Texture range;
	private Texture health;
	private Sprite sprite;
	private int guiPosition;
	private BitmapFont font;
	private Actor actor;
	
	public InfoDisplay( int guiPosition )
	{
		actor = null;
		
		texture = new Texture(Gdx.files.internal("data/menu/displaybackground.jpg"));
		attackSpeed = new Texture(Gdx.files.internal("data/menu/attackSpeed.png"));
		movementSpeed = new Texture(Gdx.files.internal("data/menu/movementSpeed.png"));
		damage = new Texture(Gdx.files.internal("data/menu/damage.png"));
		range = new Texture(Gdx.files.internal("data/menu/range.png"));
		health = new Texture(Gdx.files.internal("data/menu/health.png"));
		
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
		
		if(actor != null)
		{
			if(actor instanceof Tower)
			{
				Tower tower = (Tower) actor;
				
				if(tower.isSold())
				{
					actor = null;
				} else {
				
					// Damage
					batch.draw(damage, guiPosition, 0, damage.getWidth(), damage.getHeight(), 0, 0, damage.getWidth(), damage.getHeight(), false, true);
					String damageString = String.format("Damage:%d", tower.getDamage());
					font.draw(batch,damageString, guiPosition + 64, 16);
					
					// Attack Speed
					batch.draw(attackSpeed, guiPosition + (getStage().getWidth() - guiPosition-90)/3, 0, attackSpeed.getWidth(), attackSpeed.getHeight(), 0, 0, attackSpeed.getWidth(), attackSpeed.getHeight(), false, true);
					String speedString = String.format("Freq:%.2f", (1/tower.getFireRate()));
					font.draw(batch,speedString, guiPosition + (getStage().getWidth() - guiPosition-90)/3 + 64, 16);
		
					// Range
					batch.draw(range, guiPosition + (getStage().getWidth() - guiPosition-90)*2/3, 0, range.getWidth(), range.getHeight(), 0, 0, range.getWidth(), range.getHeight(), false, true);
					String rangeString = String.format("Range:%.0f", tower.getRange());
					font.draw(batch,rangeString, guiPosition + (getStage().getWidth() - guiPosition-90)*2/3 + 64, 16);
				}
				
			} else if(actor instanceof Enemy)
			{
				Enemy enemy = (Enemy) actor;
				if(!enemy.isAlive())
				{
					actor = null;
				} else {
				
					// Damage
					batch.draw(damage, guiPosition, 0, damage.getWidth(), damage.getHeight(), 0, 0, damage.getWidth(), damage.getHeight(), false, true);
					String damageString = String.format("Damage:%d", enemy.getDamage());
					font.draw(batch,damageString, guiPosition + 64, 16);
					
					// Movement Speed
					batch.draw(movementSpeed, guiPosition + (getStage().getWidth() - guiPosition-90)/3, 0, movementSpeed.getWidth(), movementSpeed.getHeight(), 0, 0, movementSpeed.getWidth(), movementSpeed.getHeight(), false, true);
					String speedString = String.format("Speed:%d", ((int)enemy.getSpeed()));
					font.draw(batch,speedString, guiPosition + (getStage().getWidth() - guiPosition-90)/3 + 64, 16);
		
					// Health
					batch.draw(health, guiPosition + (getStage().getWidth() - guiPosition-90)*2/3, 0, health.getWidth(), health.getHeight(), 0, 0, health.getWidth(), health.getHeight(), false, true);
					String rangeString = String.format("Health:%d", enemy.getCurrentHealth());
					font.draw(batch,rangeString, guiPosition + (getStage().getWidth() - guiPosition-90)*2/3 + 64, 16);
				}
			}
		}
	}

	public Actor getSelectedActor() {
		return actor;
	}

	public void setSelectedActor(Actor actor) {
		this.actor = actor;
	}
	
	
}
