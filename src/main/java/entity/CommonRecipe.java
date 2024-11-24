package entity;

import java.util.List;

public class CommonRecipe implements Recipe {
    private String recipeName;
    private List<Ingredient> ingredients;
    private String instructions;
    private String username;

    public CommonRecipe(String recipeName, List<Ingredient> ingredients, String instruction, String username) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.instructions = instruction;
        this.username = username;
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
    public String getUserName() {
        return this.username;
    }
}
