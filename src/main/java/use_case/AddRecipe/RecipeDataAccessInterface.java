package use_case.AddRecipe;

import entity.Recipe;

public interface RecipeDataAccessInterface {
    void saveRecipeForUser(String username, Recipe recipe);
}
