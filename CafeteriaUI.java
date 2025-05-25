import javax.swing.*;         // For JFrame, JButton, JPanel, JOptionPane
import java.awt.*;            // For GridLayout, etc.
import java.awt.event.*;      // If you're using ActionListener
public class CafeteriaUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
