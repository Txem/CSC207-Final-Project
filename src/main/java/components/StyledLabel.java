package components;

import javax.swing.*;
import java.awt.*;

public class StyledLabel extends JLabel {
    public StyledLabel(String text) {
        super(text);

        setFont(new Font("Arial", Font.BOLD, 16));

        setForeground(Color.DARK_GRAY);

        setHorizontalAlignment(SwingConstants.LEFT);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
}

