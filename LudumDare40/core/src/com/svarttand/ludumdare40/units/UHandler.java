package com.svarttand.ludumdare40.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public interface UHandler {
	
	public void render(SpriteBatch batch, TextureAtlas atlas);
	public void remove(Unit unit);

}
