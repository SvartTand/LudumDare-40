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
	
	public void update(ArrayList<Hexagon> cityList, ArrayList<Unit> friendlyUnitList, ArrayList<Hexagon> campList){
		for (int i = 0; i < unitList.size(); i++) {
			unitList.get(i).update();
			unitList.get(i).calculateRoute(cityList, friendlyUnitList);
			unitList.get(i).moveNext(this);
		}
		addUnit(campList);
	}
	
	private void addUnit(ArrayList<Hexagon> campList){
		Random random = new Random();
		for (int i = 0; i < campList.size(); i++) {
			int rand = random.nextInt(6);
			if (rand == 2) {
				unitList.add(campList.get(i).addUnit(UnitType.WARRIOR_ENEMY));
				System.out.println("Unit Added!");
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
		unitList.remove(unit);
		
	}

}
