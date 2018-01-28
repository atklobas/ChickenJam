package chickenjam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import chickenjam.handlers.Animation;
import chickenjam.handlers.B2DVars;
import chickenjam.main.Game;

public class Sprite {
	protected Animation animation;
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	protected int x, y;
	protected float width;
	protected float height;
	public Sprite() {
		animation = new Animation();
	}
	
	public Sprite(float x, float y, float width, float height) {
		animation = new Animation();
		Texture tex = Game.res.getTexture("bunny");
		TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
		setAnimation(sprites, 1 / 12f);
		this.x=(int)x;
		this.y=(int)y;
		this.height=height;
		this.width=width;
	}

	public void setAnimation(TextureRegion[] reg, float delay) {
		animation.setFrames(reg, delay);
		width = reg[0].getRegionWidth();
		height = reg[0].getRegionHeight();
	}
	
	public void update(float dt) {
		animation.update(dt);
	}
	
	public void render(SpriteBatch sb) {//System.out.println(x+","+y+","+width+","+height);
		sb.begin();
		sb.draw(
			animation.getFrame(),
			x,// * B2DVars.PPM - width / 2,
			y,// * B2DVars.PPM - height / 2,
			width,
			height
		);
		sb.end();
	}
	public String toString() {
		return x+","+y+","+width+","+height;
	}
}
