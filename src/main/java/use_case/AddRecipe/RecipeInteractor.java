package use_case.recipe;

import entity.Recipe;
import entity.CommonRecipe;
import use_case.AddRecipe.RecipeDataAccessInterface;
import use_case.AddRecipe.RecipeInputData;
import use_case.AddRecipe.RecipeOutputBoundary;
import use_case.AddRecipe.RecipeOutputData;

public class RecipeInteractor implements use_case.recipe.RecipeInputBoundary {
    private final RecipeDataAccessInterface recipeDataAccess;
    private final RecipeOutputBoundary addRecipePresenter;

    public RecipeInteractor(RecipeDataAccessInterface recipeDataAccess, RecipeOutputBoundary addRecipePresenter) {
        this.recipeDataAccess = recipeDataAccess;
        this.addRecipePresenter = addRecipePresenter;
    }

    @Override
    public void execute(RecipeInputData inputData) {
        try {
            Recipe recipe = new CommonRecipe(
                    inputData.getName(),
                    inputData.getIngredients(),
                    inputData.getInstructions(),
                    inputData.getUsername(),
                    inputData.getTags()
            );
            recipeDataAccess.saveRecipeForUser(recipe);

            RecipeOutputData outputData = new RecipeOutputData("Recipe added successfully", true);
            addRecipePresenter.presentSuccess(outputData);
        } catch (Exception e) {
            RecipeOutputData outputData = new RecipeOutputData("Failed to add recipe: " + e.getMessage(), false);
            addRecipePresenter.presentError(outputData.getMessage());
        }
    }
}


