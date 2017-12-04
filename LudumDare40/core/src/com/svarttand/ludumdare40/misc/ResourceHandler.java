package com.svarttand.ludumdare40.misc;

import java.util.ArrayList;

import com.svarttand.ludumdare40.map.Hexagon;
import com.svarttand.ludumdare40.units.UnitType;

public class ResourceHandler {
	
	private int wood;
	private int gold;
	private int food;
	
	
	public ResourceHandler(int food, int gold, int wood){
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

	public void removeCost(UnitType type) {
		food = food - type.getFoodCost();
		wood = wood - type.getWoodCost();
		gold = gold - type.getGoldCost();
		
	}

	public void addTilesResourcers(Hexagon hexagon) {
		food += hexagon.getType().getFood();
		wood += + hexagon.getType().getWood();
		gold += + hexagon.getType().getGold();
		
	}
	

}
