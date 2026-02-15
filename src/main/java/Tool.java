public class Tool extends Item {
    // Constructor

    public Tool(int nivel) {
        super("Tool", nivel);
    }

    // Commands

    public void use(Player player) {
        if (player.getTarget() != null) {
            player.getTarget().modifyLife(-player.getDamage() * 2);
        }
    }
}