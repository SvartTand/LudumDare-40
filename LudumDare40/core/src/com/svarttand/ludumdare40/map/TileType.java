package com.svarttand.ludumdare40.map;

public enum TileType {
	GRASS("GrassHex");
	
	private String path;
	
	private TileType(String p){
		path = p;
	}

	public String getPath() {
		return path;
	}
	

}
