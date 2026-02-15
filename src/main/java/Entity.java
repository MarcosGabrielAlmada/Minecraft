public abstract class Entity {
	protected float life;
	protected int position;
	protected float damage;
	private int armor;
	protected Entity target;

	// Constructor

	public Entity(int position, float damage) {
		this.life = 10;
		this.position = position;
		this.damage = damage;
		this.armor = 0;
		this.target = null;
	}

	// Commands

	public void modifyLife(float cant) {
		this.life += cant;

		if (cant < 0) {
			if (cant + armor <= 0) {
				this.life += armor;
			} else {
				this.life -= cant;
			}
		}

		if (this.life > 10) {
			this.life = 10;
		} else if (this.life < 0) {
			this.life = 0;
		}
	}

	public void move(int p) {
		this.position = p;
	}

	public void modifyDamage(float cant) {
		this.damage += cant;
		if (this.damage < 1) {
			this.damage = 0;
		}
	}

	public void modifyArmor(int cant) {
		this.armor += cant;
		if (this.armor > 10) {
			this.armor = 10;
		}
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

	public float getDamage() {
		return this.damage;
	}

	public int getArmor() {
		return this.armor;
	}

	public Entity getTarget() {
		return this.target;
	}
}