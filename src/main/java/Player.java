public class Player extends Entity {
	private String name;
	private int energy;
	private int armor;
	private Inventory inventory;

	
	// Constructor

	public Player(String name, int position) {
		super(position, 0.5f);
		this.name = name;
		this.energy = 10;
		this.armor = 0;
		this.inventory = new Inventory();
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

	public void useItem() {
		this.inventory.getSelected().use(this);
	}

	public int calculateMovement(String direction) {
		int position = -1;
		int tmpPosition;

		switch (direction) {
			case "LEFT":
				tmpPosition = this.getPosition()-2;
				if (tmpPosition < 1) {
					tmpPosition = 1;
				}
				position = tmpPosition;

				break;

			case "RIGHT":
				tmpPosition = this.getPosition()+2;
				if (tmpPosition >= World.WORLD_SIZE) {
					tmpPosition = World.WORLD_SIZE;
				}
				position = tmpPosition;
				
				break;

			default:
				break;
		}

		return position;
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

	public Inventory getInventory() {
		return this.inventory;
	}
}
