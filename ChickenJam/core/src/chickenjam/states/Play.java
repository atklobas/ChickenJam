package chickenjam.states;

import static chickenjam.handlers.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import chickenjam.entities.Actor;
import chickenjam.entities.B2DSprite;
import chickenjam.entities.PowerUp;
import chickenjam.entities.Enemy;
import chickenjam.entities.HUD;
import chickenjam.entities.Player;
import chickenjam.entities.PushableObject;
import chickenjam.handlers.B2DVars;
import chickenjam.handlers.GameStateManager;
import chickenjam.handlers.MyContactListener;
import chickenjam.handlers.MyInput;
import chickenjam.main.Game;

public class Play extends GameState {
	
	private boolean debug = true;
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera b2dCam;
	private MyContactListener cl;
	
	private TiledMap tileMap;
	private float tileSize;
	private OrthogonalTiledMapRenderer tmr;
	
	public Player player;
	private Array<PowerUp> crystals;
	private Array<B2DSprite> objects;
	
	private HUD hud;
	
	private Music music;
	
	public Play(GameStateManager gsm) {
		
		super(gsm);
		
		// set up box2d stuff
		world = new World(new Vector2(0, -9.81f), true);
		cl = new MyContactListener();
		world.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();
		
		// create player
		createPlayer();
		
		// create tiles
		createTiles();
		
		// create crystals
		createCrystals();
		
		// set up box2d cam
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
		
		// set up hud
		hud = new HUD(player);
		
		// start musics
		music = Gdx.audio.newMusic(Gdx.files.internal("res/music/bensound-ukulele.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();
	}
	
	public void handleInput() {
		
		
		//if(cd.isPlayerOnGround()) {
			player.onGround(cl.isPlayerOnGround());
			player.attacked(cl.isPlayerAttacked());
		//}
		
		
		if(MyInput.isDown(MyInput.RIGHT)) {
			player.moveRight();
			/*System.out.println("here");
			if(cl.isPlayerOnGround()) {
				player.getBody().setLinearVelocity(1,player.getBody().getLinearVelocity().y);
				//player.getBody().applyForceToCenter(10, 0, true);
			}*/
		}else if(MyInput.isDown(MyInput.LEFT)) {
			player.moveLeft();
			
			//if(cl.isPlayerOnGround()) {
			/*	//player.getBody().setL
				player.getBody().setLinearVelocity(-1,player.getBody().getLinearVelocity().y);
				//player.getBody().applyForceToCenter(-10, 0, true);
				//player.getBody().applyForceToCenter(0, 250, true);
			}*/
		}else {
			player.stop();
		}
		
		if (MyInput.isPressed(MyInput.CROW)) {
			player.crow();
		}
		
		if(MyInput.isPressed(MyInput.UP)) {
			player.jump();
		}
		if(MyInput.isDown(MyInput.UP)) {
			player.flap();
		}
		
	}
	public void update(float dt) {
		
		// check input
		handleInput();
		if(!player.isDead()) {
			world.step(dt, 6, 2);
		}else {
			if(player.diedAt()+3000<System.currentTimeMillis()) {
				gsm.popState();
				
				gsm.pushState(GameStateManager.DEAD);
			}
		}
		
		// update box2d
		
		
		// remove crystals
		Array<Body> bodies = cl.getBodiesToRemove();
		for(int i = 0; i < bodies.size; i++) {
			Body b = bodies.get(i);
			crystals.removeValue((PowerUp) b.getUserData(), true);
			world.destroyBody(b);
			player.gainHealth();
		}
		bodies.clear();
		
		player.update(dt);
		
		for(int i = 0; i < crystals.size; i++) {
			crystals.get(i).update(dt);
		}
		for(B2DSprite o:this.objects) {
			o.update(dt);
		}
		
	}
	
	public void render() {
		
		// clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// set camera to follow player
		cam.position.set(
			player.getPosition().x * PPM,// + Game.V_WIDTH / 2,
			player.getPosition().y * PPM,//a+Game.V_HEIGHT / 2,
			0
		);
		cam.update();
		
		// draw tile map
		tmr.setView(cam);
		tmr.render();
		
		// draw player
		sb.setProjectionMatrix(cam.combined);
		player.render(sb);
		for(B2DSprite o:this.objects) {
			o.render(sb);
		}
		// draw crystals
		for(int i = 0; i < crystals.size; i++) {
			crystals.get(i).render(sb);
		}
		
		// draw hud
		sb.setProjectionMatrix(hudCam.combined);
		hud.render(sb);
		
		// draw box2d
		if(debug) {
			b2dCam.position.set(
					player.getPosition().x ,// + Game.V_WIDTH / 2,
					player.getPosition().y,//a+Game.V_HEIGHT / 2,
					0
				);
			b2dCam.update();
			b2dr.render(world, b2dCam.combined);
		}
		
	}
	
	public void dispose() {
		music.stop();
	}
	
	private void createPlayer() {
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		// create player
		bdef.position.set(100 / PPM, 200 / PPM);
		bdef.type = BodyType.DynamicBody;
		Body body = world.createBody(bdef);
		
		shape.setAsBox(13 / PPM, 13 / PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_RED | B2DVars.BIT_CRYSTAL;
		fdef.friction=0;
		body.createFixture(fdef).setUserData("player");
		
		// create foot sensor
		shape.setAsBox(13 / PPM, 2 / PPM, new Vector2(0, -13 / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_RED;
		fdef.isSensor = true;
		
		body.createFixture(fdef).setUserData("foot");
		
		// create player
		player = new Player(body);

		
		body.setUserData(player);
		
	}
	
	private void createTiles() {
		
		// load tile map
		tileMap = new TmxMapLoader().load("res/maps/test.tmx");
		tmr = new OrthogonalTiledMapRenderer(tileMap);
		
		tileSize=32;
		//tileSize = (int) tileMap.getProperties().get("tilewidth");
		
		TiledMapTileLayer layer;
		
		layer = (TiledMapTileLayer) tileMap.getLayers().get("red");
		createLayer(layer, B2DVars.BIT_RED);
		
		layer = (TiledMapTileLayer) tileMap.getLayers().get("green");
		createLayer(layer, B2DVars.BIT_GREEN);
		
		layer = (TiledMapTileLayer) tileMap.getLayers().get("blue");
		createLayer(layer, B2DVars.BIT_BLUE);
		
	}
	
	private void createLayer(TiledMapTileLayer layer, short bits) {
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		// go through all the cells in the layer
		for(int row = 0; row < layer.getHeight(); row++) {
			for(int col = 0; col < layer.getWidth(); col++) {
				
				// get cell
				Cell cell = layer.getCell(col, row);
				
				// check if cell exists
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				
				// create a body + fixture from cell
				bdef.type = BodyType.StaticBody;
				bdef.position.set(
					(col + 0.5f) * tileSize / PPM,
					(row + 0.5f) * tileSize / PPM
				);
				
				ChainShape cs = new ChainShape();
				Vector2[] v = new Vector2[5];
				v[0] = new Vector2(
					-tileSize / 2 / PPM, -tileSize / 2 / PPM);
				v[1] = new Vector2(
					-tileSize / 2 / PPM, tileSize / 2 / PPM);
				v[2] = new Vector2(
					tileSize / 2 / PPM, tileSize / 2 / PPM);
				v[3] = new Vector2(
						tileSize / 2 / PPM, -tileSize / 2 / PPM);
				v[4] = new Vector2(
						-tileSize / 2 / PPM, -tileSize / 2 / PPM);
				cs.createChain(v);
				//dfdef.friction = 100;
				fdef.shape = cs;
				fdef.filter.categoryBits = bits;
				fdef.filter.maskBits = B2DVars.BIT_PLAYER|B2DVars.BIT_RED;
				fdef.isSensor = false;
				world.createBody(bdef).createFixture(fdef);
				
				
			}
		}
	}
	
	private void createCrystals() {
		
		crystals = new Array<PowerUp>();
		objects= new Array<B2DSprite>();
		MapLayer layer = tileMap.getLayers().get("crystals");
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		//Enemies
		layer=tileMap.getLayers().get("enemies");
		for(MapObject o:layer.getObjects()) {
			float x=(float)o.getProperties().get("x")/PPM;
			float y=(float)o.getProperties().get("y")/PPM;
			String type=(String) o.getProperties().get("type");
			bdef.position.set(x, y);
			
			
			bdef.type = BodyType.DynamicBody;
			bdef.fixedRotation=true;
			bdef.position.set(
				(x ),// + tileSize / PPM/2,
				(y ) //+ tileSize / PPM/2
			);
			PolygonShape shape=new PolygonShape();
			shape.setAsBox(15 / PPM, 15 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_RED;
			fdef.filter.maskBits = B2DVars.BIT_RED | B2DVars.BIT_CRYSTAL|B2DVars.BIT_PLAYER;
			fdef.friction=0;
			fdef.density=10;
			Body bod=world.createBody(bdef);
			//bod;
			bod.createFixture(fdef).setUserData("enemy");
			fdef.shape = shape;
			this.objects.add(new Enemy(bod,player));
			
			
		}
		
		
		//Boxes
		layer=tileMap.getLayers().get("boxes");
		for(MapObject o:layer.getObjects()) {
			float x=(float)o.getProperties().get("x")/PPM;
			float y=(float)o.getProperties().get("y")/PPM;
			String type=(String) o.getProperties().get("type");
			bdef.position.set(x, y);
			
			bdef.type = BodyType.DynamicBody;
			bdef.fixedRotation=true;
			bdef.position.set(
				(x ),// + tileSize / PPM/2,
				(y ) //+ tileSize / PPM/2
			);
			PolygonShape shape=new PolygonShape();
			shape.setAsBox(15 / PPM, 15 / PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = B2DVars.BIT_RED;
			fdef.filter.maskBits = B2DVars.BIT_RED | B2DVars.BIT_CRYSTAL|B2DVars.BIT_PLAYER;
			fdef.friction=.5f;
			fdef.density=10;
			Body bod=world.createBody(bdef);
			bod.createFixture(fdef);
			fdef.shape = shape;
			this.objects.add(new PushableObject(bod));
			
			
		}
		 layer = tileMap.getLayers().get("crystals");
		 
		for(MapObject mo : layer.getObjects()) {
			
			bdef.type = BodyType.KinematicBody;
//			mo.getProperties().get
			float x = (float) mo.getProperties().get("x") / PPM;
			float y = (float) mo.getProperties().get("y") / PPM;
			bdef.position.set(x, y);
			
			
			CircleShape cshape = new CircleShape();
			cshape.setRadius(8 / PPM);
			
			fdef.shape = cshape;
			fdef.isSensor = true;
			fdef.filter.categoryBits = B2DVars.BIT_CRYSTAL;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("crystal");
			
			PowerUp c = new PowerUp(body);
			crystals.add(c);
			
			body.setUserData(c);
			
		}
		/**/
	}
	
	private void switchBlocks() {
		
		Filter filter = player.getBody().getFixtureList().first()
						.getFilterData();
		short bits = filter.maskBits;
		
		// switch to next color
		// red -> green -> blue -> red
		if((bits & B2DVars.BIT_RED) != 0) {
			bits &= ~B2DVars.BIT_RED;
			bits |= B2DVars.BIT_GREEN;
		}
		else if((bits & B2DVars.BIT_GREEN) != 0) {
			bits &= ~B2DVars.BIT_GREEN;
			bits |= B2DVars.BIT_BLUE;
		}
		else if((bits & B2DVars.BIT_BLUE) != 0) {
			bits &= ~B2DVars.BIT_BLUE;
			bits |= B2DVars.BIT_RED;
		}
		
		// set new mask bits
		filter.maskBits = bits;
		player.getBody().getFixtureList().first().setFilterData(filter);
		
		// set new mask bits for foot
		filter = player.getBody().getFixtureList().get(1).getFilterData();
		bits &= ~B2DVars.BIT_CRYSTAL;
		filter.maskBits = bits;
		player.getBody().getFixtureList().get(1).setFilterData(filter);
		
	}
	
}









