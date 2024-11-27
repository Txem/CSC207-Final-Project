package interface_adapter.add_recipe;
import entity.Ingredient;
import use_case.AddRecipe.RecipeInputData;

import java.util.List;


public class AddRecipeController {
    private final use_case.recipe.RecipeInputBoundary recipeInteractor;

    public AddRecipeController(use_case.recipe.RecipeInputBoundary recipeInteractor) {
        this.recipeInteractor = recipeInteractor;
    }

    public void addRecipe(String name, List<Ingredient> ingredients, String instructions, String username, String tag) {
        if (name == null || name.isEmpty() || ingredients == null || ingredients.isEmpty() ||
                instructions == null || instructions.isEmpty() ) {
            throw new IllegalArgumentException("Invalid recipe input");
        }
        RecipeInputData inputData = new RecipeInputData(name, ingredients, instructions, username, tag);
        recipeInteractor.execute(inputData);
    }
}

