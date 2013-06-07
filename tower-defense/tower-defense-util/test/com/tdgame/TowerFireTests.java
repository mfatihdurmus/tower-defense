package com.tdgame;

import org.junit.Assert;
import org.junit.Test;

public class TowerFireTests {

	@Test
	public void testFire() {
		Enemy enemy = new Enemy();
		enemy.setHealth(10);
		
		Tower tower = new Tower();
		tower.setDamage(2);
		
		tower.fire(enemy);
		
		Assert.assertEquals(8, enemy.getHealth());
	}
	
	@Test
	public void testFireEnemyDie() {
		Enemy enemy = new Enemy();
		enemy.setHealth(10);
		enemy.setAlive(true);
		
		Tower tower = new Tower();
		tower.setDamage(2);
		
		int fireNeeded = enemy.getHealth()/tower.getDamage();
		for(int i=0; i<fireNeeded; i++){
			tower.fire(enemy);
			if(i<fireNeeded-1)
				Assert.assertTrue(enemy.isAlive());
		}
		
		Assert.assertFalse(enemy.isAlive());
	}

}
