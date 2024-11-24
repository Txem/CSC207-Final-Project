package data_access;

import entity.CommonRecipe;
import entity.Ingredient;
import entity.Recipe;
import use_case.AddRecipe.RecipeDataAccessInterface;

import java.io.*;
import java.util.*;

public class FileRecipeDataAccessObject implements RecipeDataAccessInterface {
    private final File csvFile;
    private final Map<String, List<Recipe>> userRecipes = new HashMap<>();

    public FileRecipeDataAccessObject(String csvFilePath) throws IOException {
        this.csvFile = new File(csvFilePath);

        if (csvFile.exists() && csvFile.length() > 0) {
            loadData();
        } else {
            save();
        }
    }

    private void loadData() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String header = reader.readLine();
            if (header == null || !header.equals("username,recipeName,instructions,ingredients")) {
                throw new RuntimeException("Invalid CSV header.");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",", -1);
                String  username = columns[0];
                String recipeName = columns[1];
                String instructions = columns[2];
                String ingredientsData = columns[3];

                List<Ingredient> ingredients = new ArrayList<>();
                if (!ingredientsData.isEmpty()) {
                    String[] ingredientPairs = ingredientsData.split("\\|");
                    for (String pair : ingredientPairs) {
                        String[] parts = pair.split(":");
                        ingredients.add(new Ingredient(parts[0], parts[1]));
                    }
                }

                Recipe recipe = new CommonRecipe(recipeName, ingredients, instructions, username);
                userRecipes.computeIfAbsent(username, k -> new ArrayList<>()).add(recipe);
            }
        }
    }

    @SuppressWarnings("checkstyle:FinalLocalVariable")
    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write("username,recipeName,instructions,ingredients");
            writer.newLine();

            for (Map.Entry<String, List<Recipe>> entry : userRecipes.entrySet()) {
                String username = entry.getKey();
                for (Recipe recipe : entry.getValue()) {
                    StringBuilder ingredientsBuilder = new StringBuilder();
                    for (Ingredient ingredient : recipe.getIngredients()) {
                        if (ingredientsBuilder.length() > 0) {
                            ingredientsBuilder.append("|");
                        }
                        ingredientsBuilder.append(ingredient.getIngredientName())
                                .append(":")
                                .append(ingredient.getQuantity());
                    }
                    writer.write(String.join(",", username, recipe.getName(), recipe.getInstructions(), ingredientsBuilder.toString()));
                    writer.newLine();
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error saving data to CSV file.", ex);
        }
    }

    @Override
    public void saveRecipeForUser(String username, Recipe recipe) {
        userRecipes.computeIfAbsent(username, k -> new ArrayList<>()).add(recipe);
        save();
    }
}

