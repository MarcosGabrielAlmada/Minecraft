public class World {
	private static final int lenght = 10;
	private Entity[] content;


	// Constructor

	public World() {
		this.content = new Entity[lenght];
	}


	// Commands

	public void moveEntityToPosition(Entity e, int pos) { // TODO - Testear esto
		if (e.getPosition() != pos - 1) {
			this.content[e.getPosition()] = null;
			this.content[pos - 1] = e;
			e.move(pos);
		}
	}


	// Queries

	public Entity getEntityInPosition(int pos) {
		return this.content[pos - 1];
	}
}
