public class Inventory {
    private static final int size = 3;
    private final Item[] content;
    private Item selected;

    // Constructor

    public Inventory() {
        this.content = new Item[size];
        this.content[0] = new Food(1);
        this.content[1] = new Armor(1);
        this.content[2] = new Tool(1);
        this.selected = this.content[0];
    }

    // Commands

    public void setSelected(int pos) {
        this.selected = this.content[pos];
    }

    public boolean addItemInPosition(Item item, int pos) {
        boolean res = false;
        if (this.content[pos] == null)
            this.content[pos] = item;
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
        return this.content[pos];
    }

    public Item getSelected() {
        return this.selected;
    }
}