package com.svarttand.ludumdare40.map;

public enum BorderType {
	WHITE("WhiteBorder"),
	BLACK("WhiteBorder"),
	NULL("");
	
	private String path;
	
	private BorderType(String p){
		path = p;
	}

	public String getPath() {
		return path;
	}

}
