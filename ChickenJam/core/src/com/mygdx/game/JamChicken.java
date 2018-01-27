package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class JamChicken extends ApplicationAdapter {
	Texture img;
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;
	
	public static final float STEP = 1 / 60f;
	private float accum;
	GameStateManager gsm;
	
	
	
	@Override
	public void create () {
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		
		
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		gsm=new GameStateManager(this);
	}

	@Override
	public void render () {
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP) {
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
		}
	/*	Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
		*/
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	public SpriteBatch getSpriteBatch() { return batch; }
	public OrthographicCamera getCamera() { return cam; }
	public OrthographicCamera getHUDCamera() { return hudCam; }
	
}
