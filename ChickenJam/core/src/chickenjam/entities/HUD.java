package chickenjam.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import chickenjam.handlers.B2DVars;
import chickenjam.main.Game;

public class HUD {
	
	private Player player;
	
	private TextureRegion[] blocks;
	
	public HUD(Player player) {
		
		this.player = player;
		
		Texture tex = Game.res.getTexture("hud");
		
		blocks = new TextureRegion[3];
		for(int i = 0; i < blocks.length; i++) {
			blocks[i] = new TextureRegion(tex, 32 + i * 16, 0, 16, 16);
		}
		
	}
	
	public void render(SpriteBatch sb) {
		
		sb.begin();
		for (short i = 0; i < player.getHealth() ; i += 2) {
			sb.draw(blocks[0], 20 + (i / 2 * 20), 200);
		}
		if (player.getHealth() % 2 == 1) {
			sb.draw(blocks[1], 20 + (player.getHealth() / 2 * 20), 200);
		}
		for (short i = player.getHealth(); (i+1) < player.getMaxHealth(); i += 2) {
			sb.draw(blocks[2], 20 + ((i+1) / 2 * 20), 200);
		}
		sb.end();

		
//		short bits = player.getBody().getFixtureList().first()
//						.getFilterData().maskBits;
//		
//		sb.begin();
//		if((bits & B2DVars.BIT_RED) != 0) {
//			sb.draw(blocks[0], 40, 200);
//		}
//		if((bits & B2DVars.BIT_GREEN) != 0) {
//			sb.draw(blocks[1], 40, 200);
//		}
//		if((bits & B2DVars.BIT_BLUE) != 0) {
//			sb.draw(blocks[2], 40, 200);
//		}
//		sb.end();
		
	}
	
	
}













