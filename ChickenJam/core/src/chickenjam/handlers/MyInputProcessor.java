package chickenjam.handlers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class MyInputProcessor extends InputAdapter {
	
	public boolean keyDown(int k) {
		switch(k) {
		case Keys.W:MyInput.setKey(MyInput.UP, true);
			break;
		case Keys.A:MyInput.setKey(MyInput.LEFT, true);
			break;
		case Keys.S:MyInput.setKey(MyInput.DOWN, true);
			break;
		case Keys.D:MyInput.setKey(MyInput.RIGHT, true);
			break;
		case Keys.SPACE:MyInput.setKey(MyInput.CROW, true);
			break;
		case Keys.ENTER:MyInput.setKey(MyInput.ENTER, true);
			break;
		default:;
		}
		
		return true;
	}
	
	public boolean keyUp(int k) {
		switch(k) {
		case Keys.W:MyInput.setKey(MyInput.UP, false);
			break;
		case Keys.A:MyInput.setKey(MyInput.LEFT, false);
			break;
		case Keys.S:MyInput.setKey(MyInput.DOWN, false);
			break;
		case Keys.D:MyInput.setKey(MyInput.RIGHT, false);
			break;
		case Keys.SPACE:MyInput.setKey(MyInput.CROW, false);
			break;
		case Keys.ENTER:MyInput.setKey(MyInput.ENTER, false);
			break;
		default:;
		}
		return true;
	}
	
}
