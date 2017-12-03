package com.svarttand.ludumdare40.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.svarttand.ludumdare40.misc.FloatingText;

public interface UHandler {
	
	public static final int TEXT_DURATION = 2;
	
	public void render(SpriteBatch batch, TextureAtlas atlas, ShapeRenderer renderer);
	public void remove(Unit unit);
	
	public void addFloatingText(String text, float posx, float posy);

}
