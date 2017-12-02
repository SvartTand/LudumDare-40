package com.svarttand.ludumdare40.units;

public enum UnitType {
	WARRIOR("Warrior", 2, 1, 20,10);
	
	private String path;
	private int movments;
	private int range;
	private int hp;
	private int dmg;
	
	private UnitType(String p, int m, int r, int h, int d){
		path = p;
		movments = m;
		range = r;
		hp = h;
		dmg = d;
	}

	public String getPath() {
		return path;
	}

	public int getMovments() {
		return movments;
	}

	public int getRange() {
		return range;
	}

	public int getHp() {
		return hp;
	}

	public int getDmg() {
		return dmg;
	}
	
	

}
