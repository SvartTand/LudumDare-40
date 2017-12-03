package com.svarttand.ludumdare40.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface UHandler {
	
	public void render(SpriteBatch batch, TextureAtlas atlas, ShapeRenderer renderer);
	public void remove(Unit unit);

}
