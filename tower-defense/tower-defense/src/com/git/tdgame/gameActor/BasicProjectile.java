package com.git.tdgame.gameActor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BasicProjectile extends Projectile {

	public BasicProjectile(Actor source, Actor target, Vector2 tileSize) {
		super(source, target, tileSize);
		damage = 10;
	}

	@Override
	void finish() {
		Enemy e= (Enemy)target;
		e.takeDamage(damage);
		this.remove();
	}

}
