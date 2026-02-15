public class Food extends Item {
    // Constructor

    public Food(int nivel) {
        super("Food", nivel);
    }


    // Commands

    public void use(Player player) {
        player.modifyLife(this.nivel);
    }
}