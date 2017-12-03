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
import com.svarttand.ludumdare40.map.HexagonMap;
import com.svarttand.ludumdare40.map.TileType;
import com.svarttand.ludumdare40.misc.Command;
import com.svarttand.ludumdare40.misc.FloatingText;
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
	private Button BuildCommandButton;
	private Button makeWarriorButton;
	private Button makeBuilderButton;
	private Button makeTankButton;
	private Button makeUnit;
	
	private Label resourcesText;
	private Label objectiveText;
	private Label unitInfoText;
	private Label hexInfoText;


	private Label buildInfoText;
	
	private Label foodText;
	private Label goldText;
	private Label woodText;
	
	private Unit selectedUnit;
	private Hexagon selectedHexagon;
	
	private boolean tabVisible;
	 
	private ArrayList<Button> buttonList;
	
	ArrayList<FloatingText> floatingTexts;
	
	public PlayUI(TextureAtlas atlas, final PlayState game){
		camera = new OrthographicCamera();
		viewport = new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, camera);
		stage = new Stage(viewport);
		
		buttonList = new ArrayList<Button>();
		tabVisible = false;
		floatingTexts = new ArrayList<FloatingText>();
		
		font = new BitmapFont();
	    skin = new Skin(atlas);
	    style = new TextButton.TextButtonStyle();
	    style.font = font;
	    style.up = skin.getDrawable("Button");
	    style.down = skin.getDrawable("ButtonPressed");
	    style.checked = skin.getDrawable("ButtonPressed");
	    style.disabled = skin.getDrawable("ButtonDisabled");
	    
	    resourcesText = new Label("Food: " + game.getResources().getFood() + "\nGold: " + game.getResources().getGold() +"\nWood: " + game.getResources().getWood(), new LabelStyle(font, Color.WHITE));
	    resourcesText.setPosition(Application.V_WIDTH *0.48f, Application.V_HEIGHT*0.03f);
	    
	    objectiveText = new Label("OBJECIVE:\nControl all the gold.\n" + game.getMap().getOwnedGold() + "/" + game.getMap().getTotalGold(), new LabelStyle(font, Color.WHITE));
	    objectiveText.setPosition(Application.V_WIDTH *0.01f, Application.V_HEIGHT*0.925f);
	    
	    unitInfoText = new Label("Unit:", new LabelStyle(font, Color.WHITE));
	    unitInfoText.setPosition(Application.V_WIDTH *0.6f, Application.V_HEIGHT*0.05f);
	    
	    hexInfoText = new Label("Nothing selected", new LabelStyle(font, Color.WHITE));
	    hexInfoText.setPosition(Application.V_WIDTH *0.02f, Application.V_HEIGHT*0.06f);
	    
	    buildInfoText = new Label("Nothing selected", new LabelStyle(font, Color.WHITE));
	    buildInfoText.setPosition(Application.V_WIDTH *0.115f, Application.V_HEIGHT*0.2f);
	    
	    buildInfoText = new Label("", new LabelStyle(font, Color.WHITE));
	    buildInfoText.setPosition(Application.V_WIDTH *0.115f, Application.V_HEIGHT*0.2f);
	    
	    foodText = new Label("10", new LabelStyle(font, Color.WHITE));
	    foodText.setPosition(Application.V_WIDTH *0.06f, Application.V_HEIGHT*0.02f);
	    goldText = new Label("10", new LabelStyle(font, Color.WHITE));
	    goldText.setPosition(Application.V_WIDTH *0.305f, Application.V_HEIGHT*0.02f);
	    woodText = new Label("10", new LabelStyle(font, Color.WHITE));
	    woodText.setPosition(Application.V_WIDTH *0.36f, Application.V_HEIGHT*0.02f);
	    
	    buildInfoText.setVisible(false);
	    
	    stage.addActor(woodText);
	    stage.addActor(goldText);
	    stage.addActor(foodText);
	    
	    stage.addActor(buildInfoText);
	    stage.addActor(hexInfoText);
	    stage.addActor(resourcesText);
	    stage.addActor(objectiveText);
	    stage.addActor(unitInfoText);
	    
	    
	    
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
	    
	    
	    makeBuilderButton = new TextButton("Builder", style);
	    makeBuilderButton.setPosition(Application.V_WIDTH*0.125f, Application.V_HEIGHT*0.11f);
	    makeBuilderButton.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 updateBuyTab(makeBuilderButton.isChecked(), UnitType.WORKER);
	        	 makeTankButton.setChecked(false);
	        	 makeWarriorButton.setChecked(false);
	            }
	        });
	    buttonList.add(makeBuilderButton);
	    stage.addActor(makeBuilderButton);
	    
	    makeWarriorButton = new TextButton("Infantry", style);
	    makeWarriorButton.setPosition(Application.V_WIDTH*0.2f, Application.V_HEIGHT*0.11f);
	    makeWarriorButton.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 updateBuyTab(makeWarriorButton.isChecked(), UnitType.WARRIOR);
	        	 makeBuilderButton.setChecked(false);
	        	 makeTankButton.setChecked(false);
	            }
	        });
	    buttonList.add(makeWarriorButton);
	    stage.addActor(makeWarriorButton);
	    
	    
	    
	    makeTankButton = new TextButton("Tank", style);
	    makeTankButton.setPosition(Application.V_WIDTH*0.275f, Application.V_HEIGHT*0.11f);
	    makeTankButton.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 updateBuyTab(makeTankButton.isChecked(), UnitType.TANK);
	        	 makeBuilderButton.setChecked(false);
	        	 makeWarriorButton.setChecked(false);
	            }
	        });
	    buttonList.add(makeTankButton);
	    stage.addActor(makeTankButton);
	    
	    
	    
	    
	    
	    makeUnit = new TextButton("Buy", style);
	    makeUnit.setPosition(Application.V_WIDTH*0.275f, Application.V_HEIGHT*0.18f);
	    makeUnit.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 if (selectedUnit == null && selectedHexagon != null) {
	        		 if (makeWarriorButton.isChecked()) {
	        			 if (game.getResources().getFood()>= UnitType.WARRIOR.getFoodCost() && 
			        			 game.getResources().getWood()>= UnitType.WARRIOR.getWoodCost() &&
			        			 game.getResources().getGold()>= UnitType.WARRIOR.getGoldCost()) {
			        			 selectedUnit = selectedHexagon.addUnit(UnitType.WARRIOR);
			 					game.getUnitHandler().addUnit(selectedUnit);
			 					System.out.println("Unit added");
			 					updateButtons();
			 					
			 					game.getResources().removeCost(UnitType.WARRIOR);
			 					update(game.getResources(), game.getMap());
							}else{
								System.out.println("Not enough money!");
								FloatingText temp = new FloatingText("Not Enough Money!", Application.V_WIDTH*0.5f, Application.V_HEIGHT*0.5f, 2, new LabelStyle(font, Color.RED), true);
								floatingTexts.add(temp);
								//stage.addActor(temp.getLabel());
							}
					}else if (makeBuilderButton.isChecked()) {
						 if (game.getResources().getFood()>= UnitType.WORKER.getFoodCost() && 
			        			 game.getResources().getWood()>= UnitType.WORKER.getWoodCost() &&
			        			 game.getResources().getGold()>= UnitType.WORKER.getGoldCost()) {
			        			 selectedUnit = selectedHexagon.addUnit(UnitType.WORKER);
			 					game.getUnitHandler().addUnit(selectedUnit);
			 					System.out.println("Unit added");
			 					updateButtons();
			 					
			 					game.getResources().removeCost(UnitType.WORKER);
			 					update(game.getResources(), game.getMap());
							}else{
								System.out.println("Not enough money!");
								FloatingText temp = new FloatingText("Not Enough Money!", Application.V_WIDTH*0.5f, Application.V_HEIGHT*0.5f, 2, new LabelStyle(font, Color.RED), true);
								floatingTexts.add(temp);
								//stage.addActor(temp.getLabel());
							}
					}else if (makeTankButton.isChecked()) {
						if (game.getResources().getFood()>= UnitType.TANK.getFoodCost() && 
			        			 game.getResources().getWood()>= UnitType.TANK.getWoodCost() &&
			        			 game.getResources().getGold()>= UnitType.TANK.getGoldCost()) {
			        			 selectedUnit = selectedHexagon.addUnit(UnitType.TANK);
			 					game.getUnitHandler().addUnit(selectedUnit);
			 					System.out.println("Unit added");
			 					updateButtons();
			 					
			 					game.getResources().removeCost(UnitType.TANK);
			 					update(game.getResources(), game.getMap());
							}else{
								System.out.println("Not enough money!");
								FloatingText temp = new FloatingText("Not Enough Money!", Application.V_WIDTH*0.5f, Application.V_HEIGHT*0.5f, 2, new LabelStyle(font, Color.RED), true);
								floatingTexts.add(temp);
								//stage.addActor(temp.getLabel());
							}
					}
				}
	        	 makeUnit.setChecked(false);
	            }
	        });
	    buttonList.add(makeUnit);
	    stage.addActor(makeUnit);
	    makeUnit.setVisible(false);
	    
	    
	    
	    
	    
	    BuildCommandButton = new TextButton("Build", style);
	    BuildCommandButton.setPosition(Application.V_WIDTH*0.825f, Application.V_HEIGHT*0.11f);
	    BuildCommandButton.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 System.out.println("build pressed");
	        	 
	        		 if (selectedUnit != null && selectedUnit.getType() == UnitType.WORKER) {
	        			if (selectedHexagon.getType() == TileType.GOLD) {
	        				FloatingText temp = new FloatingText("Cant Build on Gold!", Application.V_WIDTH*0.5f, Application.V_HEIGHT*0.5f, 2, new LabelStyle(font, Color.RED), true);
							floatingTexts.add(temp);
							stage.addActor(temp.getLabel());
						}else if (selectedHexagon.getType() == TileType.CITY) {
							FloatingText temp = new FloatingText("Cant Build on Domes!", Application.V_WIDTH*0.5f, Application.V_HEIGHT*0.5f, 2, new LabelStyle(font, Color.RED), true);
							floatingTexts.add(temp);
							stage.addActor(temp.getLabel());
						}else{
							
							selectedHexagon.setType(TileType.CITY);
		 					game.getMap().addCity(selectedHexagon);
		 					selectedUnit.remove(game.getUnitHandler());
		 					selectedUnit = null;
						}
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
	    moveCommandButton.setDisabled(true);
		makeBuilderButton.setDisabled(true);
		makeWarriorButton.setDisabled(true);
		BuildCommandButton.setDisabled(true);
		makeTankButton.setDisabled(true);
	    
	}
	
	public void update(ResourceHandler resources, HexagonMap map){
		System.out.println("resource Update");
		resourcesText.setText("Food: " + resources.getFood() + "\nGold: " + resources.getGold() +"\nWood: " + resources.getWood());
		objectiveText.setText("OBJECIVE:\nControl all the gold.\n" + map.getOwnedGold() + "/" + map.getTotalGold());
		updateUnitText();
	}
	
	
	
	public void updateUnitText(){
		unitInfoText.setText("Unit:\n" + selectedUnit);
		hexInfoText.setText(selectedHexagon + " /turn");
	}
	
	public void updateFloatinTexts(float delta){
		for (int i = 0; i < floatingTexts.size(); i++) {
			if (floatingTexts.get(i).update(delta)) {
				floatingTexts.get(i).getLabel().setText("");
				floatingTexts.remove(i);
				
			}
		}
	}
	
	
	public void render(SpriteBatch batch, TextureAtlas atlas){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(atlas.findRegion("UiBar"), 0, 0);
		if (selectedUnit != null) {
			batch.draw(atlas.findRegion(selectedUnit.getPath()),Application.V_WIDTH*0.575f, Application.V_HEIGHT*0.095f, atlas.findRegion(selectedUnit.getPath()).getRegionWidth()*2, atlas.findRegion(selectedUnit.getPath()).getRegionHeight()*2 );
		}
		if (selectedHexagon != null) {
			batch.draw(atlas.findRegion(selectedHexagon.getPath()),Application.V_WIDTH*0.02f, Application.V_HEIGHT*0.1f);
		}
		batch.draw(atlas.findRegion("TopCorner"),0, Application.V_HEIGHT - atlas.findRegion("TopCorner").getRegionHeight());
		//recources
		batch.draw(atlas.findRegion("FoodIcon"),Application.V_WIDTH*0.45f, Application.V_HEIGHT*0.08f);
		batch.draw(atlas.findRegion("GoldIcon"),Application.V_WIDTH*0.45f, Application.V_HEIGHT*0.05f);
		batch.draw(atlas.findRegion("WoodIcon"),Application.V_WIDTH*0.45f, Application.V_HEIGHT*0.02f);
		
		batch.draw(atlas.findRegion("FoodIcon"),Application.V_WIDTH*0.27f, Application.V_HEIGHT*0.02f);
		batch.draw(atlas.findRegion("GoldIcon"),Application.V_WIDTH*0.33f, Application.V_HEIGHT*0.02f);
		batch.draw(atlas.findRegion("WoodIcon"),Application.V_WIDTH*0.39f, Application.V_HEIGHT*0.02f);
		
		for (int i = 0; i < floatingTexts.size(); i++) {
			floatingTexts.get(i).getLabel().draw(batch, 1);
		}
		
		if (tabVisible) {
			batch.draw(atlas.findRegion("TopCorner"),Application.V_WIDTH*0.105f, Application.V_HEIGHT*0.17f);
			batch.draw(atlas.findRegion("FoodIcon"),Application.V_WIDTH*0.11f, Application.V_HEIGHT*0.2f);
			batch.draw(atlas.findRegion("GoldIcon"),Application.V_WIDTH*0.16f, Application.V_HEIGHT*0.2f);
			batch.draw(atlas.findRegion("WoodIcon"),Application.V_WIDTH*0.21f, Application.V_HEIGHT*0.2f);
		}
		
		batch.end();
	}
	
	private void updateBuyTab(boolean b, UnitType type){
		buildInfoText.setVisible(b);
		makeUnit.setVisible(b);
		tabVisible = b;
		if (type == UnitType.WARRIOR) {
			buildInfoText.setText(UnitType.WARRIOR.getPath() + ":   Agility: " + UnitType.WARRIOR.getMovments() + "\n    " + UnitType.WARRIOR.getFoodCost()
			+ ",     " + UnitType.WARRIOR.getGoldCost() + ",     " + UnitType.WARRIOR.getWoodCost() + "\nDmg: " + UnitType.WARRIOR.getDmg() + ", Hp: " +UnitType.WARRIOR.getHp());
		}else if (type == UnitType.TANK) {
			buildInfoText.setText(UnitType.TANK.getPath() + ":   Agility: " + UnitType.TANK.getMovments() + "\n    " + UnitType.TANK.getFoodCost()
			+ ",     " + UnitType.TANK.getGoldCost() + ",     " + UnitType.TANK.getWoodCost() + "\nDmg: " + UnitType.TANK.getDmg() + ", Hp: " +UnitType.TANK.getHp());
		}else if (type == UnitType.WORKER) {
			buildInfoText.setText(UnitType.WORKER.getPath() + ":   Agility: " + UnitType.WORKER.getMovments() + "\n    " + UnitType.WORKER.getFoodCost()
			+ ",     " + UnitType.WORKER.getGoldCost() + ",     " + UnitType.WORKER.getWoodCost() + "\nDmg: " + UnitType.WORKER.getDmg() + ", Hp: " +UnitType.WORKER.getHp());
		}
		
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
			makeBuilderButton.setDisabled(false);
			makeWarriorButton.setDisabled(false);
			makeTankButton.setDisabled(false);
		}else{
			makeBuilderButton.setDisabled(true);
			makeWarriorButton.setDisabled(true);
			makeTankButton.setDisabled(true);
		}
		if (selectedUnit == null) {
			moveCommandButton.setDisabled(true);
			BuildCommandButton.setDisabled(true);
		}else{
			moveCommandButton.setDisabled(false);
			if (selectedUnit.getType() == UnitType.WORKER) {
				BuildCommandButton.setDisabled(false);
			}
		}
		updateBuyTab(false, UnitType.TANK);
		updateUnitText();
	}

	public void resetButtons() {
		for (int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).setChecked(false);
		}
		
	}
	
	public void resize(int width, int height){
		viewport.update(width, height);
	}

	public void updateGain(int food, int gold, int wood) {
		foodText.setText("Total Increase per turn: " + food);
		goldText.setText("" + gold);
		woodText.setText("" + wood);
		
		
	}

}
