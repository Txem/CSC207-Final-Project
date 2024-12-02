package view;

import data_access.FileRecipeDataAccessObject;
import entity.Ingredient;
import interface_adapter.add_recipe.AddRecipeController;
import interface_adapter.add_recipe.AddRecipePresenter;
import interface_adapter.add_recipe.AddRecipeViewModel;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final AddRecipePresenter addRecipePresenter = new AddRecipePresenter(addRecipeViewModel);
        final RecipeInteractor recipeInteractor = new RecipeInteractor(fileRecipeDataAccessObject, addRecipePresenter);
        this.addRecipeController = new AddRecipeController(recipeInteractor);
        this.addRecipeViewModel = addRecipeViewModel;
        setTitle("Create Your Own Recipe");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background image
        JLabel backgroundLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("recipe background.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        setContentPane(backgroundLabel);
        backgroundLabel.setLayout(new GridBagLayout());

        // Create UI components
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Create Your Own Recipe");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        gbc.gridwidth = 1;

        JLabel nameLabel = new JLabel("Recipe Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField nameField = new JTextField(20);
        nameField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        nameField.setToolTipText("Enter the recipe name");

        JLabel ingredientsLabel = new JLabel("Ingredients (name:quantity, separated by |):");
        ingredientsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField ingredientsField = new JTextField(50);
        ingredientsField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        ingredientsField.setToolTipText("Example: sugar:100g | flour:200g");

        JLabel instructionsLabel = new JLabel("Instructions:");
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextArea instructionsArea = new JTextArea(10, 50);
        instructionsArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        JScrollPane instructionsScrollPane = new JScrollPane(instructionsArea);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(34, 139, 34));
        submitButton.setForeground(Color.black);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(220, 20, 60));
        cancelButton.setForeground(Color.black);

        // Add components to the panel with improved layout
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(ingredientsLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(ingredientsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(instructionsLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(instructionsScrollPane, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, gbc);

        backgroundLabel.add(panel, gbc);

        // Button actions
        submitButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String ingredientsInput = ingredientsField.getText().trim();
            String instructions = instructionsArea.getText().trim();

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
                JOptionPane.showMessageDialog(this, "Recipe added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to add recipe: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }
}


