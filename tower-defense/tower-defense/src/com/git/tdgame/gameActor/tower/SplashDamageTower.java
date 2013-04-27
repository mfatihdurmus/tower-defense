package com.git.tdgame.gameActor.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.git.tdgame.gameActor.projectile.AbstractProjectile;
import com.git.tdgame.gameActor.projectile.HomingProjectile;

public class SplashDamageTower extends AbstractTower {

	public SplashDamageTower(Vector2 position) {
		super(position);
    	texture = new Texture(Gdx.files.internal("data/game/tower/splashDamageTower.png"));
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture,WIDTH,HEIGHT);
        fireRate = 2;
        range = 150;
	}

	@Override
	AbstractProjectile createProjectile()
	{
		// TO DO : Make it generic
		// Take all properties in constructor
		// return new HomingProjectile(this, target, projectileProperties);

		HomingProjectile homingProjectile = new HomingProjectile(this, target);
		homingProjectile.setTexture(new Texture(Gdx.files.internal("data/game/projectile/cannonBall.png")));
		homingProjectile.setDamageRadius(32);
		homingProjectile.setSpeed(768);
		return homingProjectile;
	}

}
