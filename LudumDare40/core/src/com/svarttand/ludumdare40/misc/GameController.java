package com.svarttand.ludumdare40.misc;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.svarttand.ludumdare40.Application;
import com.svarttand.ludumdare40.map.BorderType;
import com.svarttand.ludumdare40.map.Hexagon;
import com.svarttand.ludumdare40.map.TileType;
import com.svarttand.ludumdare40.states.PlayState;


public class GameController implements InputProcessor{
	
	private PlayState game;
	private Hexagon currentSelected;
	private Hexagon previousSelected;
	
	private ArrayList<Hexagon> possiblePlaces;
	
	private Command currentCommand;
	
	public GameController(PlayState game) {
		this.game = game;
		currentCommand = Command.NO_COMMAND;
		possiblePlaces = new ArrayList<Hexagon>();
		previousSelected = null;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (screenY < Gdx.graphics.getHeight()*0.8125f) {
			
			Vector2 v  = convertToGameCordinates(screenX, screenY);
			System.out.println(v.x + ", " + v.y);
			if (currentSelected != null) {
				currentSelected.setSelected(BorderType.NULL);
				previousSelected = currentSelected;
			}
			game.getMap().updateBorders();
			if (currentCommand == Command.NO_COMMAND) {
				if (button == 0) {
					try {
						currentSelected = game.getMap().getTileWithPoint(v);
						currentSelected.setSelected(BorderType.WHITE);
//						for (int i = 0; i < currentSelected.getNeighbors().size(); i++) {
//							currentSelected.getNeighbors().get(i).setSelected(BorderType.WHITE);
//						}
						game.getUI().setHex(currentSelected);
						System.out.println(currentSelected);
					}catch (Exception e) {
						System.out.println(e);
					}
				}else if (button == 1) {
					if (currentSelected != null && currentSelected.getUnit() != null) {
						if (currentSelected.getUnit().getMovmentsLeft() > 0) {
							game.getController().setMovmentPossibilities(currentSelected.getUnit().getMovmentsLeft());
							currentCommand = Command.MOVE;
						}
					}
				}
				
			}
			if (currentCommand == Command.ATTACK) {
				
			}
			
			
			if (currentCommand == Command.MOVE) {
				try {
					currentSelected = game.getMap().getTileWithPoint(v);
					for (int i = 0; i < possiblePlaces.size(); i++) {
						
						if (possiblePlaces.get(i).isSame(currentSelected)) {
							if (currentSelected.getUnit() != null) {
								previousSelected.getUnit().attack(currentSelected, game.getEnemyUnitHandler(), game.getUnitHandler());
							}else{
								previousSelected.getUnit().move(currentSelected, currentSelected.getType().getMovmentCost(), game.getMap());
								if (currentSelected.getType() == TileType.CAMP) {
									currentSelected.setType(currentSelected.getPreviousType());
									game.getMap().removeCamp(currentSelected);
								}
							}
							
						}else{
							int k = 0;
							if (possiblePlaces.get(i).getType() == TileType.CITY) {
								possiblePlaces.get(i).setSelected(BorderType.BLUE);
								k = possiblePlaces.get(i).getNeighbors().size();
							}
							for (int j = k; j < possiblePlaces.get(i).getNeighbors().size(); j++) {
								if (possiblePlaces.get(i).getNeighbors().get(j).getType() == TileType.CITY) {
									possiblePlaces.get(i).setSelected(BorderType.BLUE);
									break;
								}else{
									possiblePlaces.get(i).setSelected(BorderType.NULL);
								}
							}
							
						}
						
					}
					currentSelected.setSelected(BorderType.WHITE);
					currentCommand = Command.NO_COMMAND;
					game.getUI().resetButtons();
					possiblePlaces.clear();
//					for (int i = 0; i < currentSelected.getNeighbors().size(); i++) {
//						currentSelected.getNeighbors().get(i).setSelected(BorderType.WHITE);
//					}
					game.getUI().setHex(currentSelected);
					System.out.println(currentSelected);
				}catch (Exception e) {
					System.out.println(e);
				}
			}
			game.getUI().resetButtons();
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private Vector2 convertToGameCordinates(int x, int y){
		Vector2 v = new Vector2(0,0);
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		 w = w / Application.V_WIDTH;
		 h = h / Application.V_HEIGHT;
		 
		 w = x / w;
		 h = y / h;
		 
		 w = (float) (game.getCam().position.x + w - Application.V_WIDTH * 0.5);
		 h = (float) (-game.getCam().position.y + h + Application.V_HEIGHT*0.5);
		 
		 
		 v.x = w;
		 v.y = h;
		
		return v;
	}
	
	public void setCommand(Command command){
		currentCommand = command;
	}

	public void setMovmentPossibilities(int movmentsLeft) {
		System.out.println("here!");
		ArrayList<Hexagon> temp = new ArrayList<Hexagon>();
		for (int i = 0; i < currentSelected.getNeighbors().size(); i++) {
			currentSelected.getNeighbors().get(i).setDistance(currentSelected.getNeighbors().get(i).getType().getMovmentCost());
			if ((currentSelected.getNeighbors().get(i).getUnit() == null || currentSelected.getNeighbors().get(i).getUnit().getType().isEnemy())&& currentSelected.getNeighbors().get(i).getType().getMovmentCost() <= movmentsLeft) {
				possiblePlaces.add(currentSelected.getNeighbors().get(i));
			}
			
		}
//		Hexagon hexTemp;
//		while(!temp.isEmpty()){
//			hexTemp = temp.get(0);
//			if (temp.get(0).getDistanceFromStart() == movmentsLeft && temp.get(0).getUnit() == null) {
//				possiblePlaces.add(temp.get(0));
//				System.out.println("added");
//				temp.remove(0);
//			}else if (temp.get(0).getDistanceFromStart() < movmentsLeft) {
//				for (int i = 0; i < temp.get(0).getNeighbors().size(); i++) {
//					if (temp.get(0).getNeighbors().get(i).getDistanceFromStart() == 0 && !temp.get(0).getNeighbors().get(i).isSame(currentSelected)) {
//						temp.get(0).getNeighbors().get(i).setDistance(temp.get(0).getNeighbors().get(i).getType().getMovmentCost());
//						temp.add(temp.get(0).getNeighbors().get(i));
//						if (temp.get(0).getUnit() == null) {
//							possiblePlaces.add(temp.get(0));
//							System.out.println("added");
//						}
//						temp.remove(0);
//					}
//				}
//			}else if (temp.get(0).getDistanceFromStart() > movmentsLeft) {
//				temp.remove(0);
//			}
//		}
		System.out.println("loop passed");
		
		for (int i = 0; i < possiblePlaces.size(); i++) {
			possiblePlaces.get(i).setSelected(BorderType.WHITE);
		}
		
	}

}
