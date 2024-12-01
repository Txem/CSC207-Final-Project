package data_access;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.favorite.FavoriteUserDataAccessInterface;

/**
 * Search the recipe by id using the API and write it to a file.
 */
public class SearchById implements FavoriteUserDataAccessInterface {

    private static final String BASE_URL = "https://api.edamam.com/api/recipes/v2/";
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String APP_ID = "d50864f2";
    private static final String OUTPUT_FILE = "recipe.json";

    /**
     * Fetch recipe details by its ID and write the JSON response to a file.
     *
     * @param recipeId The unique ID of the recipe to fetch.
     * @param username the username
     */
    public void fetchAndWriteRecipeById(String recipeId, String username) {
        // Build the API URL
        final String apiUrl = String.format("%s%s?type=public&app_id=%s&app_key=%s", BASE_URL, recipeId,
                APP_ID, SearchById.getApiToken());

        // Set up HTTP client
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            // Execute the request
            final Response response = client.newCall(request).execute();

            // Check if the response is successful
            if (response.isSuccessful()) {
                final JSONObject responseBody = new JSONObject(response.body().string());

                final JSONObject recipeJson = responseBody.getJSONObject("recipe");

                if (hasUserSavedRecipe(recipeJson.getString("uri"), username)) {
                    System.out.println("Already downloaded");
                }
                else {
                    writeJsonToFile(recipeJson, username);
                }
            } else {
                throw new RuntimeException("Failed to fetch recipe: " + response.message());
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException("Error fetching recipe by ID", e);
        }
    }

    /**
     * Write JSON object to a file.
     *
     * @param recipeJson the recipe.
     * @param username   the user's name.
     */
    private void writeJsonToFile(JSONObject recipeJson, String username) {
        try {
            // Add username and tag to the recipe
            recipeJson.put("username", username);
            recipeJson.put("tag", "favorite");

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
                System.out.println("Recipe added to " + OUTPUT_FILE);
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException("Error appending recipe to file", e);
        }
    }

    private static final String RECIPE_FILE = "src/recipe.json"; // Path to the recipe file

    /**
     * Check if a user has already saved the same recipe.
     *
     * @param uri      The unique ID of the recipe to check.
     * @param username The username to check for.
     * @return True if the user already saved the recipe, false otherwise.
     */
    public boolean hasUserSavedRecipe(String uri, String username) {
        try {
            // Read the JSON file
            JSONObject fileContent = readJsonFile();
            if (fileContent == null) {
                return false; // No recipes file exists
            }

            // Get the recipes array
            JSONArray recipesArray = fileContent.optJSONArray("recipes");
            if (recipesArray == null) {
                return false; // No recipes exist in the file
            }

            // Iterate through the recipes and check if the user already saved the recipe
            for (int i = 0; i < recipesArray.length(); i++) {
                JSONObject recipe = recipesArray.getJSONObject(i);
                if (recipe.getString("uri").equals(uri) && recipe.getString("username").equals(username)) {
                    return true; // User already saved this recipe
                }
            }
            return false; // Recipe exists, but the user has not saved it
        }
        catch (IOException | JSONException e) {
            throw new RuntimeException("Error checking if user saved the recipe", e);
        }
    }

    /**
     * Read the JSON file containing the recipes.
     *
     * @return The JSONObject representing the file's content, or null if the file does not exist.
     */
    private JSONObject readJsonFile() throws IOException, JSONException {
        java.io.File file = new java.io.File(OUTPUT_FILE);
        if (!file.exists()) {
            return null; // File does not exist
        }

        // Read the file content
        StringBuilder fileData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileData.append(line);
            }
        }

        // Convert file content to JSON object
        return new JSONObject(fileData.toString());
    }

    /**
     * Fetches the API token from environment variables.
     *
     * @return The API token.
     */
    private static String getApiToken() {
        return System.getenv("token");
    }
}