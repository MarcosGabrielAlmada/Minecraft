public class Zombie {
    private int life;
    private double power;
    private int positionX;
    private int positionY;


    // Constructor

    public Zombie() {
        this.life = 10;
        this.power = 0.5;
        this.positionX = 0;
        this.positionY = 0;
    }


    // Commands

    public void move(int pX, int pY) {
	    this.positionX = pX;
	    this.positionY = pY;
    }

    public void subtractLife(int cant) {
        this.life -= cant;
    }


    // Queries

    public void attack(Player player) {
        player.addLife(-this.power);
    }
}