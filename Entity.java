public abstract class Entity {
	protected float life;
	protected int position;
	protected Entity target;
	

	// Constructor

	public Entity(int p) {
		this.life = 10;
		this.position = p;
	}
	

	// Commands

	public void modifyLife(float cant) {
		this.life += cant;
		if (this.life > 10) {
			this.life = 10;
		}
	}

	public void move(int p) {
		this.position = p;
	}

	public void setTarget(Entity e) {
		this.target = e;
	}


	// Queries

	public float life() {
		return this.life;
	}

	public int position() {
		return this.position;
	}

	public Entity getTarget() {
		return this.target;
	}
}