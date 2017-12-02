package com.svarttand.ludumdare40.map;

public enum TileType {
	GRASS("GrassHex", 1);
	
	private String path;
	private int movmentCost;
	
	private TileType(String p, int cost){
		path = p;
		movmentCost = cost;
	}

	public String getPath() {
		return path;
	}
	
	public int getMovmentCost(){
		return movmentCost;
	}
	

}
