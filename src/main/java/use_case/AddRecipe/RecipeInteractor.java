package use_case.AddRecipe;

import entity.CommonRecipe;
import entity.Recipe;

public class RecipeInteractor implements use_case.recipe.RecipeInputBoundary {
    private final RecipeDataAccessInterface recipeDataAccessInterface;
    private final RecipeOutputBoundary recipeOutputBoundary;

    public RecipeInteractor(RecipeDataAccessInterface recipeDataAccessInterface, RecipeOutputBoundary recipeOutputBoundary) {
        this.recipeDataAccessInterface = recipeDataAccessInterface;
        this.recipeOutputBoundary = recipeOutputBoundary;
    }

    @Override
    public void execute(RecipeInputData inputData) {
        Recipe recipe = new CommonRecipe(inputData.getName(), inputData.getIngredients(), inputData.getInstructions(), inputData.getUser());

    }
}
