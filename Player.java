public class Player {
	private String name;
	private int life;
	private int energy;
	private int armor;
	private Inventory inventory;
	private int positionX;
	private int positionY;
}

// Constructor

public Player(String n, int pX, int pY) {
	this.name = n;
	this.life = 10;
	this.energy = 10;
	this.armor = 10;
	this.inventory = new Inventory();
	this.positionX = pX;
	this.positionY = pY;
}

// Commands

public boolean addLife(int cant) {
	this.life += cant;
	if (this.life > 10) {
		this.life = 10;
	}
}

public boolean addEnergy(int cant) {
	this.energy += cant;
	if (this.energy > 10) {
		this.energy = 10;
	}
}

public void addArmor(int cant) {
	this.armor += cant;
	if (this.armor > 10) {
		this.armor = 10;
	}
}

public void move(int pX, int pY) {
	this.positionX = pX;
	this.positionY = pY;
}

// Queries

public String getName() {
	return this.name;
}

public int getLife() {
	return this.life;
}

public int getEnergy() {
	return this.energy;
}

public int getArmor() {
	return this.armor;
}

public boolean useItem() {
	return false;
}