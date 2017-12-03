package com.svarttand.ludumdare40.map;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.svarttand.ludumdare40.misc.ResourceHandler;
import com.svarttand.ludumdare40.states.GameStateManager;
import com.svarttand.ludumdare40.states.PlayState;
import com.svarttand.ludumdare40.ui.PlayUI;
import com.svarttand.ludumdare40.units.Unit;


public class HexagonMap {
	
	public static final int HEX_WIDTH = 64;
	public static final int HEX_HEIGHT = 56;
	
	private int sizeX;
	private int sizeY;
	private Hexagon[][] map;
	
	private Vector2 startPos;
	
	private ArrayList<Hexagon> cityList;
	private ArrayList<Hexagon> campList;
	private ArrayList<Hexagon> goldList;
	private ArrayList<Hexagon> ownedGold;
	
	private GameStateManager gsm;
	
	private Random random;
	private PlayState game;
	
	public HexagonMap(int x, int y, GameStateManager gsm, PlayState game){
		this.gsm = gsm;
		sizeX = x;
		sizeY = y;
		map = new Hexagon[x][y];
		goldList = new ArrayList<Hexagon>();
		random = new Random();
		createMap(random);
		cityList = new ArrayList<Hexagon>();
		campList = new ArrayList<Hexagon>();
		
		ownedGold = new ArrayList<Hexagon>();
		
		int randx = random.nextInt((sizeX - 5)) + 3;
		int randy = random.nextInt((sizeY - 5)) + 3;
		
		map[randx][randy].setType(TileType.CITY);
		startPos = new Vector2(map[randx][randy].getPosX(), map[randx][randy].getPosY());
		addCity(map[randx][randy]);
		this.game = game;
		//updateBorders(game.getUI());
	}
	
