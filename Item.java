public abstract class Item {
    private String name;
    private int position;


    // Commands

    abstract public boolean use();


    // Queries

    public String getName() {
        return this.name;
    }
}