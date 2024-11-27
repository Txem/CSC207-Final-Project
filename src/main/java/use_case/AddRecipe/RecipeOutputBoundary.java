package use_case.AddRecipe;

public interface RecipeOutputBoundary {
    void presentSuccess(RecipeOutputData outputData);
    void presentError(String errorMessage);
}
