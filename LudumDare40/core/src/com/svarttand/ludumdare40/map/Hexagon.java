package com.svarttand.ludumdare40.map;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Hexagon {
	
	
	private ArrayList<Hexagon> neighbors;
	float posX;
	float posY;
	
	private TileType type;
	private BorderType b_Type;
	
	private Vector2[] verticies;
	
	public Hexagon(float x, float y, TileType t){
		posX = x;
		posY = y;
		neighbors = new ArrayList<Hexagon>();
		type = t;
		b_Type = BorderType.NULL;
		verticies = new Vector2[6];
		addVerticies();
	}
	
	public void addNeighbor(Hexagon hex){
		neighbors.add(hex);
	}
	
	public String getPath(){
		return type.getPath();
	}
	
	public void setSelected(BorderType t){
		b_Type = t;
	}
	
	public BorderType getBorderType(){
		return b_Type;
	}
	
	private void addVerticies(){
		verticies[0] = new Vector2(posX + HexagonMap.HEX_WIDTH, posY + HexagonMap.HEX_HEIGHT*0.5f);
		verticies[1] = new Vector2(posX + (HexagonMap.HEX_WIDTH * 0.75f), posY);
		verticies[2] = new Vector2(posX + (HexagonMap.HEX_WIDTH * 0.25f), posY);
		verticies[3] = new Vector2(posX , posY + HexagonMap.HEX_HEIGHT*0.5f);
		verticies[4] = new Vector2(posX + (HexagonMap.HEX_WIDTH * 0.25f), posY + HexagonMap.HEX_HEIGHT);
		verticies[5] = new Vector2(posX + (HexagonMap.HEX_WIDTH * 0.75f), posY + HexagonMap.HEX_HEIGHT);
	}


	public Vector2[] getVerticies() {
		return verticies;
	}
	
	public String toString(){
		return type.getPath() + ", " + posX + ", " + posY;
	}
	

}
