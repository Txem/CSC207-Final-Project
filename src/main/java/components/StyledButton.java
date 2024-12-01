package components;

import view.HoverEffect;

import javax.swing.*;
import java.awt.*;

/**
 * A reusable styled button class with built-in hover effect.
 */
public class StyledButton extends JButton {

    public StyledButton(String text) {
        super(text);

        // Default colors
        Color defaultColor = new Color(0, 123, 255); // Blue background
        Color hoverColor = new Color(30, 144, 255); // Lighter blue on hover
        Color pressedColor = new Color(0, 102, 204); // Darker blue when pressed

        // Set default button styles
        setFont(new Font("Arial", Font.BOLD, 20)); // Font size and style
        setBackground(defaultColor); // Default background color
        setForeground(Color.WHITE); // White text
        setFocusPainted(false); // Remove focus painting
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2), // White border
                BorderFactory.createEmptyBorder(10, 30, 10, 30) // Padding
        ));
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        setPreferredSize(new Dimension(150, 50)); // Default button size

        // Apply hover effect
        HoverEffect.apply(this, defaultColor, hoverColor, pressedColor);
    }
}