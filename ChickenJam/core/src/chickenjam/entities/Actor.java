package chickenjam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import chickenjam.main.Game;

public class Actor extends B2DSprite {
	protected boolean onGround=false;
	protected float maxSpeed=3;
	protected float gndForce=3;
	protected float airForce=2;
	protected float fallDamping=.93f;
	
	public Actor(Body body) {
		super(body);
		// TODO Auto-generated constructor stub
	}


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
}
