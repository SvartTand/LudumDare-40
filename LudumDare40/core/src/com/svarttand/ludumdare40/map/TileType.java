package com.svarttand.ludumdare40.map;

public enum TileType {
	GRASS("GrassHex", 1,0,1,0, 0.8f, 1.2f),
	CITY("CityHex", 1,1,1,1, 1.3f, 1.3f),
	GOLD("GoldHex", 1,2,0,0,1.1f,1),
	FOOD("FoodHex", 1,0,2,0,0.8f, 1.2f),
	DESSERT("DessertHex", 2,0,0,0, 0.5f, 0.5f),
	WOOD("WoodHex", 2,0,1,2, 1.4f,1.2f),
	CAMP("CampHex", 2,0,0,0,1.5f,1.2f);
	
	
	private String path;
	private int movmentCost;
	private int gold;
	private int food;
	private int wood;
	
	private float defAmplifier;
	private float attackAmplifier;
	
	
	private TileType(String p, int cost, int go, int fo, int wo, float def, float attack){
		path = p;
		movmentCost = cost;
		gold = go;
		food = fo;
		wood = wo;
		defAmplifier = def;
		attackAmplifier = attack;
	}

	public String getPath() {
		return path;
	}
	
	public int getMovmentCost(){
		return movmentCost;
	}

	public int getGold() {
		return gold;
	}

	public int getFood() {
		return food;
	}

	public int getWood() {
		return wood;
	}

	public float getDefAmplifier() {
		return defAmplifier;
	}

	public float getAttackAmplifier() {
		return attackAmplifier;
	}
	
	
	

}
