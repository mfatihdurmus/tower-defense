package com.tdgame;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class TowerActTests {

	@Test
	public void testActRangeOneShot() {
		Enemy enemy = new Enemy();
		enemy.setX(6);
		enemy.setY(5);
		enemy.setTraveledDist(1);
		enemy.setHealth(10);
		enemy.setAlive(true);
		ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
		enemyList.add(enemy);

		Tower tower = new Tower();
		tower.setRange(5);
		tower.setX(3);
		tower.setY(0);
		tower.setDamage(5);
		tower.setTimeToFire(1);
		tower.setFireRate(1);
		tower.setEnemies(enemyList);
		
		for(int i=0; i<6; i++){
			enemy.act();
			tower.act(1);
			if(enemy.getX() == 3)
				Assert.assertEquals(5, enemy.getHealth());
			else if(enemy.getX() > 3){
				Assert.assertEquals(10, enemy.getHealth());
			}else {
				Assert.assertEquals(5, enemy.getHealth());
			}
		}
	}
	
	@Test
	public void testActEnemyMustDie() {
		Enemy enemy = new Enemy();
		enemy.setX(6);
		enemy.setY(4);
		enemy.setTraveledDist(1);
		enemy.setHealth(10);
		enemy.setAlive(true);
		ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
		enemyList.add(enemy);

		Tower tower = new Tower();
		tower.setRange(5);
		tower.setX(3);
		tower.setY(0);
		tower.setDamage(5);
		tower.setTimeToFire(0);
		tower.setFireRate(1);
		tower.setEnemies(enemyList);
		
		for(int i=0; i<6; i++){
			enemy.act();
			tower.act(1);
		}
		
		Assert.assertFalse(enemy.isAlive());
	}
	
	@Test
	public void testActEnemyEscapeFireLowFireRate() {
		Enemy enemy = new Enemy();
		enemy.setX(6);
		enemy.setY(4);
		enemy.setTraveledDist(1);
		enemy.setHealth(10);
		enemy.setAlive(true);
		ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
		enemyList.add(enemy);

		Tower tower = new Tower();
		tower.setRange(5);
		tower.setX(3);
		tower.setY(0);
		tower.setDamage(5);
		tower.setTimeToFire(0);
		tower.setFireRate(10);
		tower.setEnemies(enemyList);
		
		for(int i=0; i<6; i++){
			enemy.act();
			tower.act(1);
		}
		
		Assert.assertTrue(enemy.isAlive());
	}
	
	@Test
	public void testActEnemyOneEscapeOneDie() {
		ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
		
		Enemy enemy1 = new Enemy();
		enemy1.setX(6);
		enemy1.setY(4);
		enemy1.setTraveledDist(2);
		enemy1.setHealth(10);
		enemy1.setAlive(true);
		enemyList.add(enemy1);
		
		Enemy enemy2 = new Enemy();
		enemy2.setX(7);
		enemy2.setY(4);
		enemy2.setTraveledDist(1);
		enemy2.setHealth(10);
		enemy2.setAlive(true);
		enemyList.add(enemy2);

		Tower tower = new Tower();
		tower.setRange(5);
		tower.setX(3);
		tower.setY(0);
		tower.setDamage(5);
		tower.setTimeToFire(0);
		tower.setFireRate(1);
		tower.setEnemies(enemyList);
		
		for(int i=0; i<6; i++){
			enemy1.act();
			enemy2.act();
			tower.act(1);
		}
		
		if(!enemy1.isAlive() && enemy2.isAlive())
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);
	}
	
	@Test
	public void testActOneShotPerTower() {
		Enemy enemy = new Enemy();
		enemy.setX(6);
		enemy.setY(5);
		enemy.setTraveledDist(1);
		enemy.setHealth(10);
		enemy.setAlive(true);
		ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
		enemyList.add(enemy);

		Tower tower = new Tower();
		tower.setRange(5);
		tower.setX(3);
		tower.setY(0);
		tower.setDamage(5);
		tower.setTimeToFire(1);
		tower.setFireRate(1);
		tower.setEnemies(enemyList);
		
		Tower tower2 = new Tower();
		tower2.setRange(5);
		tower2.setX(2);
		tower2.setY(0);
		tower2.setDamage(5);
		tower2.setTimeToFire(1);
		tower2.setFireRate(1);
		tower2.setEnemies(enemyList);
		
		for(int i=0; i<6; i++){
			enemy.act();
			tower.act(1);
			tower2.act(1);
			if(enemy.getX() == 3)
				Assert.assertEquals(5, enemy.getHealth());
			else if(enemy.getX() > 3){
				Assert.assertTrue(enemy.isAlive());
			}else if(enemy.getX() == 2){
				Assert.assertFalse(enemy.isAlive());
			}
		}
	}
	
	@Test
	public void testActTwoTowerFrontManDies() {
		ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
		
		Enemy enemy1 = new Enemy();
		enemy1.setX(6);
		enemy1.setY(4);
		enemy1.setTraveledDist(2);
		enemy1.setHealth(10);
		enemy1.setAlive(true);
		enemyList.add(enemy1);
		
		Enemy enemy2 = new Enemy();
		enemy2.setX(7);
		enemy2.setY(4);
		enemy2.setTraveledDist(1);
		enemy2.setHealth(10);
		enemy2.setAlive(true);
		enemyList.add(enemy2);

		Tower tower = new Tower();
		tower.setRange(5);
		tower.setX(3);
		tower.setY(0);
		tower.setDamage(2);
		tower.setTimeToFire(1);
		tower.setFireRate(1);
		tower.setEnemies(enemyList);
		
		Tower tower2 = new Tower();
		tower2.setRange(5);
		tower2.setX(2);
		tower2.setY(0);
		tower2.setDamage(2);
		tower2.setTimeToFire(1);
		tower2.setFireRate(1);
		tower2.setEnemies(enemyList);
		
		for(int i=0; i<6; i++){
			enemy1.act();
			enemy2.act();
			tower.act(1);
			tower2.act(1);
		}
		
		if(!enemy1.isAlive() && enemy2.isAlive())
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);
		
	}

}

