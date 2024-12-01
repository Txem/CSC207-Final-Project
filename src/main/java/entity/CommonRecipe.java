package entity;

import java.util.List;

public class CommonRecipe implements Recipe {
    private String recipeName;
    private List<Ingredient> ingredients;
    private String instructions;
    private String username;
    private String tag;
    private String recipeId;

    public CommonRecipe(String recipeName, List<Ingredient> ingredients, String instruction, String username, String tag) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.instructions = instruction;
        this.username = username;
        this.tag = tag;
        recipeId = null;
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

    public String getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
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
        final StringBuilder ingredientsList = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
            ingredientsList.append(ingredient).append("\n");
        }
        return "Recipe: " + recipeName + "\nIngredients:\n" + ingredientsList
                + "Instructions: " + instructions + "\nTag: " + tag + "\nUsername: " + username + "\n";
    }

}
