public class Food extends Item {
    // Constructor

    public Food(int nivel) {
        super(nivel);
    }


    // Commands

    public void use(Player player) {
        player.addEnergy(this.nivel*2);
    }
}