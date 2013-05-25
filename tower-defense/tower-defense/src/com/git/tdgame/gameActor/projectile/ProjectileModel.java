package com.git.tdgame.gameActor.projectile;


public class ProjectileModel {
	
	int width;
	int height;
	float speed;
	int damage;
	float damageRadius;
	float slowAmount;
	float slowDuration;
	String texturePath;
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public float getDamageRadius() {
		return damageRadius;
	}
	public void setDamageRadius(float damageRadius) {
		this.damageRadius = damageRadius;
	}
	public float getSlowAmount() {
		return slowAmount;
	}
	public void setSlowAmount(float slowAmount) {
		this.slowAmount = slowAmount;
	}
	public float getSlowDuration() {
		return slowDuration;
	}
	public void setSlowDuration(float slowDuration) {
		this.slowDuration = slowDuration;
	}
	public String getTexturePath() {
		return texturePath;
	}
	public void setTexturePath(String texturePath) {
		this.texturePath = texturePath;
	}
	@Override
	public String toString() {
		return "ProjectileModel [width=" + width + ", heigth=" + height
				+ ", speed=" + speed + ", damage=" + damage + ", damageRadius="
				+ damageRadius + ", slowAmount=" + slowAmount
				+ ", slowDuration=" + slowDuration + ", texturePath="
				+ texturePath + "]";
	}
}
