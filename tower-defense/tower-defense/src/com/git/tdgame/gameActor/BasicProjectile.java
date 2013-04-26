package com.git.tdgame.gameActor;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class BasicProjectile extends Projectile {

	public BasicProjectile(Actor source, Actor target)
	{
		super(source, target);
		damage = 10;
	}

	@Override
	void finish() {
		Enemy e= (Enemy)target;
		e.takeDamage(damage);
		this.remove();
	}

}
