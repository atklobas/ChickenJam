package chickenjam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;

import chickenjam.handlers.B2DVars;
import chickenjam.main.Game;

public class Player extends Actor {
	
	private final short MAX_HEALTH = 6;
	private short currentHealth;
//	private short maxHealth;
	private float invunerable=0;
	public Player(Body body) {
		
		super(body);
		
		Texture tex = Game.res.getTexture("bunny");
		TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
		
		setAnimation(sprites, 1 / 12f);
		setToMaxHealth();
		
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
	
	public void loseHealth() { currentHealth--; }
	public void gainHealth() { currentHealth++; }
	public short getHealth() { return currentHealth; }
	public void setToMaxHealth() { currentHealth = 6; }
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
	
	
}










