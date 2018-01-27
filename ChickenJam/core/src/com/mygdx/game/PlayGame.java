package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World; 

public class PlayGame extends GameState{
	public static final float ppm=64;
	World world;
	Box2DDebugRenderer debug;
	OrthographicCamera debugCam=new OrthographicCamera();
	public PlayGame(GameStateManager gsm) {
		super(gsm);
		debugCam.setToOrtho(false,gsm.getInst().V_WIDTH/ppm,gsm.getInst().V_HEIGHT/ppm);
		System.out.println(gsm);
		world=new World(new Vector2(0,-9.8f),true);
		debug=new Box2DDebugRenderer();
		BodyDef floorDef=new BodyDef();
		floorDef.position.set(100/ppm,100/ppm);
		floorDef.type=	BodyType.StaticBody;
		
		PolygonShape floorShape=new PolygonShape();
		floorShape.setAsBox(100/ppm, 2/ppm);
		FixtureDef def=new FixtureDef();
		def.shape=floorShape;
		Body floor=world.createBody(floorDef);
		floor.createFixture(def);
		floorDef.position.set(100/ppm,200/ppm);
		floorDef.type=BodyType.DynamicBody;
		floor=world.createBody(floorDef);
		floor.createFixture(def);
	}

	@Override
	public void update(float dt) {
		world.step(dt, 8, 3);
		//System.out.println("updating");
		
	}

	@Override
	public void render() {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		debug.render(world, this.debugCam.combined);
		//System.out.println("rendering")		;
	}

}
