package entity;

import java.util.List;

public class CommonRecipe implements Recipe {
    private String recipeName;
    private List<Ingredient> ingredients;
    private String instructions;

    public CommonRecipe(String recipeName, List<Ingredient> ingredients, String instruction) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.instructions = instruction;
    }

    @Override
    public String getName() {
        return this.recipeName;
    }

    @Override
    public String getIngredients() {
        return this.ingredients.toString();
    }

    public String getInstructions() {
        return this.instructions;
    }
}