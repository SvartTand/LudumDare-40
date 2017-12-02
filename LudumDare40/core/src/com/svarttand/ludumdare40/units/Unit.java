package com.svarttand.ludumdare40.units;

import com.badlogic.gdx.math.Vector2;
import com.svarttand.ludumdare40.map.Hexagon;

public class Unit {
	
	private Hexagon currentPos;
	private Vector2 pos;
	private int movmentsLeft;
	private int health;
	
	private UnitType type;
	
	public Unit(Hexagon hex, UnitType t){
		type = t;
		movmentsLeft = 0;
		health = type.getHp();
		currentPos = hex;
		pos = new Vector2(currentPos.getPosX() ,currentPos.getPosY());
	}
	
	public void update(){
		movmentsLeft = type.getMovments();
	}
	
	public String getPath(){
		return type.getPath();
	}
	
	public Vector2 getPos(){
		return pos;
	}
	

}
