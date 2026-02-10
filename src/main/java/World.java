public class World {
	public static final int lenght = 10;
	private Entity[] grid;


	// Constructor

	public World() {
		this.grid = new Entity[lenght];
	}


	// Commands

	public void serEntityInPosition(Entity entity, int pos) {
		this.grid[pos - 1] = entity;
	}

	public void moveEntityToPosition(Entity e, int pos) { // TODO - Testear esto
		if (e.getPosition() != pos - 1) {
			this.grid[e.getPosition()] = null;
			this.grid[pos - 1] = e;
			e.move(pos);
		}
	}


	// Queries

	public Entity[] getGrid() {
		return this.grid;
	}

	public Entity getEntityInPosition(int pos) {
		return this.grid[pos - 1];
	}
}
