// SportsStoreGUI.java
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;

public class SportsStoreGUI extends JFrame {
    // Make these public so other panels can use them
    public static final Color BACKGROUND_COLOR = new Color(248, 250, 252);
    public static final Color PRIMARY_COLOR = new Color(59, 130, 246);
    public static final Color TEXT_COLOR = new Color(31, 41, 55);
    public static final Color LIGHT_GRAY = new Color(229, 231, 235);
    public static final Color PLACEHOLDER_COLOR = new Color(156, 163, 175);
    public static final Color WHITE = Color.WHITE;

    public SportsStoreGUI() {
        setTitle("Sportory");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Ignore and use default
        }

        showLoginPanel();
    }

    // Public so panels can use
    public JTextField createStyledTextField(String placeholder, int width) {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(width, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        field.setBackground(WHITE);
        field.setForeground(TEXT_COLOR);
        addPlaceholder(field, placeholder);
        return field;
    }

    public JTextField createStyledTextField(String placeholder) {
        return createStyledTextField(placeholder, 200);
    }

    public JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(200, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        field.setBackground(WHITE);
        field.setForeground(TEXT_COLOR);
        addPlaceholder(field, placeholder);
        return field;
    }

    private void addPlaceholder(JTextField field, String placeholderText) {
        field.setText(placeholderText);
        field.setForeground(PLACEHOLDER_COLOR);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholderText)) {
                    field.setText("");
                    field.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholderText);
                    field.setForeground(PLACEHOLDER_COLOR);
                }
            }
        });
    }

    // Public so panels can use
    public JButton createPrimaryButton(String text, int width) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(null);
        button.setForeground(Color.black);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        // Remove setPreferredSize or use only height
        button.setMaximumSize(new Dimension(button.getPreferredSize().width, 300)); // Prevent stretching
        return button;
    }

    public JButton createSecondaryButton(String text, int width) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(255,0,0,128));
        button.setForeground(Color.black);
        button.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        button.setFocusPainted(false);
        // Remove setPreferredSize or use only height
        button.setMaximumSize(new Dimension(button.getPreferredSize().width, 50)); // Prevent stretching
        return button;
    }

    private JLabel createStyledLabel(String text, int fontSize, boolean isBold) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", isBold ? Font.BOLD : Font.PLAIN, fontSize));
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private JLabel createLinkLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(PRIMARY_COLOR);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return label;
    }

    public void showLoginPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(80, 50, 80, 50));

        JLabel welcomeLabel = createStyledLabel("Welcome to Sportory", 28, true);
        JLabel subtitleLabel = createStyledLabel("Enter your username and password below to login.", 16, false);

        JTextField usernameField = createStyledTextField("Username");
        JPasswordField passwordField = createStyledPasswordField("Password");

        JButton loginButton = createPrimaryButton("LOGIN", 500);

        JLabel noAccountLabel = createStyledLabel("Don't have an account?", 14, false);
        JLabel registerLink = createLinkLabel("Sign Up");

        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(usernameField);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(loginButton);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(noAccountLabel);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(registerLink);

        setContentPane(mainPanel);
        revalidate();
        repaint();

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.equals("Username") || password.equals("Password")) {
                JOptionPane.showMessageDialog(this, "Please enter your credentials.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = DataStore.users.get(username);
            if (user != null && user.getPassword().equals(password)) {
                if (user.isAdmin())
                    showAdminPanel();
                else
                    showStorePanel(user);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        registerLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showRegisterPanel();
            }
        });
    }

    public void showRegisterPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(80, 50, 80, 50));

        JLabel welcomeLabel = createStyledLabel("Create Your Account", 28, true);
        JLabel subtitleLabel = createStyledLabel("Join Sportory and start shopping today.", 16, false);

        JTextField newUserField = createStyledTextField("Choose Username");
        JPasswordField newPassField = createStyledPasswordField("Choose Password");

        JButton createButton = createPrimaryButton("CREATE ACCOUNT", 200);

        JLabel hasAccountLabel = createStyledLabel("Already have an account?", 14, false);
        JLabel loginLink = createLinkLabel("Back to Login");

        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(newUserField);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(newPassField);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(createButton);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(hasAccountLabel);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(loginLink);

        setContentPane(mainPanel);
        revalidate();
        repaint();

        createButton.addActionListener(e -> {
            String username = newUserField.getText().trim();
            String password = new String(newPassField.getPassword());

            if (username.equals("Choose Username") || password.equals("Choose Password")) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (username.equalsIgnoreCase("admin")) {
                JOptionPane.showMessageDialog(this, "Cannot register as admin.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if (DataStore.users.containsKey(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                User newUser = new User(username, password, false);
                DataStore.users.put(username, newUser);
                JOptionPane.showMessageDialog(this, "Account created successfully! You can now log in.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                showLoginPanel();
            }
        });

        loginLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showLoginPanel();
            }
        });
    }

    public void showStorePanel(User user) {
        setContentPane(new StorePanel(user, this));
        revalidate();
        repaint();
    }

    public void showAdminPanel() {
        setContentPane(new AdminPanel(this));
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SportsStoreGUI().setVisible(true));
    }
}