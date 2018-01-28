package chickenjam.entities;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;

import chickenjam.handlers.B2DVars;
import chickenjam.main.Game;
import chickenjam.states.Play;

public class Player extends Actor {
	
	private final short MAX_HEALTH = 6;
	private short currentHealth;
	private boolean dead = false;

//	private short maxHealth;
	private float invunerable=0;
	private boolean onGround=false;
	private float maxSpeed=3;
	private float gndForce=3;
	private float airForce=2;
	private float fallDamping=.93f;
	private Map<String, Sound> soundMap;
	private long lastCrowed=0;
	private long diedAt=0;
	private long wonAt=0;
	private float width,height;
	public Player(Body body) {
		super(body);
		;
		width=32;
		height=32;
		Texture tex = Game.res.getTexture("rooster");
		TextureRegion[] sprites = TextureRegion.split(tex, 209, 209)[0];
		loadSound();
		setAnimation(sprites, 1 / 12f);
		setToMaxHealth();
		
		
	}
	public void render(SpriteBatch sb) {
		//animation.getFrame().flip(body.getLinearVelocity().x<-.1,false);
		int reverse=1;
		if(!goingRight) {
			reverse=-1;
		}
		
		sb.begin();
		sb.draw(
			animation.getFrame(),
			body.getPosition().x * B2DVars.PPM - reverse*width / 2,
			body.getPosition().y * B2DVars.PPM - height / 2,
			reverse*width,
			height
		);
		sb.end();
	}
	
	public void update(float dt) {
		super.update(dt);
		if(invunerable>0) {
			invunerable-=dt;
			System.out.println(invunerable);
		}
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
	
	public void loseHealth() { 
		if (!(getHealth() <= 0)) {
			currentHealth--;
			if (getHealth() <= 0) {
				dead = true;
				
				diedAt=System.currentTimeMillis();
				System.out.println(diedAt);
				death();
			}
		}
	}
	
	public void gainHealth() {
		if (!(getHealth() >= MAX_HEALTH)) {
			currentHealth++;
		}
	}
	
	public short getHealth() { return currentHealth; }
	public void setToMaxHealth() { currentHealth = MAX_HEALTH; }
	public short getMaxHealth() { return MAX_HEALTH; }


	public void flap() {
		body.applyForceToCenter(0, 7, true);
		
	}
	public void attacked(boolean playerAttacked) {
		if(playerAttacked&&invunerable<=0) {
			this.loseHealth();
			invunerable=1;
			
		}
		
	}
	
	public void crow() {
		if(System.currentTimeMillis()-lastCrowed>2000) {		
			
			soundMap.get("crow").play(0.1f);
			lastCrowed=System.currentTimeMillis();
			
		}
	}
	
	public boolean isDead() {
		return dead;
	}
		
	public void death() {
		super.canMove = false;
//		Texture tex = Game.res.getTexture("rooster_dead");
//		TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
//		Play.class.music.stop();
//		Deaggro enemies
//		Wait a couple seconds
//		call continue screen
//		Game.this.gameOver();
	}

	public long diedAt() {
		// TODO Auto-generated method stub
		return diedAt;
	}
	public long wonAt() {
		return this.wonAt;
	}
	public void win() {
		this.wonAt=System.currentTimeMillis();
	}
	
}










