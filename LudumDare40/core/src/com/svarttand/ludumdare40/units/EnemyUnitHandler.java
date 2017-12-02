package com.svarttand.ludumdare40.units;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.svarttand.ludumdare40.map.Hexagon;

public class EnemyUnitHandler implements UHandler{
	
	private ArrayList<Unit> unitList;
	
	public EnemyUnitHandler(){
		unitList = new ArrayList<Unit>();
	}
	
	public void update(ArrayList<Hexagon> cityList, ArrayList<Unit> friendlyUnitList, ArrayList<Hexagon> campList, UnitHandler handler){
		System.out.println("unitHandling updating...");
		for (int i = 0; i < unitList.size(); i++) {
			System.out.println("Unit " + i + "/" + unitList.size());
			unitList.get(i).update();
			System.out.println("calculating route...");
			unitList.get(i).calculateRoute(cityList, friendlyUnitList);
			System.out.println("moving unit...");
			unitList.get(i).moveNext(this, handler);
		}
		addUnit(campList);
	}
	
	private void addUnit(ArrayList<Hexagon> campList){
		Random random = new Random();
		for (int i = 0; i < campList.size(); i++) {
			int rand = random.nextInt(6);
			if (rand == 2) {
				if (campList.get(i).getUnit() == null) {
					unitList.add(campList.get(i).addUnit(UnitType.WARRIOR_ENEMY));
					System.out.println("Unit Added!");
				}
			}
			
		}
	}
	@Override
	public void render(SpriteBatch batch, TextureAtlas atlas){
		for (int i = 0; i < unitList.size(); i++) {
			batch.draw(atlas.findRegion(unitList.get(i).getPath()), unitList.get(i).getPos().x, unitList.get(i).getPos().y);
		}
	}

	@Override
	public void remove(Unit unit) {
		System.out.println("here");
		for (int i = 0; i < unitList.size(); i++) {
			System.out.println(unitList.get(i) + ", " + unit);
			if (unitList.get(i).isSame(unit)) {
				unitList.remove(i);
				System.out.println("removed");
			}
		}
		
	}

}
