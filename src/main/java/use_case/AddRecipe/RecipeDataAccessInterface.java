package use_case.AddRecipe;

import entity.Recipe;

public interface RecipeDataAccessInterface {
    void saveRecipe(Recipe recipe) throws Exception; // Save a recipe
}
