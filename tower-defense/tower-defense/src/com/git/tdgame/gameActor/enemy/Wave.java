package com.git.tdgame.gameActor.enemy;

import java.util.ArrayList;

public class Wave {
	private float delay;
	private ArrayList<String> enemies;
	
	public Wave()
	{
		delay = 10;
		enemies = new ArrayList<String>();
		enemies.add("1");
		enemies.add("2");
		enemies.add("3");
		enemies.add("4");
	}
	
	public ArrayList<String> getEnemies()
	{
		return enemies;
	}
	
	public float getDelay()
	{
		return delay;
	}
}
