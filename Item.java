public abstract class Item {
    private String name;
    private int position;
    private int intensity;


    // Constructor

    public Item(int intensity) {
        this.intensity = intensity;
    }

    // Commands

    abstract public void use();


    // Queries

    public String getName() {
        return this.name;
    }
}