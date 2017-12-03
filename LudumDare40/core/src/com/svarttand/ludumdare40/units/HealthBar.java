package com.svarttand.ludumdare40.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HealthBar {
	
	private float maxHp;
	private float width;
	private float height;
	private float hpwidth;
	
	private float x;
	private float y;
	
	public HealthBar(int l, int h, float maxHp, float x2, float y2){
		x = x2;
		y = y2;
		this.maxHp = maxHp;
		width = l;
		height = h;
		hpwidth = width;
	}
	
	public void update(float newHP, float x2, float y2){
		this.x = x2;
		this.y = y2;
		hpwidth = width * (newHP/maxHp);
		System.out.println("Hp width =" + hpwidth);
		
	}
	
	public void render(ShapeRenderer renderer){
		renderer.setColor(Color.RED);
		renderer.rect(x, y, width, height);
		renderer.setColor(Color.GREEN);
		renderer.rect(x, y, hpwidth, height);
	}

}
