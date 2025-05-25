import javax.swing.*;         // For JFrame, JButton, JPanel, JOptionPane
import java.awt.*;            // For GridLayout, etc.
import java.awt.event.*;      // If you're using ActionListener
class CustomerPanelFrame extends JFrame {
    public CustomerPanelFrame(JFrame parent) {
        setTitle("Customer Panel");
        setSize(400, 200);
        setLocationRelativeTo(parent);

        JButton placeOrderBtn = new JButton("View Stores & Place Order");
        JButton backBtn = new JButton("Back");

        placeOrderBtn.addActionListener(e -> {
            try {
                Database.getInstance().placeOrder();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new MainFrame();
        });

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(placeOrderBtn);
        panel.add(backBtn);

        add(panel);
        setVisible(true);
    }
}
