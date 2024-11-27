package entity;

import java.util.List;

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

    public String getTags() {
        return this.tag;
    }
}
