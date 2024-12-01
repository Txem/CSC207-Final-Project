package use_case.search;

import java.util.List;

import entity.CommonRecipe;
import entity.Recipe;

/**
 * Interface for search engine.
 */
public interface SearchEngineUserDataAccessInterface {

    /**
     * Return whether there is a response for the keyword.
     * @param keyword the keyword user type in.
     * @return a boolean represent whether there is a keyword.
     */
    Boolean existByKeyword(String keyword);

    /**
     * Returns the keyword of the curren keyword of searchEngine.
     * @return the keyword of current search.
     */
    String getCurrentKeyWord();

    /**
     * Get the recipe response from the api.
     * @param keyword the keyword from the user type
     * @return the json response (convert to recipe class).
     */
    List<CommonRecipe> getRecipeList(String keyword);

    /**
     * Sets the username indicating who is the current user of the application.
     * @param keyword the new current username
     */
    void setCurrentKeyword(String keyword);
}
