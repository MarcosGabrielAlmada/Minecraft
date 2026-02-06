public class Food extends Item {
    // Constructor

    public Food(int intensity) {
        super(intensity);
    }


    // Commands

    public void use(Player player) {
        player.addEnergy(this.intensity*2);
    }
}