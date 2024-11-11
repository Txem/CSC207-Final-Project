package data_access;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class EdamamAPI {
    private static final String APP_ID = "d50864f2";  // 替换为你的ID
    private static final String APP_KEY = "98322a9a5ec5ced4eb6c6afd4c5d52d4"; // 替换为你的KEY

    public static void searchRecipe(String query) throws IOException {
        String apiUrl = "https://api.edamam.com/search?q=" + query +
                "&app_id=" + APP_ID + "&app_key=" + APP_KEY;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            String response = new Scanner(connection.getInputStream()).useDelimiter("\\A").next();
            parseAndDisplayRecipes(response);
        } else {
            System.out.println("API request failed with response code: " + responseCode);
        }
    }

    private static void parseAndDisplayRecipes(String response) {
        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        JsonArray hits = jsonResponse.getAsJsonArray("hits");

        if (hits.isEmpty()) {
            System.out.println("No recipes found.");
            return;
        }

        for (int i = 0; i < hits.size(); i++) {
            JsonObject recipe = hits.get(i).getAsJsonObject().getAsJsonObject("recipe");
            String label = recipe.get("label").getAsString();
            String url = recipe.get("url").getAsString();
            System.out.println((i + 1) + ". " + label + ": " + url);
        }
    }
}