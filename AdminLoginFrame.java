import javax.swing.*;         // For JFrame, JButton, JPanel, JOptionPane
import java.awt.*;            // For GridLayout, etc.
import java.awt.event.*;      // If you're using ActionListener
class AdminLoginFrame extends JFrame {
    public AdminLoginFrame(JFrame parent) {
        setTitle("Admin Login");
        setSize(300, 200);
        setLocationRelativeTo(parent);

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            try {
                if (Database.getInstance().verifyAdmin(username, password)) {
                    JOptionPane.showMessageDialog(this, "Login Successful!");
                    new AdminPanelFrame(this);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(new JLabel(""));
        panel.add(loginBtn);

        add(panel);
        setVisible(true);
    }
}
