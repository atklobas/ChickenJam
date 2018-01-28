package chickenjam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import chickenjam.main.Game;

public class GameOver implements Screen {
	private Viewport viewport;
	private Stage stage;
	
	private Game game;
	
	public GameOver(Game game) {
		this.game = game;
		viewport = new FitViewport(Game.V_WIDTH, Game.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, ((Game) game).getSpriteBatch());
		
		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		
		Table table = new Table();
		table.center();
		table.setFillParent(true);
		
		Label gameOverLabel1 = new Label("The other animals played too rough.", font);
		Label gameOverLabel2 = new Label("The farmer helped you back to the coop.", font);
		Label continueLabel = new Label("Play again?", font);
		
		table.add(gameOverLabel1).expandX();
		table.row();
		table.add(gameOverLabel2).expandX();
		table.row();
		table.add(continueLabel).expandX().padTop(10f);
		
		stage.addActor(table);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
//		if (Gdx.input.isKeyPressed(32)) {
//			Game.class.create();
//			dispose();
//		}
//		Gdx.gl.glClearColor(0, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}

}
