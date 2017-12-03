package com.svarttand.ludumdare40.units;

public enum UnitType {
	WARRIOR("Infantry", 2, 1, 20,10, 10,5,0,false),
	WORKER("Worker", 2,0,10,0, 20,10,10,false),
	ARCHER("Archer", 2, 2,15,5,5,10,0,false),
	ARCHER_ENEMY("ArcherEnemy", 2, 2,15,5,5,10,0, true),
	WARRIOR_ENEMY("SnailAiliens", 2, 1, 20,10, 5,5,0, true),
	TANK("Tank", 2,1,50,15,20,20,20,false),
	TANK_ENEMY("BigBadAilien", 2,1,50,15,20,20,20,true);
	
	private String path;
	private int movments;
	private int range;
	private int hp;
	private int dmg;
	private int foodCost;
	private int woodCost;
	private int goldCost;
	private boolean enemy;
	
	private UnitType(String p, int m, int r, int h, int d, int food, int wood, int gold, boolean e){
		path = p;
		movments = m;
		range = r;
		hp = h;
		dmg = d;
		foodCost = food;
		goldCost = gold;
		woodCost = wood;
		enemy = e;
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

	public boolean isEnemy() {
		// TODO Auto-generated method stub
		return enemy;
	}
	
	
	

}
