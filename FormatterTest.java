package faou;

import javax.swing.*;
import java.awt.*;

/**
 * Simple test to verify the NumericFormatter functionality
 */
public class FormatterTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Number Formatter Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLocationRelativeTo(null);
            
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Test field 1
            panel.add(new JLabel("Amount 1:"));
            JTextField field1 = new JTextField();
            NumericFormatter.attachThousandsFormatter(field1);
            panel.add(field1);
            
            // Test field 2  
            panel.add(new JLabel("Amount 2:"));
            JTextField field2 = new JTextField();
            NumericFormatter.attachThousandsFormatter(field2);
            panel.add(field2);
            
            // Parse button
            JButton parseButton = new JButton("Parse Values");
            parseButton.addActionListener(e -> {
                long val1 = NumericFormatter.parseNumeric(field1.getText());
                long val2 = NumericFormatter.parseNumeric(field2.getText());
                JOptionPane.showMessageDialog(frame, 
                    "Field 1: " + val1 + "\nField 2: " + val2 + "\nSum: " + (val1 + val2));
            });
            panel.add(parseButton);
            
            frame.add(panel);
            frame.setVisible(true);
        });
    }
}