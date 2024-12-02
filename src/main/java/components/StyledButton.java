package components;

import view.HoverEffect;

import javax.swing.*;
import java.awt.*;

/**
 * A reusable styled button class with built-in hover effect and auto-resizing font.
 */
public class StyledButton extends JButton {

    public StyledButton(String text) {
        super(text);

        // Default colors
        Color defaultColor = new Color(0, 123, 255); // Blue background
        Color hoverColor = new Color(30, 144, 255); // Lighter blue on hover
        Color pressedColor = new Color(0, 102, 204); // Darker blue when pressed

        // Set default button styles
        setFont(new Font("Arial", Font.BOLD, 16)); // Default font size (smaller)
        setBackground(defaultColor); // Default background color
        setForeground(Color.WHITE); // White text
        setFocusPainted(false); // Remove focus painting
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2), // White border
                BorderFactory.createEmptyBorder(10, 20, 10, 20) // Padding
        ));
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        setPreferredSize(new Dimension(150, 50)); // Default button size

        // Apply hover effect
        HoverEffect.apply(this, defaultColor, hoverColor, pressedColor);

        // Enable auto-resizing font
        enableAutoResizingFont();
    }

    /**
     * Adjust the font size to ensure the text fits within the button bounds.
     */
    private void enableAutoResizingFont() {
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                adjustFontSize();
            }
        });
    }

    /**
     * Dynamically adjusts the font size to fit the button's text.
     */
    private void adjustFontSize() {
        String text = getText();
        if (text == null || text.isEmpty()) return;

        Font originalFont = getFont();
        FontMetrics metrics = getFontMetrics(originalFont);

        int buttonWidth = getWidth() - getInsets().left - getInsets().right - 10; // Account for padding
        int buttonHeight = getHeight() - getInsets().top - getInsets().bottom - 10;

        int fontSize = originalFont.getSize();

        while (fontSize > 8 && (metrics.stringWidth(text) > buttonWidth || metrics.getHeight() > buttonHeight)) {
            fontSize--;
            setFont(new Font(originalFont.getName(), originalFont.getStyle(), fontSize));
            metrics = getFontMetrics(getFont());
        }
    }
}
