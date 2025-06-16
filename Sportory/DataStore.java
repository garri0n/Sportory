// DataStore.java
import java.util.*;

class DataStore {
    public static Map<String, User> users = new HashMap<>();
    public static Map<String, Item> inventory = new LinkedHashMap<>();
    private static int nextProductId = 1;

    static {
        // Add sample users
        users.put("admin", new User("admin", "admin123", true));
        users.put("user1", new User("user1", "pass1", false));

        // Add sample items with enhanced properties
        inventory.put("Football", new Item("Football", "Sports Equipment", 25.0, 50, "images/football.avif"));
        inventory.put("Basketball", new Item("Basketball", "Sports Equipment", 30.0, 40, "images/basketball.avif"));
        inventory.put("Nike Elite Socks", new Item("Nike Elite Socks", "Apparel", 10.0, 100, "images/basketball.avif"));
        inventory.put("AE1 Low", new Item("AE1 Low", "Footwear", 150.0, 25, "images/basketball.avif"));
        inventory.put("Volleyball", new Item("Volleyball", "Sports Equipment", 20.0, 35, "images/basketball.avif"));
        inventory.put("Boxing Gloves", new Item("Boxing Gloves", "Sports Equipment", 70.0, 20, "images/basketball.avif"));
        inventory.put("Tennis Racket", new Item("Tennis Racket", "Sports Equipment", 50.0, 30, "images/basketball.avif"));
    }

    public static int getNextProductId() {
        return nextProductId++;
    }
}
