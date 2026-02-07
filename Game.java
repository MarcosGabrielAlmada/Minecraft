public class Game {
	private Entity[] entities;
	private Entity turn;
	private World world;


	// Constructor

	public Game() {
		this.entities = new Entity[2];
		this.entities[0] = new Player("Marcos", 0);
		this.entities[1] = new Zombie(10);

		this.turn = this.entities[0];
		this.world = new World();
	}


	// Commands

	public void toggleTurn() {
		if (this.turn == this.entities[0]) {
			this.turn = this.entities[1];
		} else {
			this.turn = this.entities[0];
		}
	}


	// Queries

	public Entity[] getEntities() {
		return this.entities;
	}

	public Entity getTurn() {
		return this.turn;
	}

	public World getWorld() {
		return this.world;
	}
}
