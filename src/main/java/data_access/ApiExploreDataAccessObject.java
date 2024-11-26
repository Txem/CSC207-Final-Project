package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.search.SearchEngineUserDataAccessInterface;


/**
 * Search the api on the internet.
 */

public class ApiExploreDataAccessObject implements SearchEngineUserDataAccessInterface {

    private static final int SUCCESS_CODE = 200;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String RECIPE_LABEL = "hits";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";

    private List<Recipe> recipes;
    private boolean resultExist;

    public ApiExploreDataAccessObject(String keyword) {
        this.recipes = getRecipeList(keyword);
    }

    public static String getApiToken() {
        return System.getenv("token");
    }

    @Override
    public Boolean existByKeyword(String keyword) {
        return null;
    }

    @Override
    public String getCurrentKeyWord() {
        return "";
    }

    @Override
    public List<Recipe> getRecipeList(String keyword) {
        // Make an API call to get the user object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format("https://api.edamam.com/api/recipes/v2?q=%s&app_id=%s&app_key=%s",
                        keyword, "d50864f2", ApiExploreDataAccessObject.getApiToken()))
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                final JSONArray recipesJSONObject = responseBody.getJSONArray("hits");
                for (int i = 0; i < recipesJSONObject.length(); i++) {
                    final String recipeName = recipesJSONObject.getJSONObject(i).getString("label");
                    final JSONArray ingredientsJSONObject = recipesJSONObject.getJSONObject(i).getJSONArray("ingredients");
                    final List<Ingredient> ingredients = new ArrayList<Ingredient>();
                    for (int j = 0; j < ingredientsJSONObject.length(); j++) {
                        final String ingredientName = ingredientsJSONObject.getJSONObject(j).getString("food");
                        final String quantity = ingredientsJSONObject.getJSONObject(j).getString("quantity")
                                + ingredientsJSONObject.getJSONObject(j).getString("pound");
                        final Ingredient ingredient = new Ingredient(ingredientName, quantity);
                        ingredients.add(ingredient);
                    }
                    final String instuction = recipesJSONObject.getJSONObject(i).getString("institution");
                    // TODO add tags
                    final CommonRecipe commonRecipe = new CommonRecipe(recipeName, ingredients, instuction, null);
                    recipes.add(commonRecipe);
                }

                return recipes;
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void setCurrentKeyword(String keyword) {

    }
}

