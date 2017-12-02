package com.svarttand.ludumdare40.units;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class UnitHandler {
	
	ArrayList<Unit> unitList;
	
	public UnitHandler(){
		unitList = new ArrayList<Unit>();
	}
	
	public void addUnit(Unit unit){
		unitList.add(unit);
	}
	
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

}
