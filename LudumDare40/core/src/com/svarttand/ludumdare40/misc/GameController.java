package com.svarttand.ludumdare40.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.svarttand.ludumdare40.Application;
import com.svarttand.ludumdare40.map.BorderType;
import com.svarttand.ludumdare40.map.Hexagon;
import com.svarttand.ludumdare40.states.PlayState;


public class GameController implements InputProcessor{
	
	private PlayState game;
	private Hexagon currentSelected;
	
	private Command currentCommand;
	
	public GameController(PlayState game) {
		this.game = game;
		currentCommand = Command.NO_COMMAND;
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
			if (currentCommand == Command.NO_COMMAND) {
				
				if (currentSelected != null) {
					currentSelected.setSelected(BorderType.NULL);
				}
				
				try {
					currentSelected = game.getMap().getTileWithPoint(v);
					currentSelected.setSelected(BorderType.WHITE);
//					for (int i = 0; i < currentSelected.getNeighbors().size(); i++) {
//						currentSelected.getNeighbors().get(i).setSelected(BorderType.WHITE);
//					}
					game.getUI().setHex(currentSelected);
					System.out.println(currentSelected);
				}catch (Exception e) {
					System.out.println(e);
				}
			}
			if (currentCommand == Command.ATTACK) {
				
			}
			if (currentCommand == Command.MOVE) {
				
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

}
