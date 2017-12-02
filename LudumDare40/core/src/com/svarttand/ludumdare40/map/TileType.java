package com.svarttand.ludumdare40.map;

public enum TileType {
	GRASS("GrassHex", 1,0,1,0),
	CITY("CityHex", 1,1,1,1),
	GOLD("GoldHex", 1,5,0,0),
	FOOD("FoodHex", 1,0,5,0),
	DESSERT("DessertHex", 2,0,0,0),
	WOOD("WoodHex", 2,0,1,5);
	
	private String path;
	private int movmentCost;
	private int gold;
	private int food;
	private int wood;
	
	
	private TileType(String p, int cost, int go, int fo, int wo){
		path = p;
		movmentCost = cost;
		gold = go;
		food = fo;
		wood = wo;
	}

	public String getPath() {
		return path;
	}
	
	public int getMovmentCost(){
		return movmentCost;
	}
	

}
