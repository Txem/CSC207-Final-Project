package use_case.search;

import java.util.List;

import entity.Recipe;

/**
 * Init the output data of the recipe list.
 */
public class SearchEngineOutputData {
    private final List<Recipe> recipes;
    private final Boolean responseSuccess;

    public SearchEngineOutputData(List<Recipe> recipes, Boolean responseSuccess) {
        this.recipes = recipes;
        this.responseSuccess = responseSuccess;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public Boolean getResponseSuccess() {
        return responseSuccess;
    }
}
