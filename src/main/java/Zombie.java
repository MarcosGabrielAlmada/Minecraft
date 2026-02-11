public class Zombie extends Entity {
    // Constructor

    public Zombie(int position) {
        super(position, 1);
    }

    // Commands

    public void attack(Entity e) {
        e.modifyLife(-this.getDamage());
    }

    public int calculateMovement(Entity player) {
		int tmpPosition = this.position;
        if (tmpPosition < player.getPosition()) {
            tmpPosition++;
        } else {
            tmpPosition--;
        }
        return tmpPosition;
    }
}