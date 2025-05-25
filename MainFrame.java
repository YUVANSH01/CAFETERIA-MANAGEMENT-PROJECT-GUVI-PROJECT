
import javax.swing.*;         // For JFrame, JButton, JPanel, JOptionPane
import java.awt.*;            // For GridLayout, etc.
import java.awt.event.*;      // If you're using ActionListener
class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Cafeteria System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton adminBtn = new JButton("Admin Login");
        JButton customerBtn = new JButton("Continue as Customer");
        JButton exitBtn = new JButton("Exit");

        adminBtn.addActionListener(e -> new AdminLoginFrame(this));
        customerBtn.addActionListener(e -> new CustomerPanelFrame(this));
        exitBtn.addActionListener(e -> System.exit(0));

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(new JLabel("Welcome!", SwingConstants.CENTER));
        panel.add(adminBtn);
        panel.add(customerBtn);
        panel.add(exitBtn);

        add(panel);
        setVisible(true);
    }
}
