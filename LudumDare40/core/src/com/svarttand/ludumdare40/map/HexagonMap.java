package com.svarttand.ludumdare40.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class HexagonMap {
	
	public static final int HEX_WIDTH = 64;
	public static final int HEX_HEIGHT = 56;
	
	private int sizeX;
	private int sizeY;
	private Hexagon[][] map;
	
	public HexagonMap(int x, int y){
		sizeX = x;
		sizeY = y;
		map = new Hexagon[x][y];
		createMap();
		
		
	}
	
	private void createMap(){
		System.out.println("xv");
		for (int j = 0; j < map.length; j++) {
			for (int i = 0; i < map[j].length; i++) {
				if (j%2 == 0) {
					map[i][j] = new Hexagon(i*(HEX_WIDTH+HEX_WIDTH*0.5f), j*HEX_HEIGHT*0.5f, TileType.GRASS);
					try {
						map[i][j-1].addNeighbor(map[i][j]);
						map[i][j].addNeighbor(map[i][j-1]);
						System.out.println(i + ", " + j + " added below " + i + ", " + (j-1));
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						map[i-1][j].addNeighbor(map[i][j]);
						map[i][j].addNeighbor(map[i-1][j]);
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
					map[i][j] = new Hexagon(i*(HEX_WIDTH+HEX_WIDTH*0.5f)+HEX_WIDTH*0.75f, j*HEX_HEIGHT*0.5f, TileType.GRASS);
					try {
						map[i-1][j].addNeighbor(map[i][j]);
						map[i][j].addNeighbor(map[i-1][j]);
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
			}
		}
		
	}
	
	public void render(SpriteBatch batch, TextureAtlas atlas){
		Hexagon placeholder = null;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				batch.draw(atlas.findRegion(map[i][j].getPath()), map[i][j].posX, map[i][j].posY);
//				if (map[i][j].isSelected()) {
//					placeholder = map[i][j];
//					//System.out.println("Drawing selected at " + map[i][j].getX() + ", " +  map[i][j].getY());
//					batch.draw(atlas.findRegion("SelectedIndicator2"), map[i][j].getX(), map[i][j].getY());
//					
//				}
			}
		}
	}

}
