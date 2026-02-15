public abstract class Item {
    protected String name;
    protected int position;
    protected int nivel;

    // Constructor

    public Item(String name, int nivel) {
        this.name = name;
        this.nivel = nivel;
    }

    // Commands

    abstract public void use(Player player);

    // Queries

    public String getName() {
        return this.name;
    }

    public int getNivel() {
        return this.nivel;
    }
}