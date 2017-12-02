package com.svarttand.ludumdare40.units;

public enum UnitType {
	WARRIOR("Warrior", 2, 1, 20,10, 5,5,0),
	WORKER("Worker", 2,0,10,0, 5,10,0),
	ARCHER("Archer", 2, 2,15,5,5,10,0),
	ARCHER_ENEMY("ArcherEnemy", 2, 2,15,5,5,10,0),
	WARRIOR_ENEMY("WarriorEnemy", 2, 1, 20,10, 5,5,0);
	
	private String path;
	private int movments;
	private int range;
	private int hp;
	private int dmg;
	private int foodCost;
	private int woodCost;
	private int goldCost;
	
	private UnitType(String p, int m, int r, int h, int d, int food, int wood, int gold){
		path = p;
		movments = m;
		range = r;
		hp = h;
		dmg = d;
		foodCost = food;
		goldCost = gold;
		woodCost = wood;
	}

	public int getFoodCost() {
		return foodCost;
	}

	public int getWoodCost() {
		return woodCost;
	}

	public int getGoldCost() {
		return goldCost;
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
