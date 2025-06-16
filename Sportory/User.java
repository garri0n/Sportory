// User.java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class User {
    private final String username;
    private final String password;
    private final boolean isAdmin;
    private final List<String> purchaseHistory;

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.purchaseHistory = Collections.synchronizedList(new ArrayList<>());
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void addPurchase(String record) {
        if (record != null && !record.trim().isEmpty()) {
            purchaseHistory.add(record);
        }
    }
    
    public List<String> getPurchaseHistory() {
        return new ArrayList<>(purchaseHistory); // Return a copy to avoid concurrent modification
    }
    
    public boolean hasPurchases() {
        return !purchaseHistory.isEmpty();
    }
}
