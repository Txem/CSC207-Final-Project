package interface_adapter.add_recipe;

import use_case.AddRecipe.RecipeOutputBoundary;
import use_case.AddRecipe.RecipeOutputData;

public class AddRecipePresenter implements RecipeOutputBoundary {
    private final AddRecipeState state;

    public AddRecipePresenter(AddRecipeState state) {
        this.state = state;
    }

    @Override
    public void presentSuccess(RecipeOutputData outputData) {
        state.setMessage(outputData.getMessage());
        state.setSuccess(true);
    }

    @Override
    public void presentError(String errorMessage) {
        state.setMessage(errorMessage);
        state.setSuccess(false);
    }
}

