package com.svarttand.ludumdare40.states;

import java.util.Stack;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateManager {
	
	private Stack<State> states;
    public AssetManager assetManager;

    public GameStateManager(AssetManager manager){
        assetManager = manager;
        states = new Stack<State>();
    }

    public void push(State state){
        states.push(state);
    }

    public void pop(){
        states.pop();
    }

    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }
    public State peek(){
        return states.peek();
    }

    public void update(float dt){
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }

    public void dispose(){
        for (int i = 0; i < states.size(); i++) {
            states.pop().dispose();
        }
        assetManager.dispose();
        System.out.println("Disposed!");
    }
    public void resize(int width, int height){
        states.peek().resize(width,height);
    }

}
