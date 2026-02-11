public class Zombie extends Entity {
    private static double power = 1;

    // Constructor

    public Zombie(int p) {
        super(p);
    }

    // Commands

    public void attack(Entity e) {
        e.modifyLife((float) -Zombie.power);
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