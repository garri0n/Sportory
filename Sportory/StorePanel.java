// StorePanel.java
import java.awt.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

class StorePanel extends JPanel {
    private final User user;
    private final SportsStoreGUI frame;
    private JComboBox<String> itemComboBox;
    private JTextField quantityField;
    private JLabel stockLabel;
    private JTextArea cartArea;
    private JLabel imageLabel;
    private JLabel totalLabel;
    private Map<String, Integer> cart = new HashMap<>();
    private double total = 0.0;

    public StorePanel(User user, SportsStoreGUI frame) {
        this.user = user;
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(frame.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(frame.BACKGROUND_COLOR);

        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setForeground(frame.TEXT_COLOR);

        JButton logoutBtn = frame.createSecondaryButton("Logout", 100);
        logoutBtn.addActionListener(e -> frame.showLoginPanel());

        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(logoutBtn, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBackground(frame.BACKGROUND_COLOR);

        // Left side - Shopping
        JPanel shoppingPanel = new JPanel();
        shoppingPanel.setLayout(new BoxLayout(shoppingPanel, BoxLayout.Y_AXIS));
        shoppingPanel.setBackground(frame.BACKGROUND_COLOR);
        shoppingPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JPanel formPanel = new JPanel();
        formPanel.setBackground(frame.BACKGROUND_COLOR);
        itemComboBox = new JComboBox<>(DataStore.inventory.keySet().toArray(new String[0]));
        itemComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        quantityField = frame.createStyledTextField("1", 60);
        stockLabel = new JLabel();
        stockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JButton addBtn = frame.createPrimaryButton("Add to Cart", 120);

        formPanel.add(new JLabel("Item:"));
        formPanel.add(itemComboBox);
        formPanel.add(Box.createHorizontalStrut(10));
        formPanel.add(new JLabel("Qty:"));
        formPanel.add(quantityField);
        formPanel.add(Box.createHorizontalStrut(10));
        formPanel.add(stockLabel);
        formPanel.add(Box.createHorizontalStrut(10));
        formPanel.add(addBtn);

        cartArea = new JTextArea(10, 20);
        cartArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cartArea.setEditable(false);
        cartArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(frame.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        shoppingPanel.add(formPanel);
        shoppingPanel.add(Box.createVerticalStrut(20));
        shoppingPanel.add(new JScrollPane(cartArea));

        // Right side - Image
        imageLabel = new JLabel("", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(300, 300));
        updateImage();

        centerPanel.add(shoppingPanel);
        centerPanel.add(imageLabel);
        add(centerPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(frame.BACKGROUND_COLOR);

        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JButton checkoutBtn = frame.createPrimaryButton("Checkout", 120);
        JButton historyBtn = frame.createSecondaryButton("History", 120);

        footerPanel.add(totalLabel);
        footerPanel.add(Box.createHorizontalStrut(20));
        footerPanel.add(checkoutBtn);
        footerPanel.add(Box.createHorizontalStrut(10));
        footerPanel.add(historyBtn);
        add(footerPanel, BorderLayout.SOUTH);

        // Event listeners
        itemComboBox.addActionListener(e -> updateImage());
        addBtn.addActionListener(e -> addToCart());
        checkoutBtn.addActionListener(e -> checkout());
        historyBtn.addActionListener(e -> showHistory());

        updateImage();
    }

    private void addToCart() {
        try {
            int qty = Integer.parseInt(quantityField.getText());
            if (qty <= 0) throw new NumberFormatException();

            String name = (String) itemComboBox.getSelectedItem();
            Item item = DataStore.inventory.get(name);

            int alreadyInCart = cart.getOrDefault(name, 0);
            int available = item.getQuantity() - alreadyInCart;
            if (qty > available) {
                JOptionPane.showMessageDialog(this, "Insufficient stock! Available: " + available);
                return;
            }

            cart.put(name, alreadyInCart + qty);
            updateCartArea();
            updateTotal();
            updateImage();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity.");
        }
    }

    private void updateCartArea() {
        cartArea.setText("");
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String name = entry.getKey();
            int qty = entry.getValue();
            Item item = DataStore.inventory.get(name);
            double sub = item.getPrice() * qty;
            cartArea.append(qty + " x " + name + " @ $" + item.getPrice() + " = $" + String.format("%.2f", sub) + "\n");
        }
    }

    private void updateTotal() {
        total = 0.0;
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            Item item = DataStore.inventory.get(entry.getKey());
            total += item.getPrice() * entry.getValue();
        }
        totalLabel.setText("Total: $" + String.format("%.2f", total));
    }

    private void checkout() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty.");
            return;
        }

        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String name = entry.getKey();
            int qty = entry.getValue();
            Item item = DataStore.inventory.get(name);

            if (item.getQuantity() >= qty) {
                item.setQuantity(item.getQuantity() - qty);
                user.addPurchase(LocalDateTime.now() + ": " + qty + " x " + name + " @ $" + item.getPrice() + " = $" + String.format("%.2f", item.getPrice() * qty));
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient stock for " + name);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Thanks for your purchase!\nTotal: $" + String.format("%.2f", total));
        cart.clear();
        cartArea.setText("");
        total = 0;
        totalLabel.setText("Total: $0.00");
        updateImage();
    }

    private void updateImage() {
        String name = (String) itemComboBox.getSelectedItem();
        imageLabel.setText("");
        imageLabel.setIcon(null);
        if (name != null) {
            Item item = DataStore.inventory.get(name);
            if (item != null) {
                int available = item.getQuantity() - cart.getOrDefault(name, 0);
                stockLabel.setText("(Stock: " + available + ")");
                try {
                    ImageIcon icon = new ImageIcon(item.getImagePath());
                    Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(img));
                } catch (Exception e) {
                    imageLabel.setText("Image not found");
                }
            }
        }
    }

    private void showHistory() {
        StringBuilder sb = new StringBuilder();
        for (String record : user.getPurchaseHistory()) {
            sb.append(record).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.length() > 0 ? sb.toString() : "No purchase history.");
    }
}