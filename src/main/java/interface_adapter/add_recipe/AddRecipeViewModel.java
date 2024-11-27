package interface_adapter.add_recipe;

import entity.Ingredient;

import java.util.List;

public class AddRecipeViewModel {
    private final AddRecipeController controller;
    private final AddRecipeState state;

    public AddRecipeViewModel(AddRecipeController controller, AddRecipeState state) {
        this.controller = controller;
        this.state = state;
    }

    public AddRecipeState getState() {
        return state;
    }

    public void addRecipe(String name, List<Ingredient> ingredients, String instructions, String username, String tag) {
        try {
            controller.addRecipe(name, ingredients, instructions, username, tag);
        } catch (IllegalArgumentException e) {
            state.setMessage(e.getMessage());
            state.setSuccess(false);
        }
    }
}

