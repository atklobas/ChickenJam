package chickenjam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import chickenjam.main.Game;

public class PushableObject extends B2DSprite{

	public PushableObject(Body body) {
		super(body);
		
		Texture tex = Game.res.getTexture("box");
		TextureRegion[] sprites = TextureRegion.split(tex, 32, 31)[0];
		this.width=300;
		
		setAnimation(sprites, 1);
	}

}
