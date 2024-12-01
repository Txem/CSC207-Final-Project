package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverEffect {

    /**
     * Adds hover effect to a JButton.
     *
     * @param button       The button to apply the hover effect to.
     * @param defaultColor The default background color.
     * @param hoverColor   The color when the mouse hovers over the button.
     * @param pressedColor The color when the button is pressed.
     */
    public static void apply(JButton button, Color defaultColor, Color hoverColor, Color pressedColor) {
        button.setForeground(Color.WHITE); // Set default text color
        button.setBackground(defaultColor); // Set default background color
        button.setOpaque(true);
        button.setBorderPainted(false);

        // Add mouse listener for hover and pressed effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor); // Change to hover color
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(defaultColor); // Reset to default
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(pressedColor); // Change to pressed color
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(hoverColor); // Return to hover color
            }
        });
    }
}

