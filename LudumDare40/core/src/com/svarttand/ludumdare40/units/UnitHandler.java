package com.svarttand.ludumdare40.units;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class UnitHandler implements UHandler{
	
	ArrayList<Unit> unitList;
	
	public UnitHandler(){
		unitList = new ArrayList<Unit>();
	}
	
	public void addUnit(Unit unit){
		unitList.add(unit);
	}
	@Override
	public void render(SpriteBatch batch, TextureAtlas atlas){
		for (int i = 0; i < unitList.size(); i++) {
			batch.draw(atlas.findRegion(unitList.get(i).getPath()), unitList.get(i).getPos().x, unitList.get(i).getPos().y);
		}
	}

	public void nextTurn() {
		for (int i = 0; i < unitList.size(); i++) {
			unitList.get(i).update();
		}
		
	}
	@Override
	public void remove(Unit unit) {
		unitList.remove(unit);
		
		
	}

	public ArrayList<Unit> getUnits() {
		// TODO Auto-generated method stub
		return unitList;
	}

}
