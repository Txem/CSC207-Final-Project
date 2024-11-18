package entity;

import java.util.List;

public class CommonRecipe implements Recipe {
    private String recipeName;
    private List<Ingredient> ingredients;
    private String instructions;
    private User user;

    public CommonRecipe(String recipeName, List<Ingredient> ingredients, String instruction, User user) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.instructions = instruction;
        this.user = user;
    }

    @Override
    public String getName() {
        return this.recipeName;
    }

    @Override
    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public String getInstructions() {
        return this.instructions;
    }

    @Override
    public User getUser() {
        return this.user;
    }
}
