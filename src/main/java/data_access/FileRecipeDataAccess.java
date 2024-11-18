package use_case.AddRecipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import entity.Recipe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileRecipeDataAccess implements RecipeDataAccessInterface {
    private final File recipeFile;
    private final ObjectMapper objectMapper;

    public FileRecipeDataAccess(String recipeFilePath) {
        this.recipeFile = new File(recipeFilePath);
        this.objectMapper = new ObjectMapper();

        // Initialize the JSON file if it doesn't exist
        if (!recipeFile.exists()) {
            try {
                recipeFile.createNewFile();
                objectMapper.writeValue(recipeFile, new HashMap<String, List<Recipe>>());
            } catch (IOException e) {
                throw new RuntimeException("Could not create recipe file.", e);
            }
        }
    }

    @Override
    public void saveRecipe(Recipe recipe) throws Exception {
        Map<String, List<Recipe>> userRecipes = loadAllUserRecipes();

        // Extract username and recipes list
        String username = recipe.getUser().getName(); // Assuming Recipe entity has a `getUser` method
        List<Recipe> recipes = userRecipes.getOrDefault(username, new ArrayList<>());

        // Add the new recipe to the user's list
        recipes.add(recipe);
        userRecipes.put(username, recipes);

        // Save updated data back to the file
        saveAllUserRecipes(userRecipes);
    }

    // Helper method to load all user recipes from the file
    private Map<String, List<Recipe>> loadAllUserRecipes() throws IOException {
        if (recipeFile.length() == 0) { // Empty file check
            return new HashMap<>();
        }
        return objectMapper.readValue(recipeFile, new TypeReference<Map<String, List<Recipe>>>() {});
    }

    // Helper method to save all user recipes to the file
    private void saveAllUserRecipes(Map<String, List<Recipe>> userRecipes) throws IOException {
        objectMapper.writeValue(recipeFile, userRecipes);
    }
}



