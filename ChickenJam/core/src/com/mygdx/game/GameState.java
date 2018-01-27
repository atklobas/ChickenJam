package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameState {
	private JamChicken inst;
	protected GameStateManager gsm;
	protected SpriteBatch batch;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudCam;
	
	protected GameState(GameStateManager gsm) {
		this.gsm = gsm;
		
		inst = gsm.getInst();
		System.out.println(inst);
		batch = inst.getSpriteBatch();
		cam = inst.getCamera();
		System.out.println(cam);
		hudCam = inst.getHUDCamera();
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public abstract void update(float dt);

	public abstract void render();
}
