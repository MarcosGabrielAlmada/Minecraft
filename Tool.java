public class Tool extends Item {
    // Constructor

    public Tool(int nivel) {
        super(nivel);
    }


    // Commands

    public void use(Player player) {
        player.getTarget().modifyLife(nivel*2);
    }
}