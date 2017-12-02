package com.svarttand.ludumdare40.units;

import com.badlogic.gdx.math.Vector2;
import com.svarttand.ludumdare40.map.Hexagon;

public class Unit {
	
	private Hexagon currentPos;
	private Vector2 pos;
	private int movmentsLeft;
	private int health;
	
	private UnitType type;
	//1 = players 2 = AI
	private int team;
	
	public Unit(Hexagon hex, UnitType t, int team){
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
	
	private void updatePos(){
		pos.set(currentPos.getPosX() ,currentPos.getPosY());
	}

	public int getMovmentsLeft() {
		// TODO Auto-generated method stub
		return movmentsLeft;
	}

	public void move(Hexagon newPos, int movmentCost) {
		currentPos.setHasUnit(false);
		currentPos.setUnit(null);
		currentPos = newPos;
		movmentsLeft = movmentsLeft- movmentCost;
		System.out.println(movmentsLeft);
		currentPos.setUnit(this);
		currentPos.setHasUnit(true);
		updatePos();
		
	}
	

}
