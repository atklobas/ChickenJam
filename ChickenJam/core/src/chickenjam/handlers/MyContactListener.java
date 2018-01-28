package chickenjam.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class MyContactListener implements ContactListener {
	
	private int numFootContacts;
	private Array<Body> bodiesToRemove;
	
	public MyContactListener() {
		super();
		bodiesToRemove = new Array<Body>();
	}
	boolean playerAttacked=false;
	
	// called when two fixtures start to collide
	public void beginContact(Contact c) {
		
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		//System.out.println(c.getChildIndexA());
		if(fa == null || fb == null) return;
		
		
		boolean enemy=false;
		boolean player=false;
		if(fa.getUserData() != null && fa.getUserData().equals("enemy")) {
			enemy=true;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("enemy")) {
			enemy=true;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("player")) {
			player=true;
		}if(fa.getUserData() != null && fa.getUserData().equals("player")) {
			player=true;
		}
		playerAttacked=enemy&&player;
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
			numFootContacts++;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
			numFootContacts++;
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("crystal")) {
			bodiesToRemove.add(fa.getBody());
		}
		if(fb.getUserData() != null && fb.getUserData().equals("crystal")) {
			bodiesToRemove.add(fb.getBody());
		}
		
	}
	
	// called when two fixtures no longer collide
	public void endContact(Contact c) {
		
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		if(fa == null || fb == null) return;
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
			numFootContacts--;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
			numFootContacts--;
		}
		
	}
	public boolean isPlayerAttacked() {
		return this.playerAttacked;
		
	}
	public boolean isPlayerOnGround() { return numFootContacts > 0; }
	public Array<Body> getBodiesToRemove() { return bodiesToRemove; }
	
	
	
	public void preSolve(Contact c, Manifold m) {}
	public void postSolve(Contact c, ContactImpulse ci) {}
	
}









