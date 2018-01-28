package chickenjam.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import chickenjam.handlers.GameStateManager;
import chickenjam.handlers.MyInput;



public class Death extends GameState{
	
	
	
	BitmapFont font = new BitmapFont(); 
	ShapeRenderer sr;
	public Death(GameStateManager gsm) {
		super(gsm);
		sr=new ShapeRenderer();
	}

	@Override
	public void handleInput() {
		if(MyInput.isDown(MyInput.ENTER)) {
			gsm.popState();
			gsm.pushState(gsm.PLAY);
		
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		handleInput();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.begin();
		//render
	    font.draw(sb, "The other animals played too rough.", 80, 128);
	    font.draw(sb, "The farmer helped you back to the coop.", 80, 100);
	    font.draw(sb, "Press enter to continue.", 100, 80);
	    sb.end();
		/*
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(Color.RED);
		sr.rect(0, 0, 10, 10);
		//sb.draw
		sr.end();
		*/
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
