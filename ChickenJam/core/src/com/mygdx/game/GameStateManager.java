package com.mygdx.game;

import java.util.Stack;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateManager {
	
	
private JamChicken inst;
	
	private Stack<GameState> GameStates;
	protected SpriteBatch batch;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudCam;
	public static final int PLAY = 912837;
	
	public enum States{
		PLAY,
		PAUSE,
		MENU;
	}
	
	public GameStateManager(JamChicken inst) {
		this.inst = inst;
		GameStates = new Stack<GameState>();
		pushState(PLAY);
		this.batch=inst.getSpriteBatch();
		this.cam=inst.getCamera();
	}
	
	public JamChicken getInst() { return inst; }
	
	public void update(float dt) {
		GameStates.peek().update(dt);
	}
	
	public void render() {
		GameStates.peek().render();
	}
	
	private GameState getState(int state) {
		System.out.println("here");
		if(state == PLAY) return new PlayGame(this);
		return null;
	}
	
	public void setState(int state) {
		popState();
		pushState(state);
	}
	
	public void pushState(int state) {
		GameStates.push(getState(state));
	}
	
	public void popState() {
		GameState g = GameStates.pop();
		g.dispose();
	}
	
	
/*
private Stack<GameStates> states=new Stack< GameStates>();

public void update(float dt) {
	states.peek().update(dt);
	
}
public void render() {
	states.peek().render();
}
*/

}
