package com.svarttand.ludumdare40.misc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class FloatingText {

	private static final int GRAVITY = 50;
	
	String text;
	float duration;
	Color color;
	
	int x;
	int y;
	
	private Label label;
	
	private boolean gravity;
	
	public FloatingText(String text,float f,float g, float duration, LabelStyle style, boolean gravity){
		this.text = text;
		this.duration = duration;
		
		label = new Label(text, style);
		label.setPosition(f- label.getWidth()*0.5f, g-label.getHeight()*0.5f);
		this.gravity = gravity;
	}
	
	public boolean update(float delta){
		duration -= delta;
		if (gravity) {
			label.setPosition(label.getX(), label.getY() - delta*GRAVITY);
		}
		if (duration <= 0) {
			return true;
		}
		return false;
	}
	
	public Label getLabel(){
		return label;
	}
	
}
