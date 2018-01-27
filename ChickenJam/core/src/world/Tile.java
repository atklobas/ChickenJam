package world;

public enum Tile {
	STREET(1,"Street",true),
	WALL(2,"Wall",true),
	WINDOW(3,"Window",false),
	WINDOWCIEL(4,"Window Ciel",true),
	BRICK(5,"Brick",true),
	CRATE(6,"Create",true,true,10,false),
	BARREL(7,"Barrel",true),
	GRASS(8,"Grass",true),
	HAYSTACK(9,"Haystack",true),
	DIRT(10,"Dirt",true),
	LADDER(11,"Ladder",false);
	
	private Tile(int id,String name, boolean collidable) {
		this(id,name,collidable,false,-1,false);
	}
	private Tile(int id,String name, boolean collidable, boolean movable, int health, boolean interact) {
		this.id = id;
		this.collidable = collidable;
		this.movable = movable;
		this.health = health;
		this.interact = interact;
		this.name = name;
	}
	private int id;
	
	private boolean collidable;
	private boolean movable;
	private int health;//-1 for unbreakable
	private boolean interact;
	private String name;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isCollidable() {
		return collidable;
	}
	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}
	public boolean isMovable() {
		return movable;
	}
	public void setMovable(boolean movable) {
		this.movable = movable;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public boolean isInteract() {
		return interact;
	}
	public void setInteract(boolean interact) {
		this.interact = interact;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
