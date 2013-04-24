package com.git.tdgame.gameActor;

import com.badlogic.gdx.math.Vector2;

public class BasicTower extends Tower {

	public BasicTower(Vector2 position, Vector2 tileSize,
			int mapHeight) {
		super(position, tileSize, mapHeight);
	}

	@Override
	Projectile createProjectile() {
		return new BasicProjectile(this, target, tileSize);
	}

}
