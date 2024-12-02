package view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;

/**
 * A panel containing a label and a text field.
 */
class LabelTextPanel extends JPanel {
    LabelTextPanel(JLabel label, JTextField textField) {
        JPanel panel = new JPanel();
        panel.add(label);
        panel.setBackground(new Color(173, 216, 230));
        JPanel panel2 = new JPanel();
        panel.add(textField);

        this.add(panel);

        this.setOpaque(false);
    }
}
