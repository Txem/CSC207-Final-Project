package interface_adapter.searchengine;

import java.util.List;

import entity.CommonRecipe;

/**
 * The search state for the search Engine.
 */
public class SearchEngineState {
    private String keyword;
    private String searchError;
    private List<CommonRecipe> recipes;

    public String getKeyword() {
        return keyword;
    }

    public String getSearchError() {
        return searchError;
    }

    public List<CommonRecipe> getRecipes() {
        return recipes;
    }

    public List<CommonRecipe> setRecipes(List<CommonRecipe> recipes) {
        this.recipes = recipes;
        return recipes;
    }

    public void setSearchError(String searchError) {
        this.searchError = searchError;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
