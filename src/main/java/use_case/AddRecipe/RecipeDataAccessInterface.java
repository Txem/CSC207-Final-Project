package use_case.AddRecipe;

import entity.Recipe;

public interface RecipeDataAccessInterface {
    void saveRecipeForUser(Recipe recipe);
}
