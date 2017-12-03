package com.svarttand.ludumdare40.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svarttand.ludumdare40.Application;
import com.svarttand.ludumdare40.states.GameStateManager;
import com.svarttand.ludumdare40.states.PlayState;

public class MenuUI {
	
	private TextButton.TextButtonStyle style;
	private Skin skin;
	private BitmapFont font;
	private Stage stage;
	
	private Button playButton;
	private Label text;
	
	public MenuUI(final TextureAtlas textureAtlas, final GameStateManager gsm, Viewport viewport, OrthographicCamera cam){
		stage = new Stage(viewport);
		
		 cam.update();
	     viewport.apply();
	     
	     font = new BitmapFont();
	     skin = new Skin(textureAtlas);
	     style = new TextButton.TextButtonStyle();
	     style.font = font;
	     style.up = skin.getDrawable("MenuButton");
	     style.down = skin.getDrawable("MenuButtonPressed");
	     style.checked = skin.getDrawable("MenuButtonPressed");
	     
	     playButton = new TextButton("PLAY", style);
	     playButton.setPosition((Application.V_WIDTH*0.5f-playButton.getWidth()*0.5f), Application.V_HEIGHT*0.3f-playButton.getHeight());
	     playButton.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	             gsm.push(new PlayState(gsm, textureAtlas));
	             playButton.setChecked(false);
	            }
	        });
	     
	     stage.addActor(playButton);
	     
	     text = new Label("Made by Albert Lindberg in libgdx for Ludum Dare in 48 hours", new LabelStyle(font, Color.WHITE));
		 text.setPosition(Application.V_WIDTH *0.5f - text.getWidth()*0.5f, Application.V_HEIGHT*0.06f);
		 
		 stage.addActor(text);
		 
	     Gdx.input.setInputProcessor(stage);
	}
	
	
	public Stage getStage(){
		return stage;
	}


	public void dispose() {
		stage.dispose();
		
	}

}
