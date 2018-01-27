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
		default:;
		}
		
		if(k == Keys.Z) {
			MyInput.setKey(MyInput.LEFT, true);
		}
		if(k == Keys.X) {
			MyInput.setKey(MyInput.RIGHT, true);
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
		default:;
		}
		if(k == Keys.Z) {
			MyInput.setKey(MyInput.LEFT, false);
		}
		if(k == Keys.X) {
			MyInput.setKey(MyInput.RIGHT, false);
		}
		return true;
	}
	
}
