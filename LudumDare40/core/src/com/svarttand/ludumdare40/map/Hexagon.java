package com.svarttand.ludumdare40.map;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.svarttand.ludumdare40.units.Unit;
import com.svarttand.ludumdare40.units.UnitType;

public class Hexagon {
	
	
	private ArrayList<Hexagon> neighbors;
	float posX;
	float posY;
	
	private TileType type;
	private BorderType b_Type;
	private TileType previousType;
	private BorderType previousBorder;
	
	private Vector2[] verticies;
	
	private Unit unit;
	private boolean hasUnit;
	
	private int distanceFromStart;
	private Hexagon parent;
	
	private int i;
	private int j;
	
	public Hexagon(float x, float y,int i, int j, TileType t){
		posX = x;
		posY = y;
		neighbors = new ArrayList<Hexagon>();
		type = t;
		previousType = t;
		b_Type = BorderType.NULL;
		previousBorder = BorderType.NULL;
		verticies = new Vector2[6];
		addVerticies();
		hasUnit = false;
		distanceFromStart = 0;
		this.i = i;
		this.j = j;
	}
	
	public void addNeighbor(Hexagon hex){
		neighbors.add(hex);
	}
	
	public String getPath(){
		return type.getPath();
	}
	
	public void setSelected(BorderType t){
		previousBorder = b_Type;
		b_Type = t;
	}
	
	public BorderType getBorderType(){
		return b_Type;
	}
	
//	public BorderType getPrevBorder(){
//		return previousBorder;
//	}
	
	public float getPosX(){
		return posX;
	}
	public float getPosY(){
		return posY;
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
	
	public String getPos(){
		return "" + i + ", " + j;
	}
	
	public String toString(){
		return type.getPath() + "\n" + "Movment cost " + type.getMovmentCost() + ", Provides: \nFood: " + type.getFood() + ", Wood: " + type.getWood() + ", Gold: " + type.getGold() ;
	}
	
	public boolean hasUnit(){
		return hasUnit;
	}
	
	public TileType getType(){
		return type;
	}
	
	public Unit addUnit(UnitType t){
		unit = new Unit(this, t);
		setHasUnit(true);
		return unit;
	}
	
	public void setUnit(Unit unit){
		this.unit = unit;
	}
	
	public void setHasUnit(boolean b){
		hasUnit = b;
	}

	public Unit getUnit() {
		return unit;
	}

	public ArrayList<Hexagon> getNeighbors() {
		// TODO Auto-generated method stub
		return neighbors;
	}
	
	public void setDistance(int dist){
		distanceFromStart = dist;
	}
	
	public int getDistanceFromStart(){
		return distanceFromStart;
	}

	public boolean isSame(Hexagon otherHex) {
		if (posX == otherHex.getPosX() && posY == otherHex.getPosY()) {
			return true;
		}
		return false;
	}

	public void setType(TileType tileType) {
		type = tileType;
		
	}
	
	public void setParent(Hexagon parent){
		this.parent = parent;
	}
	
	public Hexagon getParent(){
		return parent;
	}

	public TileType getPreviousType() {
		return previousType;
	}
	

}
