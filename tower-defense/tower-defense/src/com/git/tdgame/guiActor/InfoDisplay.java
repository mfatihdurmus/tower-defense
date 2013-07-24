package com.git.tdgame.guiActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.git.tdgame.gameActor.Enemy;
import com.git.tdgame.gameActor.tower.MainTower;
import com.git.tdgame.gameActor.tower.Tower;

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
	private Vector2 defaultSize;
	
	public InfoDisplay( int guiPosition )
	{
		actor = null;
		
		texture = new Texture(Gdx.files.internal("data/game/gui/displaybackground.jpg"));
		attackSpeed = new Texture(Gdx.files.internal("data/game/gui/attackSpeed.png"));
		movementSpeed = new Texture(Gdx.files.internal("data/game/gui/movementSpeed.png"));
		damage = new Texture(Gdx.files.internal("data/game/gui/damage.png"));
		range = new Texture(Gdx.files.internal("data/game/gui/range.png"));
		health = new Texture(Gdx.files.internal("data/game/gui/health.png"));
		defaultSize = new Vector2(32, 32);
		
		texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        sprite = new Sprite(texture);
        
        this.guiPosition = guiPosition;
        
        font = new BitmapFont(Gdx.files.internal("data/game/gui/font.fnt"), true);
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
					batch.draw(damage, guiPosition, 16, defaultSize.x, defaultSize.y, 0, 0, damage.getWidth(), damage.getHeight(), false, true);
					String damageString = String.format("Dmg:%d", tower.getDamage());
					font.draw(batch,damageString, guiPosition + 34, 22);
					
					// Attack Speed
					batch.draw(attackSpeed, guiPosition + (getStage().getWidth() - guiPosition-90)/3, 16, defaultSize.x, defaultSize.y, 0, 0, attackSpeed.getWidth(), attackSpeed.getHeight(), false, true);
					String speedString = String.format("Frq:%.2f", (1/tower.getFireRate()));
					font.draw(batch,speedString, guiPosition + (getStage().getWidth() - guiPosition-90)/3 + 34, 22);
		
					// Range
					batch.draw(range, guiPosition + (getStage().getWidth() - guiPosition-90)*2/3, 16, defaultSize.x, defaultSize.y, 0, 0, range.getWidth(), range.getHeight(), false, true);
					String rangeString = String.format("Rng:%.0f", tower.getRange());
					font.draw(batch,rangeString, guiPosition + (getStage().getWidth() - guiPosition-90)*2/3 + 34, 22);
				}
				
			} else if(actor instanceof Enemy)
			{
				Enemy enemy = (Enemy) actor;
				if(!enemy.isAlive())
				{
					actor = null;
				} else {
				
					// Damage
					batch.draw(damage, guiPosition, 16, defaultSize.x, defaultSize.y, 0, 0, damage.getWidth(), damage.getHeight(), false, true);
					String damageString = String.format("Dmg:%d", enemy.getDamage());
					font.draw(batch,damageString, guiPosition + 34, 22);
					
					// Movement Speed
					batch.draw(movementSpeed, guiPosition + (getStage().getWidth() - guiPosition-90)/3, 16, defaultSize.x, defaultSize.y, 0, 0, movementSpeed.getWidth(), movementSpeed.getHeight(), false, true);
					String speedString = String.format("Spd:%d", ((int)enemy.getSpeed()));
					font.draw(batch,speedString, guiPosition + (getStage().getWidth() - guiPosition-90)/3 + 34, 22);
		
					// Health
					batch.draw(health, guiPosition + (getStage().getWidth() - guiPosition-90)*2/3, 16, defaultSize.x, defaultSize.y, 0, 0, health.getWidth(), health.getHeight(), false, true);
					String healthString = String.format("Hlth:%d", enemy.getCurrentHealth());
					font.draw(batch,healthString, guiPosition + (getStage().getWidth() - guiPosition-90)*2/3 + 34, 22);
				}
			} else if(actor instanceof MainTower) {
				MainTower base = (MainTower) actor;
				
				// Health
				batch.draw(health, guiPosition, 16, defaultSize.x, defaultSize.y, 0, 0, health.getWidth(), health.getHeight(), false, true);
				String healthString = String.format("Health:%d", base.getCurrentHealth());
				font.draw(batch,healthString, guiPosition + 34, 22);
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
