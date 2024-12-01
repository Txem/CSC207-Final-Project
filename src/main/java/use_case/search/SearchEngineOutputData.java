package use_case.search;

import java.util.List;

import entity.CommonRecipe;
import entity.Recipe;

/**
 * Init the output data of the recipe list.
 */
public class SearchEngineOutputData {
    private final List<CommonRecipe> recipes;
    private final Boolean responseSuccess;
    private final String keyword;

    public SearchEngineOutputData(List<CommonRecipe> recipes, Boolean responseSuccess, String keyword) {
        this.recipes = recipes;
        this.responseSuccess = responseSuccess;
        this.keyword = keyword;
    }

    public List<CommonRecipe> getRecipes() {
        return recipes;
    }

    public Boolean getResponseSuccess() {
        return responseSuccess;
    }

    public String getKeyword() {
        return keyword;
    }
}
