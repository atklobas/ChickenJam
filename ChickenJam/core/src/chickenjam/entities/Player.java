package chickenjam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import chickenjam.main.Game;

public class Player extends B2DSprite {
	
	private final short MAX_HEALTH = 6;
	private short currentHealth;
//	private short maxHealth;
	private boolean onGround=false;
	private float maxSpeed=3;
	private float gndForce=2;
	private float airForce=1;
	
	public Player(Body body) {
		
		super(body);
		
		Texture tex = Game.res.getTexture("bunny");
		TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
		
		setAnimation(sprites, 1 / 12f);
		setToMaxHealth();
		
	}
	
	public void loseHealth() { currentHealth--; }
	public void gainHealth() { currentHealth++; }
	public short getHealth() { return currentHealth; }
	public void setToMaxHealth() { currentHealth = 6; }
	public short getMaxHealth() { return MAX_HEALTH; }

	public void moveLeft() {
		if(onGround) {
			if(body.getLinearVelocity().x<-maxSpeed) {
				body.setLinearVelocity(-maxSpeed,body.getLinearVelocity().y);
			}else if(body.getLinearVelocity().x>0){
				stop();
			}else {
				body.applyForceToCenter(-gndForce, 0,true);
			}
		}
		/*if(cl.isPlayerOnGround()) {
			//player.getBody().setL
			player.getBody().setLinearVelocity(-1,player.getBody().getLinearVelocity().y);
		// TODO Auto-generated method stub
		*/
	}
	public void moveRight() {
		if(onGround) {
			if(body.getLinearVelocity().x>maxSpeed) {
				body.setLinearVelocity(maxSpeed,body.getLinearVelocity().y);
			}else if(body.getLinearVelocity().x<0){
				stop();
			}else {
				body.applyForceToCenter(gndForce, 0,true);
			}
		}
		
	}
	public void jump() {
		if(onGround) {
			body.applyForceToCenter(body.getLinearVelocity().x, 250, true);
		}
	}

	public void stop() {
		if(this.onGround) {
			if(body.getLinearVelocity().x>.3) {
				body.applyForceToCenter(-gndForce*2, 0,true);
			}else if(body.getLinearVelocity().x<-.3) {
				body.applyForceToCenter(gndForce*2, 0,true);
			}else {
				body.setLinearVelocity(0,body.getLinearVelocity().y);
			}
			
		}
		// TODO Auto-generated method stub
		
	}

	public void onGround(boolean playerOnGround) {
		this.onGround=playerOnGround;
		
	}
	
	
}










