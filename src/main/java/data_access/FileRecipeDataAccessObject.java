package data_access;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Recipe;
import use_case.AddRecipe.RecipeDataAccessInterface;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileRecipeDataAccessObject implements RecipeDataAccessInterface {
    private static final String DEFAULT_JSON_FILE = "localRecipe.json";
    private final File jsonFile;
    private final Map<String, List<Recipe>> userRecipes = new HashMap<>();

    public FileRecipeDataAccessObject() throws IOException {
        this.jsonFile = new File(DEFAULT_JSON_FILE);
        if (jsonFile.exists() && jsonFile.length() > 0) {
            loadData();
        } else {
            save();
        }
    }


    private void loadData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, List<Recipe>> loadedData = objectMapper.readValue(jsonFile, new TypeReference<Map<String, List<Recipe>>>() {});
            userRecipes.putAll(loadedData);
        } catch (Exception e) {
            throw new IOException("Failed to load data from JSON file.", e);
        }
    }

    private void save() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, userRecipes);
        } catch (IOException e) {
            throw new RuntimeException("Error saving data to JSON file.", e);
        }
    }

    @Override
    public void saveRecipeForUser(Recipe recipe) {
        String username = recipe.getUserName();
        userRecipes.computeIfAbsent(username, k -> new ArrayList<>()).add(recipe);
        save();
    }
}


