package view;

import entity.Ingredient;
import entity.User;
import interface_adapter.add_recipe.AddRecipeController;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AddRecipeView extends JFrame {
    private final AddRecipeController addRecipeController;

    public AddRecipeView(AddRecipeController addRecipeController) {
        this.addRecipeController = addRecipeController;

        setTitle("Add Recipe");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create UI components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("Recipe Name:");
        JTextField nameField = new JTextField(20);

        JLabel ingredientsLabel = new JLabel("Ingredients (name:quantity, separated by |):");
        JTextField ingredientsField = new JTextField(20);

        JLabel instructionsLabel = new JLabel("Instructions:");
        JTextArea instructionsArea = new JTextArea(5, 20);

        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        // Add components to the panel
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ingredientsLabel);
        panel.add(ingredientsField);
        panel.add(instructionsLabel);
        panel.add(new JScrollPane(instructionsArea));
        panel.add(submitButton);
        panel.add(cancelButton);

        add(panel);

        // Button actions
        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String ingredientsInput = ingredientsField.getText();
            String instructions = instructionsArea.getText();

            if (name.isEmpty() || ingredientsInput.isEmpty() || instructions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Parse ingredients
                List<String> ingredientStrings = List.of(ingredientsInput.split("\\|"));
                List<Ingredient> ingredients = new ArrayList<>();
                for (String ingredient : ingredientStrings) {
                    String[] parts = ingredient.split(":");
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Invalid ingredient format: " + ingredient);
                    }
                    ingredients.add(new Ingredient(parts[0].trim(), parts[1].trim()));
                }

                LoggedInViewModel viewModel = new LoggedInViewModel();
                LoggedInState state = viewModel.getState();
                String username = state.getUsername();
                addRecipeController.addRecipe(name, ingredients, instructions, username, "Local");
                JOptionPane.showMessageDialog(this, "Recipe added successfully!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to add recipe: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }
}

