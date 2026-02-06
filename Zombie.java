public class Zombie extends Entity {
    private static double power = 0.5;


    // Constructor

    public Zombie(int p) {
        super(p);
    }


    // Commands

    public void attack(Entity e) {
        e.modifyLife((float) -Zombie.power);
    }
}