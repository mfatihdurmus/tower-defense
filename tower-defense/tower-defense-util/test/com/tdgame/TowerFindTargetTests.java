package com.tdgame;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class TowerFindTargetTests {

	@Test
	public void testFindTargetNotInRange() {
		Enemy enemy = new Enemy();
		enemy.setX(6);
		enemy.setY(6);
		enemy.setTraveledDist(1);
		ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
		enemyList.add(enemy);

		Tower tower = new Tower();
		tower.setRange(5);
		tower.setX(0);
		tower.setY(0);
		tower.setEnemies(enemyList);
		
		Enemy target = tower.findTarget();
		
		Assert.assertNull(target);
	}
	
	@Test
	public void testFindTargetInRange() {
		Enemy enemy = new Enemy();
		enemy.setX(3);
		enemy.setY(3);
		enemy.setTraveledDist(1);
		ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
		enemyList.add(enemy);

		Tower tower = new Tower();
		tower.setRange(5);
		tower.setX(0);
		tower.setY(0);
		tower.setEnemies(enemyList);
		
		Enemy target = tower.findTarget();
		
		Assert.assertNotNull(target);
	}
	
	@Test
	public void testFindTargetNearestToBase() {
		ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
		
		Enemy enemy1 = new Enemy();
		enemy1.setX(3);
		enemy1.setY(3);
		enemy1.setTraveledDist(1);
		enemyList.add(enemy1);
		
		Enemy enemy2 = new Enemy();
		enemy2.setX(2);
		enemy2.setY(2);
		enemy2.setTraveledDist(3);
		enemyList.add(enemy2);

		Tower tower = new Tower();
		tower.setRange(5);
		tower.setX(0);
		tower.setY(0);
		tower.setEnemies(enemyList);
		
		Enemy target = tower.findTarget();
		
		Assert.assertEquals(target, enemy2);
	}
}
