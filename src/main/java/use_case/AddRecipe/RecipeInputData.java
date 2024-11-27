package use_case.AddRecipe;

import java.util.List;
import entity.Ingredient;

public class RecipeInputData {
    private String name;
    private List<Ingredient> ingredients;
    private String instructions;
    private String username;
    private String tags;

    public RecipeInputData(String name, List<Ingredient> ingredients, String instructions, String username, String tags) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.username = username;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getUsername() {
        return username;
    }

    public String getTags() {
        return this.tags;
    }
}