	private void createMap(Random random){
		System.out.println("xv");
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				int rand = random.nextInt(25);
				TileType type = TileType.GRASS;
				if (rand == 1) {
					type = TileType.GOLD;
				}else if (rand <= 6) {
					type = TileType.FOOD;
				}else if (rand <= 10) {
					type = TileType.DESSERT;
				}else if (rand <= 15) {
					type = TileType.WOOD;
				}
				if (j%2 == 0) {
					map[i][j] = new Hexagon(i*(HEX_WIDTH+HEX_WIDTH*0.5f), j*HEX_HEIGHT*0.5f,i,j, type);
					try {
						map[i][j-1].addNeighbor(map[i][j]);
						map[i][j].addNeighbor(map[i][j-1]);
						System.out.println(i + ", " + j + " added below " + i + ", " + (j-1));
					} catch (Exception e) {
						System.out.println("a");
					}
					try {
						map[i][j-2].addNeighbor(map[i][j]);
						map[i][j].addNeighbor(map[i][j-2]);
						System.out.println(i + ", " + j + " added behind " + (i-1) + ", " + (j));
					} catch (Exception e) {
						System.out.println("b");
					}
					try {
						map[i-1][j-1].addNeighbor(map[i][j]);
						map[i][j].addNeighbor(map[i-1][j-1]);
						System.out.println(i + ", " + j + " added below and behind " + (i-1) + ", " + (j-1));
					} catch (Exception e) {
						System.out.println("c");
					}
					try {
						map[i-1][j+1].addNeighbor(map[i][j]);
						map[i][j].addNeighbor(map[i-1][j+1]);
						System.out.println(i + ", " + j + " added below and behind " + (i-1) + ", " + (j-1));
					} catch (Exception e) {
						System.out.println("c");
					}
				}else{
					map[i][j] = new Hexagon(i*(HEX_WIDTH+HEX_WIDTH*0.5f)+HEX_WIDTH*0.75f, j*HEX_HEIGHT*0.5f,i,j, type);
					try {
						map[i][j-2].addNeighbor(map[i][j]);
						map[i][j].addNeighbor(map[i][j-2]);
						System.out.println(i + ", " + j + " added behind " + (i-1) + ", " + (j));
					} catch (Exception e) {
						System.out.println("d");
					}
//					try {
//						map[i+1][j-1].addNeighbor(map[i][j]);
//						map[i][j].addNeighbor(map[i+1][j-1]);
//						System.out.println(i + ", " + j + " added behind " + (i-1) + ", " + (j));
//					} catch (Exception e) {
//						System.out.println("e");
//					}
					try {
						map[i][j-1].addNeighbor(map[i][j]);
						map[i][j].addNeighbor(map[i][j-1]);
						System.out.println(i + ", " + j + " added behind " + (i-1) + ", " + (j));
					} catch (Exception e) {
						System.out.println("f");
					}
				}
				if (type == TileType.GOLD) {
					goldList.add(map[i][j]);
				}
			}
		}
		
	}
	
	public void render(SpriteBatch batch, TextureAtlas atlas){
		Hexagon placeholder = null;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				placeholder = map[i][j];
				batch.draw(atlas.findRegion(map[i][j].getPath()), map[i][j].posX, map[i][j].posY);
				if (map[i][j].getBorderType() != BorderType.NULL) {
					//System.out.println("Drawing selected at " + map[i][j].getX() + ", " +  map[i][j].getY());
					batch.draw(atlas.findRegion(map[i][j].getBorderType().getPath()), map[i][j].posX, map[i][j].posY);
//					for (int j2 = 0; j2 < placeholder.getVerticies().length; j2++) {
//						batch.draw(atlas.findRegion("point"), placeholder.getVerticies()[j2].x, placeholder.getVerticies()[j2].y);
//					}
				}
			}
		}
		
	}

	public Hexagon getTileWithPoint(Vector2 point) {
		float height = (float) (14.3f * HEX_HEIGHT - point.y);
		point.y = height;
		System.out.println(point.y);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (PolygonChecker.inside_convex_polygon(point, map[i][j].getVerticies())) {
					return map[i][j];
				}
			}
		}
		return null;
	}
	
	public Vector2 getStartPos(){
		return startPos;
	}
	
	public void update(ResourceHandler handler){
		
		for (int j1 = 0; j1 < 3; j1++) {
			int randx = random.nextInt((sizeX - 5)) + 3;
			int randy = random.nextInt((sizeY - 5)) + 3;
			
			Hexagon temp = map[randx][randy];
			boolean notPossibleLocation = false;
			for (int i = 0; i < cityList.size(); i++) {
				cityList.get(i).setSelected(BorderType.BLUE);
				handler.addTilesResourcers(cityList.get(i));
				if (temp.getType() == TileType.GOLD) {
					notPossibleLocation = true;
				}
				if (temp.isSame(cityList.get(i))) {
					notPossibleLocation = true;
				}
				for (int j = 0; j < cityList.get(i).getNeighbors().size(); j++) {
					cityList.get(i).getNeighbors().get(j).setSelected(BorderType.BLUE);
					handler.addTilesResourcers(cityList.get(i).getNeighbors().get(j));
					if (temp.isSame(cityList.get(i).getNeighbors().get(j))) {
						notPossibleLocation = true;
					}
				}
			}
			if (!notPossibleLocation && campList.size() <= handler.getGold()*0.08f) {
				temp.setType(TileType.CAMP);
				campList.add(temp);
				System.out.println("Added camp");
			}
		}
		
			
		
	}
	
	public void updateBorders(PlayUI ui){
		int wood = 0;
		int food = 0;
		int gold = 0;
		for (int i = 0; i < cityList.size(); i++) {
			cityList.get(i).setSelected(BorderType.BLUE);
			wood += cityList.get(i).getType().getWood();
			food += cityList.get(i).getType().getFood();
			gold += cityList.get(i).getType().getGold();
			for (int j = 0; j < cityList.get(i).getNeighbors().size(); j++) {
				cityList.get(i).getNeighbors().get(j).setSelected(BorderType.BLUE);
				wood += cityList.get(i).getNeighbors().get(j).getType().getWood();
				food += cityList.get(i).getNeighbors().get(j).getType().getFood();
				gold += cityList.get(i).getNeighbors().get(j).getType().getGold();
				if (cityList.get(i).getNeighbors().get(j).getType() == TileType.GOLD) {
					
				}
			}
		}
		ui.updateGain(food, gold, wood);
		if (ownedGold.size() == getTotalGold()) {
			System.out.println("game won");
			gsm.pop();
			gsm.peek().updateScreen(false, game.getTurnCounter());
		}
	}

	public void addCity(Hexagon selectedHexagon) {
		cityList.add(selectedHexagon);
		for (int i = 0; i < selectedHexagon.getNeighbors().size(); i++) {
			selectedHexagon.getNeighbors().get(i).setSelected(BorderType.BLUE);
		}
		addGold(selectedHexagon.getNeighbors());
	}
	
	public void removeCity(Hexagon city){
		if (cityList.size()> 1) {
			cityList.remove(city);
			city.setType(city.getPreviousType());
			for (int i = 0; i < city.getNeighbors().size(); i++) {
				city.getNeighbors().get(i).setSelected(BorderType.NULL);
			}
		}else{
			System.out.println("Game Lost!");
			
			gsm.pop();
			gsm.peek().updateScreen(false, game.getTurnCounter());
		}
		removeGold(city.getNeighbors());
		
	}

	public ArrayList<Hexagon> getCityList() {
		// TODO Auto-generated method stub
		return cityList;
	}
	
	public ArrayList<Hexagon> getCampList(){
		return campList;
	}

	public void removeCamp(Hexagon currentPos) {
		campList.remove(currentPos);
		
	}
	
	public int getTotalGold(){
		return goldList.size();
	}
	
	public int getOwnedGold(){
		return ownedGold.size();
	}
	
	public void addGold(ArrayList<Hexagon> neghbors){
		for (int i = 0; i < neghbors.size(); i++) {
			if (neghbors.get(i).getType() == TileType.GOLD) {
				boolean alreadyInList = false;
				for (int j = 0; j < ownedGold.size(); j++) {
					if (neghbors.get(i).isSame(ownedGold.get(j))) {
						alreadyInList = true;
					}
				}
				if (!alreadyInList) {
					ownedGold.add(neghbors.get(i));
				}
			}
			
		}
	}
	
	public void removeGold(ArrayList<Hexagon> neghbors){
		for (int i = 0; i < neghbors.size(); i++) {
			if (neghbors.get(i).getType() == TileType.GOLD) {
				boolean hasCity = false;
				for (int j = 0; j < neghbors.get(i).getNeighbors().size(); j++) {
					if (neghbors.get(i).getNeighbors().get(j).getType() == TileType.CITY) {
						hasCity = true;
					}
				}
				if (!hasCity) {
					ownedGold.remove(neghbors.get(i));
				}
			}
		}
	}
	
	

}
