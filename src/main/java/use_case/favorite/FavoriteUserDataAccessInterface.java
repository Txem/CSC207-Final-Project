package use_case.favorite;

import entity.CommonRecipe;

public interface FavoriteUserDataAccessInterface {

    /**
     * Return the recipe of that id.
     * @param recipeId the recipe the user chose to favorite.
     * @param username the username
     */
    void fetchAndWriteRecipeById(String recipeId, String username);
}
