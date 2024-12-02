package interface_adapter.searchengine;

import java.util.List;

import entity.CommonRecipe;
import entity.OnlineRecipe;

/**
 * The search state for the search Engine.
 */
public class SearchEngineState {
    private String keyword;
    private String searchError;
    private List<OnlineRecipe> recipes;

    public String getKeyword() {
        return keyword;
    }

    public String getSearchError() {
        return searchError;
    }

    public List<OnlineRecipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<OnlineRecipe> recipes) {
        this.recipes = recipes;
    }

    public void setSearchError(String searchError) {
        this.searchError = searchError;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
