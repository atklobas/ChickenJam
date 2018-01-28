package chickenjam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import chickenjam.main.Game;

public class Enemy extends Actor {
	
	Player p;
	public Enemy(Body body,Player p) {
		super(body);
		this.gndForce*=.9;
		this.maxSpeed*=.9f;
		this.p=p;
		Texture tex = Game.res.getTexture("bunny");
		TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
		
		setAnimation(sprites, 1 / 12f);
	}
	public void update(float dt) {
		super.update(dt);
		Vector2 dif=p.getBody().getPosition().sub(body.getPosition());
		float dist=dif.len();
		this.onGround=true;
		if(dist<2) {
		if(dif.x>.1) {
			this.moveRight();
			//this.body.setLinearVelocity(1,0);
		}else if(dif.x<-.1) {
			this.moveLeft();
			//this.body.setLinearVelocity(-1,0);
		}
		
		//p.getBody().getPosition().sub(body.getPosition());
	}else{
		stop();}
	}
	


}
