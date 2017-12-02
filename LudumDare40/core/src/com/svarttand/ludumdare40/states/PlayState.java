package com.svarttand.ludumdare40.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svarttand.ludumdare40.Application;
import com.svarttand.ludumdare40.map.HexagonMap;
import com.svarttand.ludumdare40.misc.GameController;


public class PlayState extends State{

	private static final int CAM_SPEED = 300;
	
	private TextureAtlas textureAtlas;
	private Viewport viewport;
	private HexagonMap map;
	
	private GameController controller;
	private InputMultiplexer multiplexer;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		textureAtlas = gsm.assetManager.get("ThePack.pack", TextureAtlas.class);
		viewport = new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, cam);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		map = new HexagonMap(30, 30);
		controller = new GameController(this);
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(controller);
		
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	protected void handleInput(float delta) {
		//UP
		if (Gdx.input.isKeyPressed(Keys.W)) {
			cam.position.y = cam.position.y + CAM_SPEED*delta;

		}
		//DOWN
		if (Gdx.input.isKeyPressed(Keys.S)) {
			cam.position.y = cam.position.y - CAM_SPEED*delta;

		}
		//LEFT
		if (Gdx.input.isKeyPressed(Keys.A)) {
			cam.position.x = cam.position.x - CAM_SPEED*delta;

		}
		//RIGHT
		if (Gdx.input.isKeyPressed(Keys.D)) {
			cam.position.x = cam.position.x + CAM_SPEED*delta;
			
		}
		cam.update();
		
		
	}

	@Override
	public void update(float delta) {
		handleInput(delta);
		
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(0, (float) 0.6, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		map.render(batch, textureAtlas);
		batch.end();
		
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		
	}
	
	public OrthographicCamera getCam(){
		return cam;
	}
	
	public HexagonMap getMap(){
		return map;
	}

}
