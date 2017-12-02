package com.svarttand.ludumdare40.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;


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
				if (map[i][j].getBorderType() != BorderType.NULL) {
					placeholder = map[i][j];
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

}
