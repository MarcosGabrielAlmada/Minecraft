public class Player extends Entity {
	private String name;

	private int energy;
	private int armor;

	// TODO: Comunicacion entre Player - Inventory

	// private Inventory inventory;

	// Constructor

	public Player(String n, int p) {
		super(p);
		this.name = n;
		this.energy = 10;
		this.armor = 10;
		// this.inventory = new Inventory();
	}

	// Commands

	

	public void addEnergy(int cant) {
		this.energy += cant;
		if (this.energy > 10) {
			this.energy = 10;
			regeneration();
		}

	}

	private void regeneration() {
		modifyLife(3);
	}

	public void addArmor(int cant) {
		this.armor += cant;
		if (this.armor > 10) {
			this.armor = 10;
		}
	}

	public void useItem(Item item) {
		item.use(this); // FIXME - posible casting, ver cual use() ejecuta
	}

	// Queries

	public String getName() {
		return this.name;
	}

	public int getEnergy() {
		return this.energy;
	}

	public int getArmor() {
		return this.armor;
	}
}
