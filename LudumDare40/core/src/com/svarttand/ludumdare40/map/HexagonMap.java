package com.svarttand.ludumdare40.map;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.svarttand.ludumdare40.misc.ResourceHandler;
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
	private int ownedGold;
	
	
	private Random random;
	
	
	public HexagonMap(int x, int y){
		sizeX = x;
		sizeY = y;
		map = new Hexagon[x][y];
		goldList = new ArrayList<Hexagon>();
		random = new Random();
		createMap(random);
		cityList = new ArrayList<Hexagon>();
		campList = new ArrayList<Hexagon>();
		
		ownedGold = 0;
		
		int randx = random.nextInt((sizeX - 5)) + 3;
		int randy = random.nextInt((sizeY - 5)) + 3;
		
		map[randx][randy].setType(TileType.CITY);
		startPos = new Vector2(map[randx][randy].getPosX(), map[randx][randy].getPosY());
		addCity(map[randx][randy]);
		updateBorders();
	}
	
	private void createMap(Random random){
		System.out.println("xv");
		for (int j = 0; j < map.length; j++) {
			for (int i = 0; i < map[j].length; i++) {
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
					map[i][j] = new Hexagon(i*(HEX_WIDTH+HEX_WIDTH*0.5f), j*HEX_HEIGHT*0.5f, type);
					try {
						map[i][j-1].addNeighbor(map[i][j]);
						map[i][j].addNeighbor(map[i][j-1]);
						System.out.println(i + ", " + j + " added below " + i + ", " + (j-1));
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						map[i][j-2].addNeighbor(map[i][j]);
						map[i][j].addNeighbor(map[i][j-2]);
						System.out.println(i + ", " + j + " added behind " + (i-1) + ", " + (j));
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						map[i-1][j-1].addNeighbor(map[i][j]);
						map[i][j].addNeighbor(map[i-1][j-1]);
						System.out.println(i + ", " + j + " added below and behind " + (i-1) + ", " + (j-1));
					} catch (Exception e) {
						// TODO: handle exception
					}
				}else{
					map[i][j] = new Hexagon(i*(HEX_WIDTH+HEX_WIDTH*0.5f)+HEX_WIDTH*0.75f, j*HEX_HEIGHT*0.5f, type);
					try {
						map[i][j-2].addNeighbor(map[i][j]);
						map[i][j].addNeighbor(map[i][j-2]);
						System.out.println(i + ", " + j + " added behind " + (i-1) + ", " + (j));
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						map[i+1][j-1].addNeighbor(map[i][j]);
						map[i][j].addNeighbor(map[i+1][j-1]);
						System.out.println(i + ", " + j + " added behind " + (i-1) + ", " + (j));
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						map[i][j-1].addNeighbor(map[i][j]);
						map[i][j].addNeighbor(map[i][j-1]);
						System.out.println(i + ", " + j + " added behind " + (i-1) + ", " + (j));
					} catch (Exception e) {
						// TODO: handle exception
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
		int randx = random.nextInt((sizeX - 5)) + 3;
		int randy = random.nextInt((sizeY - 5)) + 3;
		
		Hexagon temp = map[randx][randy];
		boolean notPossibleLocation = false;
		for (int i = 0; i < cityList.size(); i++) {
			cityList.get(i).setSelected(BorderType.BLUE);
			handler.addTilesResourcers(cityList.get(i));
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
	
	public void updateBorders(){
		ownedGold = 0;
		for (int i = 0; i < cityList.size(); i++) {
			cityList.get(i).setSelected(BorderType.BLUE);
			for (int j = 0; j < cityList.get(i).getNeighbors().size(); j++) {
				cityList.get(i).getNeighbors().get(j).setSelected(BorderType.BLUE);
				if (cityList.get(i).getNeighbors().get(j).getType() == TileType.GOLD) {
					ownedGold++;
				}
			}
		}
	}

	public void addCity(Hexagon selectedHexagon) {
		cityList.add(selectedHexagon);
		for (int i = 0; i < selectedHexagon.getNeighbors().size(); i++) {
			selectedHexagon.getNeighbors().get(i).setSelected(BorderType.BLUE);
		}
	}
	
	public void removeCity(Hexagon city){
		cityList.remove(city);
		city.setType(city.getPreviousType());
		for (int i = 0; i < city.getNeighbors().size(); i++) {
			city.getNeighbors().get(i).setSelected(BorderType.NULL);
		}
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
		return ownedGold;
	}
	
	

}
