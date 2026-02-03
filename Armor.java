public class Food {
    // Constructor

    public Food(int intensity) {
        super(intensity);
    }


    // Commands

    public void use(Player player) {
        player.addEnergy(this.intensity*2);
    }


    // Queries

    public int getIntensity() {
        return this.intensity;
    }
}