package com.svarttand.ludumdare40.ui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svarttand.ludumdare40.Application;
import com.svarttand.ludumdare40.map.Hexagon;
import com.svarttand.ludumdare40.misc.Command;
import com.svarttand.ludumdare40.misc.GameController;
import com.svarttand.ludumdare40.states.PlayState;
import com.svarttand.ludumdare40.units.Unit;
import com.svarttand.ludumdare40.units.UnitType;

public class PlayUI {
	
	private OrthographicCamera camera;
	private Viewport viewport;
	
	private Stage stage;
	
	private TextButton.TextButtonStyle style;
	private Skin skin;
	private BitmapFont font;
	
	private Button endTurnButton;
	private Button exitButton;
	
	private Button moveCommandButton;
	private Button attackCommandButton;
	private Button BuildCommandButton;
	private Button button;
	
	private Label resourcesText;
	private Label buildingInfoText;
	private Label unitInfoText;
	
	private Unit selectedUnit;
	private Hexagon selectedHexagon;
	 
	private ArrayList<Button> buttonList;
	
	public PlayUI(TextureAtlas atlas, final PlayState game){
		camera = new OrthographicCamera();
		viewport = new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, camera);
		stage = new Stage(viewport);
		
		buttonList = new ArrayList<Button>();
		
		font = new BitmapFont();
	    skin = new Skin(atlas);
	    style = new TextButton.TextButtonStyle();
	    style.font = font;
	    style.up = skin.getDrawable("Button");
	    style.down = skin.getDrawable("ButtonPressed");
	    style.checked = skin.getDrawable("ButtonPressed");
	    
	    
	    moveCommandButton = new TextButton("M", style);
	    moveCommandButton.setPosition(Application.V_WIDTH*0.75f, Application.V_HEIGHT*0.11f);
	    moveCommandButton.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 System.out.println("move pressed");
	        	 if (!moveCommandButton.isChecked()) {
	        		 if (selectedUnit != null) {
	 					game.getController().setCommand(Command.MOVE);
	 				}
	        	 }
	        	 
	            }
	        });
	    buttonList.add(moveCommandButton);
	    stage.addActor(moveCommandButton);
	    
	    attackCommandButton = new TextButton("A", style);
	    attackCommandButton.setPosition(Application.V_WIDTH*0.825f, Application.V_HEIGHT*0.11f);
	    attackCommandButton.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 System.out.println("Attack pressed");
	            }
	        });
	    buttonList.add(attackCommandButton);
	    stage.addActor(attackCommandButton);
	    
	    BuildCommandButton = new TextButton("B", style);
	    BuildCommandButton.setPosition(Application.V_WIDTH*0.9f, Application.V_HEIGHT*0.11f);
	    BuildCommandButton.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 if (selectedUnit == null && selectedHexagon != null) {
	        		 selectedUnit = selectedHexagon.addUnit(UnitType.WARRIOR);
					game.getUnitHandler().addUnit(selectedUnit);
					System.out.println("Unit added");
				}
	        	 BuildCommandButton.setChecked(false);
	            }
	        });
	    buttonList.add(BuildCommandButton);
	    stage.addActor(BuildCommandButton);
	    
	}
	
	public void update(){
		
	}
	
	
	public void render(SpriteBatch batch, TextureAtlas atlas){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(atlas.findRegion("UiBar"), 0, 0);
		batch.end();
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public void dispose(){
		stage.dispose();
	}
	
	public void setHex(Hexagon hex){
		selectedHexagon = hex;
		selectedUnit = hex.getUnit();
	}

	public void resetButtons() {
		for (int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).setChecked(false);
		}
		
	}

}
