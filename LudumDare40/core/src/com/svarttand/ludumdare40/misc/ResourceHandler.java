package com.svarttand.ludumdare40.misc;

public class ResourceHandler {
	
	private int wood;
	private int gold;
	private int food;
	
	public ResourceHandler(int wood, int gold, int food){
		this.wood = wood;
		this.gold = gold;
		this.food = food;
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}
	

}
