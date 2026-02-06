public abstract class Item {
    private String name;
    private int position;
    private int nivel;


    // Constructor

    public Item(int nivel) {
        this.nivel = nivel;
    }

    // Commands

    abstract public void use(Player player);


    // Queries

    public String getName() {
        return this.name;
    }

    public int getnivel() {
        return this.nivel;
    }
}