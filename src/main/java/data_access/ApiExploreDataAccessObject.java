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

    private List<CommonRecipe> recipes;
    private String keyword;
    private boolean resultExist;

    public ApiExploreDataAccessObject(String keyword) {
        this.recipes = getRecipeList(keyword);
        this.keyword = keyword;
    }

    public ApiExploreDataAccessObject() {
        this.recipes = new ArrayList<>();
        this.keyword = null;
    }

    public static String getApiToken() {
        return System.getenv("token");
    }

    @Override
    public Boolean existByKeyword(String keyword) {
        return resultExist;
    }

    @Override
    public String getCurrentKeyWord() {
        return keyword;
    }

    @Override
    public List<CommonRecipe> getRecipeList(String keyword) {
        // Make an API call to get the user object.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format("https://api.edamam.com/api/recipes/v2?type=public&q=%s&app_id=%s&app_key=%s",
                        keyword, "d50864f2", ApiExploreDataAccessObject.getApiToken()))
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
            if (response.code() == SUCCESS_CODE) {
                System.out.println("we got something");
                final JSONArray recipesJSONArray = responseBody.getJSONArray("hits");
                resultExist = true;

                for (int i = 0; i < recipesJSONArray.length(); i++) {
                    final String recipeName = recipesJSONArray.getJSONObject(i).getJSONObject("recipe").getString("label");
                    final JSONArray ingredientsJSONObject = recipesJSONArray.getJSONObject(i).getJSONObject("recipe").getJSONArray("ingredients");
                    final List<Ingredient> ingredients = new ArrayList<Ingredient>();
                    for (int j = 0; j < ingredientsJSONObject.length(); j++) {
                        final String ingredientName = ingredientsJSONObject.getJSONObject(j).getString("food");
//                        final String quantity = ingredientsJSONObject.getJSONObject(j).getInt("quantity")
//                                + ingredientsJSONObject.getJSONObject(j).getString("measure");

                        final String quantity = ingredientsJSONObject.getJSONObject(j).getString("text");
                        final Ingredient ingredient = new Ingredient(ingredientName, quantity);
                        ingredients.add(ingredient);
                    }
                    final JSONObject recipeObject = recipesJSONArray.getJSONObject(i).getJSONObject("recipe");
                    final String instruction = recipeObject.has("institution") ? recipeObject.getString("institution") : "Unknown";
                    final CommonRecipe commonRecipe = new CommonRecipe(recipeName, ingredients, instruction, null, null);
                    // TODO add tags
                    recipes.add(commonRecipe);
                }

                return recipes;
            }
            else {
                resultExist = false;
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void setCurrentKeyword(String keyword) {
        this.keyword = keyword;
    }
}

