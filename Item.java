
/**
 * The Item class represents an item that can be found in a room.
 * Items have a description and a weight.
 * @author Omar Malik
 * @version 2023.10.20
 */
public class Item {
    private String description;
    private double weight;

    /**
     * Create a new Item with the specified description and weight.
     *
     * @param description The description of the item.
     * @param weight The weight of the item in kilograms.
     */
    public Item(String description, double weight) {
        this.description = description;
        this.weight = weight;
    }

    /**
     * Get the description of the item.
     *
     * @return The description of the item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the weight of the item.
     *
     * @return The weight of the item in kilograms.
     */
    public double getWeight() {
        return weight;
    }
}

