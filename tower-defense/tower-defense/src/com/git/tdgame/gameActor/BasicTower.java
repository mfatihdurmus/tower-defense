package com.git.tdgame.gameActor;

import com.badlogic.gdx.math.Vector2;

public class BasicTower extends Tower {

	public BasicTower(Vector2 position) {
		super(position);
	}

	@Override
	Projectile createProjectile() {
		return new BasicProjectile(this, target);
	}

}
