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
import com.svarttand.ludumdare40.misc.ResourceHandler;
import com.svarttand.ludumdare40.ui.PlayUI;
import com.svarttand.ludumdare40.units.EnemyUnitHandler;
import com.svarttand.ludumdare40.units.UnitHandler;


public class PlayState extends State{

	private static final int CAM_SPEED = 300;
	
	private TextureAtlas textureAtlas;
	private Viewport viewport;
	private HexagonMap map;
	
	private GameController controller;
	private InputMultiplexer multiplexer;
	
	private PlayUI ui;
	
	private UnitHandler unitHandler;
	
	private ResourceHandler resourceHandler;
	private EnemyUnitHandler enemyUnitHandler;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		textureAtlas = gsm.assetManager.get("ThePack.pack", TextureAtlas.class);
		viewport = new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, cam);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		map = new HexagonMap(20, 20);
		controller = new GameController(this);
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(controller);
		resourceHandler = new ResourceHandler(5,5,5);
		ui = new PlayUI(textureAtlas, this);
		multiplexer.addProcessor(ui.getStage());
		
		Gdx.input.setInputProcessor(multiplexer);
		
		unitHandler = new UnitHandler();
		cam.position.x = map.getStartPos().x;
		cam.position.y = Application.V_HEIGHT*0.3f;
		
		resourceHandler = new ResourceHandler(5,5,5);
		enemyUnitHandler = new EnemyUnitHandler();
	}

	@Override
	protected void handleInput(float delta) {
		//UP
//		if (Gdx.input.isKeyPressed(Keys.W)) {
//			cam.position.y = cam.position.y + CAM_SPEED*delta;
//
//		}
//		//DOWN
//		if (Gdx.input.isKeyPressed(Keys.S)) {
//			cam.position.y = cam.position.y - CAM_SPEED*delta;
//
//		}
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
		unitHandler.render(batch, textureAtlas);
		batch.end();
		ui.render(batch, textureAtlas);
		ui.getStage().draw();
		
		
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
	
	public UnitHandler getUnitHandler(){
		return unitHandler;
	}
	
	public PlayUI getUI(){
		return ui;
	}

	public GameController getController() {
		return controller;
	}

	public void nextTurn() {
		unitHandler.nextTurn();
		map.update(resourceHandler);
		ui.update(resourceHandler);
		enemyUnitHandler.update(map.getCityList(), unitHandler.getUnits());
		
	}
	
	public ResourceHandler getResources(){
		return resourceHandler;
	}
	

}
