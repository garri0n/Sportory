// AdminPanel.java
import java.awt.*;
import javax.swing.*;

class AdminPanel extends JPanel {
    private final SportsStoreGUI frame;
    private DefaultListModel<String> itemListModel;
    private JList<String> itemList;
    private JTextField nameField, categoryField, priceField, quantityField, imageField;
    private JTextArea inventoryArea;

    public AdminPanel(SportsStoreGUI frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(frame.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(frame.BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(frame.TEXT_COLOR);

        JButton logoutBtn = frame.createSecondaryButton("Logout", 100);
        logoutBtn.addActionListener(e -> frame.showLoginPanel());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(logoutBtn, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Add Items Tab
        JPanel addItemPanel = new JPanel(new BorderLayout(10, 10));
        addItemPanel.setBackground(frame.BACKGROUND_COLOR);
        addItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        itemListModel = new DefaultListModel<>();
        refreshItemList();
        itemList = new JList<>(itemListModel);
        itemList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane listScroll = new JScrollPane(itemList);
        listScroll.setPreferredSize(new Dimension(200, 400));
        addItemPanel.add(listScroll, BorderLayout.WEST);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBackground(frame.BACKGROUND_COLOR);

        // Assign to class fields (not local variables!)
        nameField = frame.createStyledTextField("Name", 200);
        categoryField = frame.createStyledTextField("Category", 200);
        priceField = frame.createStyledTextField("Price", 200);
        quantityField = frame.createStyledTextField("Quantity", 200);
        imageField = frame.createStyledTextField("Image Path", 200);

        JButton addBtn = frame.createPrimaryButton("Add Item", 120);
        JButton deleteBtn = frame.createSecondaryButton("Delete Item", 120);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryField);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);
        formPanel.add(new JLabel("Image Path:"));
        formPanel.add(imageField);
        formPanel.add(addBtn);
        formPanel.add(deleteBtn);

        addItemPanel.add(formPanel, BorderLayout.CENTER);
        tabbedPane.addTab("Manage Items", addItemPanel);

        // Inventory Tab
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.setBackground(frame.BACKGROUND_COLOR);
        inventoryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inventoryArea = new JTextArea(); // Assign to class field
        inventoryArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inventoryArea.setEditable(false);

        JScrollPane inventoryScroll = new JScrollPane(inventoryArea);
        inventoryPanel.add(inventoryScroll, BorderLayout.CENTER);

        JPanel inventoryControls = new JPanel();
        inventoryControls.setBackground(frame.BACKGROUND_COLOR);

        JButton viewBtn = frame.createPrimaryButton("View Inventory", 150);
        JButton stockInBtn = frame.createSecondaryButton("Stock In", 100);
        JButton stockOutBtn = frame.createSecondaryButton("Stock Out", 100);

        inventoryControls.add(viewBtn);
        inventoryControls.add(stockInBtn);
        inventoryControls.add(stockOutBtn);
        inventoryPanel.add(inventoryControls, BorderLayout.SOUTH);

        tabbedPane.addTab("Inventory", inventoryPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // --- ACTION LISTENERS ---
        addBtn.addActionListener(e -> addItem());
        deleteBtn.addActionListener(e -> deleteItem());
        viewBtn.addActionListener(e -> displayInventory());
        stockInBtn.addActionListener(e -> stockTransaction("IN"));
        stockOutBtn.addActionListener(e -> stockTransaction("OUT"));
    }

    private void addItem() {
        try {
            String name = nameField.getText().trim();
            String category = categoryField.getText().trim();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            String imagePath = imageField.getText().trim();

            if (name.isEmpty() || category.isEmpty() || imagePath.isEmpty()) {
                throw new IllegalArgumentException("All fields are required.");
            }

            if (price < 0 || quantity < 0) {
                throw new IllegalArgumentException("Price and quantity must be non-negative.");
            }

            if (DataStore.inventory.containsKey(name)) {
                JOptionPane.showMessageDialog(this, "Item with this name already exists.");
                return;
            }

            Item newItem = new Item(name, category, price, quantity, imagePath);
            DataStore.inventory.put(name, newItem);
            refreshItemList();
            clearFields();
            displayInventory();
            JOptionPane.showMessageDialog(this, "Item added successfully!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price or quantity format.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void deleteItem() {
        String selected = itemList.getSelectedValue();
        if (selected != null) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete " + selected + "?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DataStore.inventory.remove(selected);
                refreshItemList();
                itemList.clearSelection();
                displayInventory();
                JOptionPane.showMessageDialog(this, "Item deleted successfully!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.");
        }
    }

    private void stockTransaction(String type) {
        String itemName = JOptionPane.showInputDialog(this, "Enter item name:");
        if (itemName == null || itemName.trim().isEmpty()) return;

        Item item = DataStore.inventory.get(itemName.trim());
        if (item == null) {
            JOptionPane.showMessageDialog(this, "Item not found!");
            return;
        }

        String qtyStr = JOptionPane.showInputDialog(this, 
            "Enter quantity to " + (type.equals("IN") ? "add" : "remove") + ":");
        if (qtyStr == null || qtyStr.trim().isEmpty()) return;

        try {
            int quantity = Integer.parseInt(qtyStr.trim());
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be positive.");
                return;
            }

            if (type.equals("IN")) {
                item.setQuantity(item.getQuantity() + quantity);
                JOptionPane.showMessageDialog(this, "Stock added successfully!");
            } else {
                if (item.getQuantity() >= quantity) {
                    item.setQuantity(item.getQuantity() - quantity);
                    JOptionPane.showMessageDialog(this, "Stock removed successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient stock! Available: " + item.getQuantity());
                    return;
                }
            }

            displayInventory();
            refreshItemList();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity format.");
        }
    }

    private void displayInventory() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-15s %-8s %-8s\n", "Item Name", "Category", "Price", "Stock"));
        sb.append("=".repeat(55)).append("\n");

        for (Item item : DataStore.inventory.values()) {
            sb.append(String.format("%-20s %-15s $%-7.2f %-8d\n", 
                item.getName(), item.getCategory(), item.getPrice(), item.getQuantity()));
        }

        inventoryArea.setText(sb.toString());
    }

    private void refreshItemList() {
        itemListModel.clear();
        for (String key : DataStore.inventory.keySet()) {
            itemListModel.addElement(key);
        }
    }

    private void clearFields() {
        nameField.setText("");
        categoryField.setText("");
        priceField.setText("");
        quantityField.setText("");
        imageField.setText("");
    }
}

