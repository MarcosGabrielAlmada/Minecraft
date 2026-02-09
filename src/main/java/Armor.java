public class Armor extends Item {
    // Constructor

    public Armor(int nivel) {
        super(nivel);
    }


    // Commands

    public void use(Player player) {
        player.addArmor(this.nivel*2);
    }
}