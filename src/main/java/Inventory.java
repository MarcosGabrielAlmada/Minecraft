public class Inventory {
    private static final int size = 3;
    private final Item[] content;
    private Item selected;

    // Constructor

    public Inventory() {
        this.content = new Item[size];
        this.selected = this.content[0];
    }

    // Commands

    public void setSelected(int pos) {
        this.selected = this.content[pos - 1];
    }

    public boolean addItemInPosition(Item item, int pos) {
        boolean res = false;
        if (this.content[pos - 1] == null)
            this.content[pos - 1] = item;
        res = true;
        return res;
    }

    public void moveItem(int item1, int item2) {
        Item tmp = this.content[item1];
        this.content[item1] = this.content[item2];
        this.content[item2] = tmp;
    }

    // Queries

    public int getLength() {
        return this.content.length;
    }

    public Item getItemInPosition(int pos) {
        return this.content[pos - 1];
    }

    public Item getSelected() {
        return this.selected;
    }
}