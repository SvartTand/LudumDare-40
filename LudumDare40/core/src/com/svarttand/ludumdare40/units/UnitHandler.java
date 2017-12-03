package com.svarttand.ludumdare40.units;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.svarttand.ludumdare40.misc.FloatingText;

public class UnitHandler implements UHandler{
	
	private ArrayList<FloatingText> floatingTexts;
	ArrayList<Unit> unitList;
	private BitmapFont font;
	
	public UnitHandler(BitmapFont font){
		unitList = new ArrayList<Unit>();
		floatingTexts = new ArrayList<FloatingText>();
		this.font = font;
	}
	
	public void addUnit(Unit unit){
		unitList.add(unit);
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

	public void nextTurn() {
		for (int i = 0; i < unitList.size(); i++) {
			unitList.get(i).update();
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
	
	public void updateDelta(float delta){
		for (int i = 0; i < floatingTexts.size(); i++) {
			if (floatingTexts.get(i).update(delta)) {
				floatingTexts.remove(i);
			}
		}
	}

	public ArrayList<Unit> getUnits() {
		// TODO Auto-generated method stub
		return unitList;
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
