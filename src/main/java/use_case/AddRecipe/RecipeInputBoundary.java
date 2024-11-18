package use_case.recipe;

import use_case.AddRecipe.RecipeInputData;

public interface RecipeInputBoundary {
    void execute(RecipeInputData inputData);
}

