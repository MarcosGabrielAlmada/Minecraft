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
        this.position = 0;
    }


    // Commands

    public boolean move(int pX, int pY) {
	    this.positionX = pX;
	    this.positionY = pY;
    }

    public void subtractLife(int cant) {
        this.life -= cant;
    }


    // Queries

    public boolean attack(Player player) {
        player.addLife(-this.power);
    }
}