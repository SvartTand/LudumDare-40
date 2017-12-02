package com.svarttand.ludumdare40.units;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;
import com.svarttand.ludumdare40.map.Hexagon;

public class Unit {
	
	private static final int ICON_RADIOUS = 16;
	
	private Hexagon currentPos;
	private Vector2 pos;
	private int movmentsLeft;
	private int health;
	
	private Hexagon destination;
	
	private UnitType type;
	//1 = players 2 = AI
	private int team;
	
	private LinkedList<Hexagon> movmentPath;
	
	public Unit(Hexagon hex, UnitType t){
		type = t;
		movmentsLeft = 0;
		health = type.getHp();
		currentPos = hex;
		pos = new Vector2(currentPos.getPosX() + ICON_RADIOUS ,currentPos.getPosY() + ICON_RADIOUS-2);
		movmentPath = new LinkedList<Hexagon>();
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
		pos.set(currentPos.getPosX() + ICON_RADIOUS ,currentPos.getPosY() + ICON_RADIOUS-2);
	}

	public int getMovmentsLeft() {
		// TODO Auto-generated method stub
		return movmentsLeft;
	}
	
	public UnitType getType(){
		return type;
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
	
	public void attack(Unit unit, UHandler handler){
		System.out.println("ATTACK!");
		health -= unit.getType().getDmg();
		unit.health -= type.getDmg();
		if (unit.health <= 0) {
			unit.remove(handler);
		}
		if (health <= 0) {
			remove(handler);
		}
		movmentsLeft = 0;
	}

	public void remove(UHandler handler) {
		currentPos.setUnit(null);
		handler.remove(this);
		
	}

	public void calculateRoute(ArrayList<Hexagon> cityList, ArrayList<Unit> friendlyUnitList) {
		float distance = calculateDistance(cityList.get(0),currentPos);
		destination = cityList.get(0);
		float temp = 0;
		for (int i = 0; i < cityList.size(); i++) {
			temp =calculateDistance(cityList.get(i),currentPos);
			if (distance >= temp) {
				distance = temp;
				destination = cityList.get(i);
			}
		}
		for (int i = 0; i < friendlyUnitList.size(); i++) {
			temp =calculateDistance(friendlyUnitList.get(i).getHex(),currentPos)*1.5f;
			if (distance >= temp) {
				distance = temp;
				destination = friendlyUnitList.get(i).getHex();
			}
		}
		calculatePath();
		
		
	}
	
	private void calculatePath(){
		ArrayList<Hexagon> closedSet = new ArrayList<Hexagon>();
		ArrayList<Hexagon> openSet = new ArrayList<Hexagon>();
		
		openSet.add(currentPos);
		currentPos.setParent(null);
		
		while(openSet.size() > 0){
			System.out.println("in pathFinding");
			Hexagon current = getWinner(openSet);
			if (current.isSame(destination)) {
				while(current != null){
					movmentPath.add(current);
					current = current.getParent();
				}
				break;
			}
			openSet.remove(current);
			closedSet.add(current);
			
			for (int i = 0; i < current.getNeighbors().size(); i++) {
				boolean alreadyCounted = false;
				for (int j = 0; j < closedSet.size(); j++) {
					if (closedSet.get(j).isSame(current.getNeighbors().get(i))) {
						alreadyCounted = true;
						break;
					}
				}
				if (!alreadyCounted) {
					for (int j = 0; j < closedSet.size(); j++) {
						if (closedSet.get(j).isSame(current.getNeighbors().get(i))) {
							alreadyCounted = true;
							break;
						}
					}
					if (!alreadyCounted) {
						openSet.add(current.getNeighbors().get(i));
						current.getNeighbors().get(i).setParent(current);
					}
				}
			}
		}
		for (int i = 0; i < movmentPath.size(); i++) {
			System.out.println(movmentPath.get(i));
		}
		movmentPath.removeLast();
			
	}
	
	private Hexagon getWinner(ArrayList<Hexagon> openSet) {
		Hexagon temp = openSet.get(0);
		for (int i = 0; i < openSet.size(); i++) {
			if (getFscore(temp)> getFscore(openSet.get(i))) {
				temp = openSet.get(i);
			}
		}
		return temp;
	}

	private float getFscore(Hexagon temp) {
		int gScore = 0;
		float hScore = calculateDistance(destination, temp);
		while(temp.getParent() != null){
			gScore += temp.getType().getMovmentCost();
			temp = temp.getParent();
		}
		return gScore + hScore;
	}

	public Hexagon getHex() {
		// TODO Auto-generated method stub
		return currentPos;
	}

	private float calculateDistance(Hexagon dest, Hexagon current){
		float x = Math.abs(current.getPosX() - dest.getPosX());
		float y = Math.abs(current.getPosY() - dest.getPosY());
		
		float distance = (float) Math.sqrt(Math.pow(x, 2)+ Math.pow(y, 2));
		return distance;
		
	}

	public void moveNext(EnemyUnitHandler handler) {
		while(movmentsLeft > 0){
			if (!movmentPath.isEmpty()) {
				if (movmentPath.getLast().getUnit() != null) {
					attack(movmentPath.getLast().getUnit(), handler);
				}else{
					move(movmentPath.getLast(), movmentPath.removeLast().getType().getMovmentCost());
				}
				
			}else {
				break;
			}
		}
		
		
		
	}
	

}
