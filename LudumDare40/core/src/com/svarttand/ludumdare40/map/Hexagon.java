package com.svarttand.ludumdare40.map;

import java.util.ArrayList;

public class Hexagon {
	
	
	private ArrayList<Hexagon> neighbors;
	float posX;
	float posY;
	
	private TileType type;
	
	public Hexagon(float x, float y, TileType t){
		posX = x;
		posY = y;
		neighbors = new ArrayList<Hexagon>();
		type = t;
	}
	
	public void addNeighbor(Hexagon hex){
		neighbors.add(hex);
	}
	
	public String getPath(){
		return type.getPath();
	}
	

}
