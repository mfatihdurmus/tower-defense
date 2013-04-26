package com.git.tdgame.gameActor.tower;

import com.badlogic.gdx.math.Vector2;
import com.git.tdgame.gameActor.projectile.AbstractProjectile;
import com.git.tdgame.gameActor.projectile.ConstantTargetProjectile;

public class BasicTower extends AbstractTower {

	public BasicTower(Vector2 position) {
		super(position);
	}

	@Override
	AbstractProjectile createProjectile() {
		return new ConstantTargetProjectile(this, target);
	}

}
