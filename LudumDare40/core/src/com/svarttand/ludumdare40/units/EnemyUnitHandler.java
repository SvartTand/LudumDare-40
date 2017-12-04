package com.svarttand.ludumdare40.units;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.svarttand.ludumdare40.map.Hexagon;
import com.svarttand.ludumdare40.map.HexagonMap;
import com.svarttand.ludumdare40.misc.FloatingText;

public class EnemyUnitHandler implements UHandler{
	
	private ArrayList<Unit> unitList;
	
	private ArrayList<FloatingText> floatingTexts;
	private BitmapFont font;
	
	public EnemyUnitHandler(BitmapFont font){
		unitList = new ArrayList<Unit>();
		floatingTexts = new ArrayList<FloatingText>();
		this.font = font;
	}
	
	public void update(ArrayList<Hexagon> cityList, ArrayList<Unit> friendlyUnitList, ArrayList<Hexagon> campList, UnitHandler handler, HexagonMap map, ArrayList<Sound> audio, int currentGoldInput){
		System.out.println("unitHandling updating...");
		for (int i = 0; i < unitList.size(); i++) {
			System.out.println("Unit " + i + "/" + unitList.size());
			unitList.get(i).update();
			System.out.println("calculating route...");
			unitList.get(i).calculateRoute(cityList, friendlyUnitList);
			System.out.println("moving unit...");
			unitList.get(i).moveNext(this, handler, map, audio);
		}
		addUnit(campList, currentGoldInput);
	}
	
	public void updateDelta(float delta){
		for (int i = 0; i < floatingTexts.size(); i++) {
			if (floatingTexts.get(i).update(delta)) {
				floatingTexts.remove(i);
			}
		}
	}
	
	private void addUnit(ArrayList<Hexagon> campList, int currentGoldInput){
		Random random = new Random();
		for (int i = 0; i < campList.size(); i++) {
			int rand = random.nextInt(20);
			if (rand <= currentGoldInput) {
				if (rand == 4) {
					if (campList.get(i).getUnit() == null) {
						unitList.add(campList.get(i).addUnit(UnitType.TANK_ENEMY));
						System.out.println("Unit Added!");
					}
				}else {
					if (campList.get(i).getUnit() == null) {
						unitList.add(campList.get(i).addUnit(UnitType.WARRIOR_ENEMY));
						System.out.println("Unit Added!");
					}
				}
				
			}
			
		}
	}
	@Override
	public void render(SpriteBatch batch, TextureAtlas atlas, ShapeRenderer renderer){
		for (int i = 0; i < unitList.size(); i++) {
			batch.draw(atlas.findRegion(unitList.get(i).getPath()), unitList.get(i).getPos().x, unitList.get(i).getPos().y);
		}
		for (int i = 0; i < floatingTexts.size(); i++) {
			floatingTexts.get(i).getLabel().draw(batch, 1);
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

	public void renderBars(ShapeRenderer renderer) {
		for (int i = 0; i < unitList.size(); i++) {
			unitList.get(i).getHpBar().render(renderer);
		}
		
	}

	@Override
	public void addFloatingText(String text, float posx, float posy) {
		floatingTexts.add(new FloatingText(text, posx, posy, TEXT_DURATION, new LabelStyle(font, Color.RED), true));
		
	}

}
