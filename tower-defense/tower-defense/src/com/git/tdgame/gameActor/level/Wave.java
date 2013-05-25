package com.git.tdgame.gameActor.level;

import java.util.List;

public class Wave {
	private float delay;
	private List<String> enemies;

	public float getDelay() {
		return delay;
	}
	public void setDelay(float delay) {
		this.delay = delay;
	}
	public List<String> getEnemies() {
		return enemies;
	}
	public void setEnemies(List<String> enemies) {
		this.enemies = enemies;
	}
	@Override
	public String toString() {
		return "Wave [delay=" + delay + ", enemies=" + enemies + "]";
	}
	
}
