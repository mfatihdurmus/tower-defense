package com.git.tdgame.gameActor.tower;

import com.badlogic.gdx.math.Vector2;
import com.git.tdgame.gameActor.projectile.AbstractProjectile;
import com.git.tdgame.gameActor.projectile.ConstantTargetProjectile;

public class SingleTargetTower extends AbstractTower {

	public SingleTargetTower(Vector2 position)
	{
		super(position);
	}

	@Override
	AbstractProjectile createProjectile()
	{
		// TO DO : Make it generic
		// Take all properties in constructor
		// return new HomingProjectile(this, target, projectileProperties);
		
		return new ConstantTargetProjectile(this, target);
	}

}
