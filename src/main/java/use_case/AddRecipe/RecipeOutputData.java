package use_case.AddRecipe;

public class RecipeOutputData {
    private String message;
    private boolean useCaseFailed;

    public RecipeOutputData(String message, boolean useCaseFailed) {
        this.message = message;
        this.useCaseFailed = useCaseFailed;
    }
    public String getMessage() {
        return message;
    }
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
