package data_access;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Searching recipe using Edamam API.
 *
 */
public class EdamamApi {
    private static final String APP_ID = "d50864f2";
    private static final String APP_KEY = "98322a9a5ec5ced4eb6c6afd4c5d52d4";

    /**
     * Searching recipe using Edamam API.
     *
     * @param query searching can be null.
     * @throws IOException if API request failed.
     */
    public static void searchRecipe(String query) throws IOException {
        final String apiUrl = "https://api.edamam.com/search?q=" + query
                + "&app_id=" + APP_ID + "&app_key=" + APP_KEY;

        final HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");

        final int responseStatusCodeSuccess = 200;
        final int responseCode = connection.getResponseCode();
        if (responseCode == responseStatusCodeSuccess) {
            final String response = new Scanner(connection.getInputStream()).useDelimiter("\\A").next();
            parseAndDisplayRecipes(response);
        }
        else {
            System.out.println("API request failed with response code: " + responseCode);
        }
    }

    private static void parseAndDisplayRecipes(String response) {
        final JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        final JsonArray hits = jsonResponse.getAsJsonArray("hits");

        if (hits.isEmpty()) {
            System.out.println("No recipes found.");
            return;
        }
        for (int i = 0; i < hits.size(); i++) {
            final JsonObject recipe = hits.get(i).getAsJsonObject().getAsJsonObject("recipe");
            final String label = recipe.get("label").getAsString();
            final String url = recipe.get("url").getAsString();
            System.out.println((i + 1) + ". " + label + ": " + url);
        }
    }
}
