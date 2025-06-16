// Item.java
public class Item {
    private final String name;
    private final String category;
    private double price;
    private int quantity;
    private final String imagePath;

    public Item(String name, String category, double price, int quantity, String imagePath) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.imagePath = imagePath;
    }

    public Item(String name, double price, String imagePath) {
        this(name, "General", price, 100, imagePath);
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return name + " - $" + String.format("%.2f", price) + " (Stock: " + quantity + ")";
    }
}