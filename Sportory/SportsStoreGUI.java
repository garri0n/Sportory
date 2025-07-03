import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class SportsStoreGUI extends JFrame {
    public static final Color BACKGROUND_COLOR = new Color(248, 250, 252);
    public static final Color PRIMARY_COLOR = new Color(59, 130, 246);
    public static final Color TEXT_COLOR = new Color(31, 41, 55);
    public static final Color LIGHT_GRAY = new Color(229, 231, 235);
    public static final Color PLACEHOLDER_COLOR = new Color(156, 163, 175);
    public static final Color WHITE = Color.WHITE;
    public static final Color LOGO_GREEN = new Color(72, 187, 120); 

    public SportsStoreGUI() {
        setTitle("Sportory");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND_COLOR);

        setIconImage(createSportoryIcon());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        showLoginPanel();
    }

    private BufferedImage createSportoryIcon() {
        int size = 64;
        BufferedImage icon = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = icon.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(LOGO_GREEN);
        g2d.fillOval(0, 0, size, size);

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2.5f));
        
        int centerX = size / 2;
        int centerY = size / 2;

        int storeWidth = 28;
        int storeHeight = 18;
        int storeX = centerX - storeWidth/2;
        int storeY = centerY - 3;

        g2d.drawRect(storeX, storeY, storeWidth, storeHeight);

        g2d.drawLine(storeX - 4, storeY, storeX + storeWidth + 4, storeY);
        g2d.drawLine(storeX - 4, storeY, storeX - 4, storeY - 6);
        g2d.drawLine(storeX + storeWidth + 4, storeY, storeX + storeWidth + 4, storeY - 6);
        g2d.drawLine(storeX - 4, storeY - 6, storeX + storeWidth + 4, storeY - 6);

        g2d.drawLine(centerX, storeY, centerX, storeY + storeHeight);

        for (int i = 0; i < 3; i++) {
            int stripeY = storeY - 5 + (i * 2);
            g2d.drawLine(storeX - 3, stripeY, storeX + storeWidth + 3, stripeY);
        }
        
        g2d.dispose();
        return icon;
    }

    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                int centerX = width / 2;
                int centerY = height / 2;

                int circleSize = 80;
                g2d.setColor(LOGO_GREEN);
                g2d.fillOval(centerX - circleSize/2, centerY - circleSize/2, circleSize, circleSize);

                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(3));

                int storeWidth = 40;
                int storeHeight = 25;
                int storeX = centerX - storeWidth/2;
                int storeY = centerY - 5;
                g2d.drawRect(storeX, storeY, storeWidth, storeHeight);

                g2d.drawLine(storeX - 5, storeY, storeX + storeWidth + 5, storeY);
                g2d.drawLine(storeX - 5, storeY, storeX - 5, storeY - 8);
                g2d.drawLine(storeX + storeWidth + 5, storeY, storeX + storeWidth + 5, storeY - 8);
                g2d.drawLine(storeX - 5, storeY - 8, storeX + storeWidth + 5, storeY - 8);

                g2d.drawLine(centerX, storeY, centerX, storeY + storeHeight);

                for (int i = 0; i < 4; i++) {
                    int stripeY = storeY - 7 + (i * 2);
                    g2d.drawLine(storeX - 4, stripeY, storeX + storeWidth + 4, stripeY);
                }

                g2d.dispose();
            }
        };

        logoPanel.setPreferredSize(new Dimension(120, 100));
        logoPanel.setOpaque(false);
        return logoPanel;
    }

    private JLabel createSportoryLabel() {
        JLabel label = new JLabel("Sportory", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 32));
        label.setForeground(LOGO_GREEN);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

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
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
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

    public JButton createPrimaryButton(String text, int width) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(width, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(37, 99, 235));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    public JButton createSecondaryButton(String text, int width) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(WHITE);
        button.setForeground(PRIMARY_COLOR);
        button.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(width, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private JLabel createStyledLabel(String text, int fontSize, boolean isBold) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", isBold ? Font.BOLD : Font.PLAIN, fontSize));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JLabel createLinkLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(PRIMARY_COLOR);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    public void showLoginPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(60, 50, 80, 50));

        JPanel logoPanel = createLogoPanel();
        logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel sportoryLabel = createSportoryLabel();

        JLabel welcomeLabel = createStyledLabel("Welcome to Sportory", 28, true);
        JLabel subtitleLabel = createStyledLabel("Enter your username and password below to login.", 16, false);

        JTextField usernameField = createStyledTextField("Username");
        JPasswordField passwordField = createStyledPasswordField("Password");

        JButton loginButton = createPrimaryButton("LOGIN", 200);

        JLabel noAccountLabel = createStyledLabel("Don't have an account?", 14, false);
        JLabel registerLink = createLinkLabel("Sign Up");

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(logoPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(sportoryLabel);
        mainPanel.add(Box.createVerticalStrut(20));
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
                DataStore.logUserLogin(username);
                
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(60, 50, 80, 50));

        JPanel logoPanel = createLogoPanel();
        logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel sportoryLabel = createSportoryLabel();

        JLabel welcomeLabel = createStyledLabel("Create Your Account", 28, true);
        JLabel subtitleLabel = createStyledLabel("Join Sportory and start shopping today.", 16, false);

        JTextField newUserField = createStyledTextField("Choose Username");
        JPasswordField newPassField = createStyledPasswordField("Choose Password");

        JButton createButton = createPrimaryButton("CREATE ACCOUNT", 200);

        JLabel hasAccountLabel = createStyledLabel("Already have an account?", 14, false);
        JLabel loginLink = createLinkLabel("Back to Login");

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(logoPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(sportoryLabel);
        mainPanel.add(Box.createVerticalStrut(20));
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
                // Add user to the data store
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
