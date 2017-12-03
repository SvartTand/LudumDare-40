package com.svarttand.ludumdare40.states;

import java.util.ArrayList;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svarttand.ludumdare40.Application;
import com.svarttand.ludumdare40.map.HexagonMap;
import com.svarttand.ludumdare40.map.TileType;
import com.svarttand.ludumdare40.misc.GameController;
import com.svarttand.ludumdare40.misc.ResourceHandler;
import com.svarttand.ludumdare40.ui.PlayUI;
import com.svarttand.ludumdare40.units.EnemyUnitHandler;
import com.svarttand.ludumdare40.units.UHandler;
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
	
	private ShapeRenderer renderer;
	
	private ArrayList<Sound> audioList;
	private int turnCounter;
	private BitmapFont font;
	
	public PlayState(GameStateManager gsm, TextureAtlas textureAtlas2) {
		super(gsm);
		textureAtlas = textureAtlas2;
		viewport = new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, cam);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		map = new HexagonMap(10, 22, gsm, this);
		controller = new GameController(this);
		multiplexer = new InputMultiplexer();

		resourceHandler = new ResourceHandler(30,20,20);
		ui = new PlayUI(textureAtlas, this);
		map.updateBorders(ui);
		multiplexer.addProcessor(ui.getStage());
		multiplexer.addProcessor(controller);
		
		font = new BitmapFont();
		
		Gdx.input.setInputProcessor(multiplexer);
		
		audioList = new ArrayList<Sound>();
		
		for (int i = 1; i < LoadingState.AUDIO_AMOUNT; i++) {
			audioList.add(gsm.assetManager.get("Sound/"+ i + ".wav",Sound.class));
		}
		
		unitHandler = new UnitHandler(font);
		cam.position.x = map.getStartPos().x;
		cam.position.y = Application.V_HEIGHT*0.3f;
		
		enemyUnitHandler = new EnemyUnitHandler(font);
		turnCounter = 0;
		renderer = new ShapeRenderer();
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
		ui.updateFloatinTexts(delta);
		unitHandler.updateDelta(delta);
		enemyUnitHandler.updateDelta(delta);
		
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(cam.combined);
		renderer.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(0, (float) 0.6, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		map.render(batch, textureAtlas);
		unitHandler.render(batch, textureAtlas, renderer);
		enemyUnitHandler.render(batch, textureAtlas, renderer);
		batch.end();
		renderer.begin(ShapeType.Filled);
		enemyUnitHandler.renderBars(renderer);
		unitHandler.renderBars(renderer);
		renderer.end();
		ui.render(batch, textureAtlas);
		ui.getStage().draw();
		
		
	}

	@Override
	public void dispose() {
		ui.dispose();
		font.dispose();
		
		
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		ui.resize(width, height);
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
		turnCounter++;
		unitHandler.nextTurn();
		map.update(resourceHandler);
		ui.update(resourceHandler, map);
		enemyUnitHandler.update(map.getCityList(), unitHandler.getUnits(), map.getCampList(), unitHandler, map);
		System.out.println("finished updating");
	}
	
	public ResourceHandler getResources(){
		return resourceHandler;
	}
	
	public int getTurnCounter(){
		return turnCounter;
	}

	public UHandler getEnemyUnitHandler() {
		// TODO Auto-generated method stub
		return enemyUnitHandler;
	}

	@Override
	public void updateScreen(boolean b, int t) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
