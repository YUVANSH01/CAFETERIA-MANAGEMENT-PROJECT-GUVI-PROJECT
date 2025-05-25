import javax.swing.*;         // For JFrame, JButton, JPanel, JOptionPane
import java.awt.*;            // For GridLayout, etc.
import java.awt.event.*;      // If you're using ActionListener
class AdminPanelFrame extends JFrame {
    public AdminPanelFrame(JFrame parent) {
        setTitle("Admin Panel");
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JButton addBtn = new JButton("Add Menu Item");
        JButton updateBtn = new JButton("Update Menu Item");
        JButton viewOrdersBtn = new JButton("View Orders");
        JButton logoutBtn = new JButton("Logout");

        addBtn.addActionListener(e -> {
            try {
                Database.getInstance().addMenuItem();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        updateBtn.addActionListener(e -> {
            try {
                Database.getInstance().updateMenuItem();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        viewOrdersBtn.addActionListener(e -> {
            try {
                Database.getInstance().viewOrders();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new MainFrame();
        });

        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(viewOrdersBtn);
        panel.add(logoutBtn);

        add(panel);
        setVisible(true);
    }
}
