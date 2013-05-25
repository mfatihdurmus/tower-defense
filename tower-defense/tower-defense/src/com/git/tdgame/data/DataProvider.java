package com.git.tdgame.data;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DataProvider {
	public static HashMap<String,HashMap<String,String>> getEnemyTypes(){
		Gson gson = new Gson();
		String enemyJson = Gdx.files.internal("data/game/enemy/enemy.json").readString();
		return gson.fromJson(enemyJson, new TypeToken<HashMap<String,HashMap<String,String>>>(){}.getType());
	}
	
	public static HashMap<String,HashMap<String,String>> getTowerTypes(){
		Gson gson = new Gson();
		String enemyJson = Gdx.files.internal("data/game/tower/tower.json").readString();
		return gson.fromJson(enemyJson, new TypeToken<HashMap<String,HashMap<String,String>>>(){}.getType());
	}
	
	//TODO wave 
}
