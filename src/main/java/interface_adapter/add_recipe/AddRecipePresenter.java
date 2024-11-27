package interface_adapter.add_recipe;

import use_case.AddRecipe.RecipeOutputBoundary;
import use_case.AddRecipe.RecipeOutputData;

public class AddRecipePresenter implements RecipeOutputBoundary {
    private final AddRecipeViewModel addRecipeViewModel;

    public AddRecipePresenter(AddRecipeViewModel addRecipeViewModel) {
        this.addRecipeViewModel = addRecipeViewModel;
    }

    @Override
    public void presentSuccess(RecipeOutputData outputData) {
        addRecipeViewModel.setMessage(outputData.getMessage());
        addRecipeViewModel.setSuccess(true);
    }

    @Override
    public void presentError(String errorMessage) {
        addRecipeViewModel.setMessage(errorMessage);
        addRecipeViewModel.setSuccess(false);
    }
}

