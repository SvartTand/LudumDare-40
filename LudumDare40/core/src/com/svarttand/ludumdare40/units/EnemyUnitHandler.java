package com.svarttand.ludumdare40.units;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.svarttand.ludumdare40.map.Hexagon;

public class EnemyUnitHandler {
	
	private ArrayList<Unit> unitList;
	
	public EnemyUnitHandler(){
		unitList = new ArrayList<Unit>();
	}
	
	public void update(ArrayList<Hexagon> cityList, ArrayList<Unit> friendlyUnitList){
		
	}
	
	private void addUnit(){
		
	}
	
	public void render(SpriteBatch batch, TextureAtlas atlas){
		for (int i = 0; i < unitList.size(); i++) {
			batch.draw(atlas.findRegion(unitList.get(i).getPath()), unitList.get(i).getPos().x, unitList.get(i).getPos().y);
		}
	}

}
