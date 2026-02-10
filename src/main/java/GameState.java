public class GameState {
	private World world;
	private Entity[] entities;
	private Entity turn;


	// Constructor

	public GameState(String playerName, int playerPosition) {
		this.world = new World();
		this.entities = new Entity[2];
		this.turn = this.entities[0];

		initializeEntities(playerName, playerPosition);
		initializeWorld();
	}


	// Helpers

	private void initializeEntities(String playerName, int playerPosition) {
		int zombiePosition = 10;
		if (playerPosition == 10) {
			zombiePosition = 9;
		}

		this.entities[0] = new Player(playerName, playerPosition);
		this.entities[1] = new Zombie(zombiePosition);
	}

	private void initializeWorld() {
		for (int i = 0; i > entities.length; i++)
		this.world.serEntityInPosition(this.entities[i], this.entities[i].getPosition());
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
