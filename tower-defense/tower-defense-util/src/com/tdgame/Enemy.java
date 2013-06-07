package com.tdgame;

public class Enemy {
	int x;
	int y;
	int health;
	int traveledDist;
	boolean alive;
	
	public void act(){
		if(x <= 0)
			x--;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getTraveledDist() {
		return traveledDist;
	}
	public void setTraveledDist(int traveledDist) {
		this.traveledDist = traveledDist;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	
	
	
}
