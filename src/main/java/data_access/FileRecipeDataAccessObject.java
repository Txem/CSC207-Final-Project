package data_access;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Ingredient;
import entity.Recipe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.AddRecipe.RecipeDataAccessInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileRecipeDataAccessObject implements RecipeDataAccessInterface {
    private static final String DEFAULT_JSON_FILE = "recipe.json";
    private static String OUTPUT_FILE = "recipe.json";
    private final File jsonFile;
    private final JSONObject recipeJson;
    private final Map<String, List<Recipe>> userRecipes = new HashMap<>();
    private final List<Recipe> recipes = new ArrayList<>();

    public FileRecipeDataAccessObject() throws IOException {
        this.jsonFile = new File(DEFAULT_JSON_FILE);
        System.out.println("init success");
        recipeJson = new JSONObject();
//        save();
//        if (jsonFile.exists() && jsonFile.length() > 0) {
//            loadData();
//        }
//        else {
//            save();
//        }
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
        System.out.println("save exe");
        try {
            JSONObject fileContent;
            JSONArray recipesArray;

            java.io.File file = new java.io.File(OUTPUT_FILE);
            // Read the file content safely
            StringBuilder fileData = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(OUTPUT_FILE))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    fileData.append(line);
                }
            }
            fileContent = new JSONObject(fileData.toString());
            recipesArray = fileContent.optJSONArray("recipes");

            // Add the new recipe to the array, even if it exists
            recipesArray.put(recipeJson);

            // Update the "recipes" key in the JSON object
            fileContent.put("recipes", recipesArray);

            // Write the updated JSON back to the file
            try (FileWriter writer = new FileWriter(OUTPUT_FILE)) {
                writer.write(fileContent.toString(4)); // Pretty print with 4 spaces
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException("Error appending recipe to file", e);
        }

//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, userRecipes);
//        }
//        catch (IOException e) {
//            throw new RuntimeException("Error saving data to JSON file.", e);
//        }
    }

    @Override
    public void saveRecipeForUser(Recipe recipe) {
        recipes.add(recipe);

        JSONArray ingredientsArray = new JSONArray();
        recipeJson.put("label", recipe.getName());

// 遍历每个 Ingredient，将其添加到 ingredientsArray
        for (Ingredient gredient : recipe.getIngredients()) {
            JSONObject ingredientJson = new JSONObject();
            ingredientJson.put("text", gredient.getIngredientName());
            ingredientJson.put("quantity", gredient.getQuantity());
            ingredientsArray.put(ingredientJson); // 直接加入数组
        }

// 将 ingredientsArray 直接作为 ingredients 字段放入 recipeJson
        recipeJson.put("ingredients", ingredientsArray);

        recipeJson.put("instructions", recipe.getInstructions());
        recipeJson.put("username", recipe.getUserName());
        recipeJson.put("tag", recipe.getTag());
//        String username = recipe.getUserName();
//        userRecipes.computeIfAbsent(username, k -> new ArrayList<>()).add(recipe);
        this.save();
    }
}


