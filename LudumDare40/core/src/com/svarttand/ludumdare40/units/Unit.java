package com.svarttand.ludumdare40.units;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

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
		currentPos.setUnit(this);
		currentPos.setHasUnit(true);
		updatePos();
		
	}
	
	public void attack(Hexagon hex, UHandler handlerDef, UHandler handlerAttack){
		Random random = new Random();
		int rand1 = random.nextInt((type.getDmg() - 5)) + 5;
		int rand2 = random.nextInt((type.getDmg() - 7)) + 7;
		System.out.println("ATTACK!");
		health -= rand2 * hex.getUnit().getHex().getType().getDefAmplifier();
		hex.getUnit().health -= rand1 * currentPos.getType().getAttackAmplifier();
		System.out.println("Results Attacker Hp = " + health + ", dmg dealt: " + rand1);
		System.out.println("Results Defender Hp = " + hex.getUnit().health + ", dmg dealt: " + rand2);
		if (health <= 0 && hex.getUnit().health <= 0) {
			if (health > hex.getUnit().health) {
				health = 1;
				hex.getUnit().remove(handlerDef);
				System.out.println("Dead defender");
			}else {
				hex.getUnit().health = 1;
				remove(handlerAttack);
				System.out.println("Dead attacker");
				
			}
		}else {
			
			if (hex.getUnit().health <= 0) {
				hex.getUnit().remove(handlerDef);
				System.out.println("Dead defender");
			}
			if (health <= 0) {
				remove(handlerAttack);
				System.out.println("Dead attacker");
			}
			
		}
		
		movmentsLeft = 0;
	}

	public void remove(UHandler handler) {
		currentPos.setUnit(null);
		currentPos = null;
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

	public void moveNext(EnemyUnitHandler enemyUnitHandler, UnitHandler unitHandler) {
		while(movmentsLeft > 0){
			if (!movmentPath.isEmpty()) {
				
				if (movmentPath.getLast().getUnit() == null) {
					move(movmentPath.getLast(), movmentPath.removeLast().getType().getMovmentCost());
				}else if (!movmentPath.getLast().getUnit().getType().isEnemy()) {
					attack(movmentPath.getLast(), unitHandler, enemyUnitHandler);
				}else {
					movmentsLeft = 0;
				}
				
				
			}else {
				break;
			}
		}
		
		
		
	}

	public boolean isSame(Unit unit) {
		if (pos.x == unit.getPos().x && pos.y == unit.getPos().y) {
			return true;
		}
		return false;
	}
	

}
