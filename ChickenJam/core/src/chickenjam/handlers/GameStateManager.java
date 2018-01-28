package chickenjam.handlers;

import java.util.Stack;

import chickenjam.main.Game;
import chickenjam.states.Death;
import chickenjam.states.GameState;
import chickenjam.states.Play;
import chickenjam.states.Timeout;
import chickenjam.states.Win;

public class GameStateManager {
	
	public static final  int TIMEOUT=10;

	private Game game;
	
	private Stack<GameState> gameStates;
	
	public static final int PLAY = 912837;
	public static final int DEAD=1;
	public static final int WON=2;
	
	public GameStateManager(Game game) {
		this.game = game;
		gameStates = new Stack<GameState>();
		pushState(PLAY);
	}
	
	public Game game() { return game; }
	
	public void update(float dt) {
		gameStates.peek().update(dt);
	}
	
	public void render() {
		gameStates.peek().render();
	}
	
	private GameState getState(int state) {
		if(state == PLAY) return new Play(this);
		if(state==DEAD)return new Death(this);
		if(state==WON)return new Win(this);
		if(state==TIMEOUT)return new Timeout(this);
		return null;
	}
	
	public void setState(int state) {
		popState();
		pushState(state);
	}
	
	public void pushState(int state) {
		gameStates.push(getState(state));
	}
	
	public void popState() {
		GameState g = gameStates.pop();
		g.dispose();
	}
	
}















