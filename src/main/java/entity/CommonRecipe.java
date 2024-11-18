package entity;

import java.util.List;

public class CommonRecipe implements Recipe {
    private final String recipeName;
    private final List<Ingredient> ingredients;
    private final String instructions;
    private final List<String> tags;


    public CommonRecipe(String recipeName, List<Ingredient> ingredients, String instruction, List<String> tags) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.instructions = instruction;
        this.tags = tags;
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

    public List<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Recipe: " + recipeName + "\nIngredients: " + ingredients + "\nInstructions: " + instructions
                + "\nTags: " + tags;
    }
}
