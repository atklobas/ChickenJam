package chickenjam.entities;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;

import chickenjam.handlers.B2DVars;
import chickenjam.main.Game;

public class Player extends B2DSprite {
	
	private final short MAX_HEALTH = 6;
	private short currentHealth;
	private boolean onGround=false;
	private float maxSpeed=3;
	private float gndForce=3;
	private float airForce=2;
	private float fallDamping=.93f;
	private Map<String, Sound> soundMap;
	private boolean crowing = false;
	private long lastCrowed=0;
	
	public Player(Body body) {
		
		super(body);
		
		Texture tex = Game.res.getTexture("bunny");
		TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
		loadSound();
		setAnimation(sprites, 1 / 12f);
		setToMaxHealth();
		
		
	}
	
	public void update(float dt) {
		super.update(dt);
		if(body.getLinearVelocity().y<=.01) {
			Vector2 vel=body.getLinearVelocity();
			vel.y*=fallDamping;
			body.setLinearVelocity(vel);
			Filter filter = body.getFixtureList().first()
					.getFilterData();
			short bits = filter.maskBits;
			bits|=B2DVars.BIT_RED;
			
			filter.maskBits = bits;
			body.getFixtureList().first().setFilterData(filter);
			
			// set new mask bits for foot
			filter = body.getFixtureList().get(1).getFilterData();
			bits &= ~B2DVars.BIT_CRYSTAL;
			filter.maskBits = bits;
			body.getFixtureList().get(1).setFilterData(filter);
		}else {
			Filter filter = body.getFixtureList().first()
					.getFilterData();
			short bits = filter.maskBits;
			bits&=~B2DVars.BIT_RED;
			
			filter.maskBits = bits;
			body.getFixtureList().first().setFilterData(filter);
			
			// set new mask bits for foot
			filter = body.getFixtureList().get(1).getFilterData();
			bits &= ~B2DVars.BIT_CRYSTAL;
			filter.maskBits = bits;
			body.getFixtureList().get(1).setFilterData(filter);
		}
		if(body.getLinearVelocity().equals(Vector2.Zero)) {
			animation.update(-1);
		}
		
	}
	
	public void loadSound() {
		Sound crow;
		crow = Gdx.audio.newSound(Gdx.files.internal("res/sfx/Rudy_rooster_crowing-Shelley-1948282641.mp3"));
//		Sound flap;
//		crow = Gdx.audio.newSound(Gdx.files.internal("res/sfx/Rudy_rooster_crowing-Shelley-1948282641.mp3"));
//		Sound cluck1;
//		crow = Gdx.audio.newSound(Gdx.files.internal("res/sfx/Rudy_rooster_crowing-Shelley-1948282641.mp3"));
//		Sound cluck2;
//		crow = Gdx.audio.newSound(Gdx.files.internal("res/sfx/Rudy_rooster_crowing-Shelley-1948282641.mp3"));
//		Sound cluck3;
//		crow = Gdx.audio.newSound(Gdx.files.internal("res/sfx/Rudy_rooster_crowing-Shelley-1948282641.mp3"));
//		Sound distress;
//		crow = Gdx.audio.newSound(Gdx.files.internal("res/sfx/Rudy_rooster_crowing-Shelley-1948282641.mp3"));
		soundMap = new HashMap<String, Sound>();
		soundMap.put("crow", crow);
//		soundMap.put("flap", flap);
//		soundMap.put("cluck1", cluck1);
//		soundMap.put("cluck1", cluck2);
//		soundMap.put("cluck1", cluck3);
//		soundMap.put("distress", distress);
	}
	
	public void loseHealth() { currentHealth--; }
	public void gainHealth() { currentHealth++; }
	public short getHealth() { return currentHealth; }
	public void setToMaxHealth() { currentHealth = 6; }
	public short getMaxHealth() { return MAX_HEALTH; }

	public void moveLeft() {
		goingRight=false;
		if(onGround) {
			if(body.getLinearVelocity().x<-maxSpeed) {
				body.setLinearVelocity(-maxSpeed,body.getLinearVelocity().y);
			}else if(body.getLinearVelocity().x>0){
				stop();
			}else {
				body.applyForceToCenter(-gndForce, 0,true);
			}
		}else {
			if(body.getLinearVelocity().x<-maxSpeed) {
				body.setLinearVelocity(-maxSpeed,body.getLinearVelocity().y);
			}else if(body.getLinearVelocity().x>0){
				body.applyForceToCenter(-airForce*2, 0,true);
			}else {
				
				body.applyForceToCenter(-airForce, 0,true);
			}
		}
	}
	public void moveRight() {
		goingRight=true;

		if(onGround) {
			if(body.getLinearVelocity().x>maxSpeed) {
				body.setLinearVelocity(maxSpeed,body.getLinearVelocity().y);
			}else if(body.getLinearVelocity().x<0){
				stop();
			}else {
				body.applyForceToCenter(gndForce, 0,true);
			}
		}else {
			if(body.getLinearVelocity().x>maxSpeed) {
				body.setLinearVelocity(maxSpeed,body.getLinearVelocity().y);
			}else if(body.getLinearVelocity().x<0){
				body.applyForceToCenter(gndForce*2, 0,true);
			}else {
				body.applyForceToCenter(gndForce, 0,true);
			}
		}
		
	}
	public void jump() {
		if(onGround) {
			body.applyForceToCenter(body.getLinearVelocity().x, 150, true);
		}
	}

	public void stop() {
		if(this.onGround) {
			if(body.getLinearVelocity().x>.1) {
				body.applyForceToCenter(-gndForce*4, 0,true);
			}else if(body.getLinearVelocity().x<-.1) {
				body.applyForceToCenter(gndForce*4, 0,true);
			}else {
				body.setLinearVelocity(0,body.getLinearVelocity().y);
			}
			
		}
		// TODO Auto-generated method stub
		
	}

	public void onGround(boolean playerOnGround) {
		this.onGround=playerOnGround;
		
	}

	public void flap() {
		body.applyForceToCenter(0, 7, true);
		
	}
	
	public void crow() {
		if(System.currentTimeMillis()-lastCrowed>2000) {		
			soundMap.get("crow").play(0.1f);
			lastCrowed=System.currentTimeMillis();
		}
	}
	
}










