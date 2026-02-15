public class Armor extends Item {
    // Constructor

    public Armor(int nivel) {
        super("Armor", nivel);
    }


    // Commands

    public void use(Player player) {
        player.addArmor(this.nivel*2);
    }
}