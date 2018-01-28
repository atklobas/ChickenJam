package chickenjam.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import chickenjam.handlers.Content;
import chickenjam.handlers.GameStateManager;
import chickenjam.handlers.MyInput;
import chickenjam.handlers.MyInputProcessor;

public class Game implements ApplicationListener {
	
	public static final String TITLE = "Chicken Jam";
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;
	
	public static final float STEP = 1 / 60f;
	private float accum;
	
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	
	private GameStateManager gsm;
	
	public static Content res;
	
	public void create() {
		
		//Texture.setEnforcePotImages(false);
		Gdx.input.setInputProcessor(new MyInputProcessor());
		
		res = new Content();
		res.loadTexture("res/images/bunny.png", "bunny");
		res.loadTexture("res/images/crystal.png", "crystal");
		res.loadTexture("res/images/hud.png", "hud");
		
		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		gsm = new GameStateManager(this);
		
	}
	
	public void render() {
		
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP) {
			accum -= STEP;
			gsm.update(STEP);
			MyInput.update();
		}
		gsm.render();
		
	}
	
	public void dispose() {
		
	}
	
	public SpriteBatch getSpriteBatch() { return sb; }
	public OrthographicCamera getCamera() { return cam; }
	public OrthographicCamera getHUDCamera() { return hudCam; }
	
	public void resize(int w, int h) {}
	public void pause() {}
	public void resume() {}
	
}
