package entity;

import java.util.List;

/**
 * This class represents a common recipe.
 */
public class CommonRecipe implements Recipe {
    private String recipeName;
    private List<Ingredient> ingredients;
    private String instructions;
    private String username;
    private String tag;

    public CommonRecipe(String recipeName, List<Ingredient> ingredients, String instruction, String username, String tag) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.instructions = instruction;
        this.username = username;
        this.tag = tag;
    }

    @Override
    public String getName() {
        return this.recipeName;
    }

    @Override
    public String getIngredients() {
        return this.ingredients.toString();
    }

    @Override
    public String getInstructions() {
        return this.instructions;
    }

    @Override
    public String getUserName() {
        return this.username;
    }

    @Override
    public String getTag() {
        return this.tag;
    }

    @Override
    public String toString() {
        return "Recipe: " + recipeName + "\nIngredients: " + ingredients + "\nInstructions: " + instructions
                + "\nTag: " + tag + "\nUsername: " + username + "\n";
    }
}
