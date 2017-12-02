package com.svarttand.ludumdare40.ui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svarttand.ludumdare40.Application;
import com.svarttand.ludumdare40.map.Hexagon;
import com.svarttand.ludumdare40.map.TileType;
import com.svarttand.ludumdare40.misc.Command;
import com.svarttand.ludumdare40.misc.ResourceHandler;
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
	private Button makeWarriorButton;
	private Button makeBuilderButton;
	
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
	    
	    resourcesText = new Label("Food: " + game.getResources().getFood() + "\nGold: " + game.getResources().getGold() +"\nWood: " + game.getResources().getWood(), new LabelStyle(font, Color.WHITE));
	    resourcesText.setPosition(Application.V_WIDTH *0.48f, Application.V_HEIGHT*0.03f);
	    
	    stage.addActor(resourcesText);
	    
	    moveCommandButton = new TextButton("Move", style);
	    moveCommandButton.setPosition(Application.V_WIDTH*0.75f, Application.V_HEIGHT*0.11f);
	    moveCommandButton.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 System.out.println("move pressed");
	        	 
	        		 if (selectedUnit != null) {
	 					game.getController().setCommand(Command.MOVE);
	 					game.getController().setMovmentPossibilities(selectedUnit.getMovmentsLeft());
	 				}
	        	 
	        	 
	            }
	        });
	    buttonList.add(moveCommandButton);
	    stage.addActor(moveCommandButton);
	    
	    attackCommandButton = new TextButton("Attack", style);
	    attackCommandButton.setPosition(Application.V_WIDTH*0.825f, Application.V_HEIGHT*0.11f);
	    attackCommandButton.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 System.out.println("Attack pressed");
	            }
	        });
	    buttonList.add(attackCommandButton);
	    stage.addActor(attackCommandButton);
	    
	    makeBuilderButton = new TextButton("Builder", style);
	    makeBuilderButton.setPosition(Application.V_WIDTH*0.125f, Application.V_HEIGHT*0.11f);
	    makeBuilderButton.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 if (selectedUnit == null && selectedHexagon != null) {
	        		 if (game.getResources().getFood()>= UnitType.WORKER.getFoodCost() && 
	        			 game.getResources().getWood()>= UnitType.WORKER.getWoodCost() &&
	        			 game.getResources().getGold()>= UnitType.WORKER.getGoldCost()) {
	        			 selectedUnit = selectedHexagon.addUnit(UnitType.WORKER, 1);
	 					game.getUnitHandler().addUnit(selectedUnit);
	 					System.out.println("Unit added");
	 					updateButtons();
	 					
	 					game.getResources().removeCost(UnitType.WORKER);
	 					update(game.getResources());
					}else{
						System.out.println("Not enough money!");
					}
	        		
				}
	        	 makeBuilderButton.setChecked(false);
	            }
	        });
	    buttonList.add(makeBuilderButton);
	    stage.addActor(makeBuilderButton);
	    
	    makeWarriorButton = new TextButton("Warrior", style);
	    makeWarriorButton.setPosition(Application.V_WIDTH*0.2f, Application.V_HEIGHT*0.11f);
	    makeWarriorButton.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 if (selectedUnit == null && selectedHexagon != null) {
	        		 if (game.getResources().getFood()>= UnitType.WARRIOR.getFoodCost() && 
		        			 game.getResources().getWood()>= UnitType.WARRIOR.getWoodCost() &&
		        			 game.getResources().getGold()>= UnitType.WARRIOR.getGoldCost()) {
		        			 selectedUnit = selectedHexagon.addUnit(UnitType.WARRIOR, 1);
		 					game.getUnitHandler().addUnit(selectedUnit);
		 					System.out.println("Unit added");
		 					updateButtons();
		 					
		 					game.getResources().removeCost(UnitType.WARRIOR);
		 					update(game.getResources());
						}else{
							System.out.println("Not enough money!");
						}
				}
	        	 makeWarriorButton.setChecked(false);
	            }
	        });
	    buttonList.add(makeWarriorButton);
	    stage.addActor(makeWarriorButton);
	    
	    BuildCommandButton = new TextButton("Build", style);
	    BuildCommandButton.setPosition(Application.V_WIDTH*0.9f, Application.V_HEIGHT*0.11f);
	    BuildCommandButton.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 System.out.println("build pressed");
	        	 
	        		 if (selectedUnit != null && selectedUnit.getType() == UnitType.WORKER) {
	 					selectedHexagon.setType(TileType.CITY);
	 					game.getMap().addCity(selectedHexagon);
	 					selectedUnit.remove(game.getUnitHandler());
	 					selectedUnit = null;
	 					updateButtons();
	 				}
	        	 
	        	 
	            }
	        });
	    buttonList.add(BuildCommandButton);
	    stage.addActor(BuildCommandButton);
	    
	    TextButtonStyle styleTurn = new TextButton.TextButtonStyle();
	    styleTurn.font = font;
	    styleTurn.up = skin.getDrawable("EndTurnButton");
	    styleTurn.down = skin.getDrawable("EndTurnButtonPressed");
	    styleTurn.checked = skin.getDrawable("EndTurnButtonPressed");
	    
	    endTurnButton = new TextButton("Next Turn", styleTurn);
	    endTurnButton.setPosition(Application.V_WIDTH*0.5f - endTurnButton.getWidth()*0.5f, Application.V_HEIGHT*0.12f);
	    endTurnButton.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 System.out.println("Next turn!");
	        	 game.nextTurn();
	        	 endTurnButton.setChecked(false);
	            }
	        });
	    stage.addActor(endTurnButton);
	    moveCommandButton.setVisible(false);
		attackCommandButton.setVisible(false);
		makeBuilderButton.setVisible(false);
		makeWarriorButton.setVisible(false);
		BuildCommandButton.setVisible(false);
	    
	}
	
	public void update(ResourceHandler resources){
		resourcesText.setText("Food: " + resources.getFood() + "\nGold: " + resources.getGold() +"\nWood: " + resources.getWood());
	}
	
	
	public void render(SpriteBatch batch, TextureAtlas atlas){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(atlas.findRegion("UiBar"), 0, 0);
		if (selectedUnit != null) {
			batch.draw(atlas.findRegion(selectedUnit.getPath()),Application.V_WIDTH*0.6f, Application.V_HEIGHT*0.11f);
		}
		if (selectedHexagon != null) {
			batch.draw(atlas.findRegion(selectedHexagon.getPath()),Application.V_WIDTH*0.02f, Application.V_HEIGHT*0.1f);
		}
		batch.draw(atlas.findRegion("FoodIcon"),Application.V_WIDTH*0.45f, Application.V_HEIGHT*0.08f);
		batch.draw(atlas.findRegion("GoldIcon"),Application.V_WIDTH*0.45f, Application.V_HEIGHT*0.05f);
		batch.draw(atlas.findRegion("WoodIcon"),Application.V_WIDTH*0.45f, Application.V_HEIGHT*0.02f);
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
		updateButtons();
	}
	
	private void updateButtons(){
		if (selectedHexagon.getType() == TileType.CITY) {
			makeBuilderButton.setVisible(true);
			makeWarriorButton.setVisible(true);
		}else{
			makeBuilderButton.setVisible(false);
			makeWarriorButton.setVisible(false);
		}
		if (selectedUnit == null) {
			moveCommandButton.setVisible(false);
			attackCommandButton.setVisible(false);
			BuildCommandButton.setVisible(false);
		}else{
			moveCommandButton.setVisible(true);
			attackCommandButton.setVisible(true);
			if (selectedUnit.getType() == UnitType.WORKER) {
				BuildCommandButton.setVisible(true);
			}
		}
	}

	public void resetButtons() {
		for (int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).setChecked(false);
		}
		
	}

}
