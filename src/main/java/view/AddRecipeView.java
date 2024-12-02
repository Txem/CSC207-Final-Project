package view;

import data_access.FileRecipeDataAccessObject;
import entity.Ingredient;
import entity.User;
import interface_adapter.add_recipe.AddRecipeController;
import interface_adapter.add_recipe.AddRecipePresenter;
import interface_adapter.add_recipe.AddRecipeViewModel;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import use_case.recipe.RecipeInteractor;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddRecipeView extends JFrame {
    private final AddRecipeController addRecipeController;
    private final AddRecipeViewModel addRecipeViewModel;
    private final FileRecipeDataAccessObject fileRecipeDataAccessObject;

    public AddRecipeView(AddRecipeViewModel addRecipeViewModel, String username) {
        try {
            fileRecipeDataAccessObject = new FileRecipeDataAccessObject();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        final AddRecipePresenter addRecipePresenter = new AddRecipePresenter(addRecipeViewModel);
        final RecipeInteractor recipeInteractor = new RecipeInteractor(fileRecipeDataAccessObject, addRecipePresenter);
        this.addRecipeController = new AddRecipeController(recipeInteractor);
        this.addRecipeViewModel = addRecipeViewModel;
        setTitle("Create your own Recipe");
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

