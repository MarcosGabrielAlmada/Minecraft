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
		} else if (this.life > 10) {
			this.life = 0;
		}
	}

	public void move(int p) {
		this.position = p;
	}

	public void setTarget(Entity e) {
		this.target = e;
	}


	// Queries

	public float getLife() {
		return this.life;
	}

	public int getPosition() {
		return this.position;
	}

	public Entity getTarget() {
		return this.target;
	}
}