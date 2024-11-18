package use_case.AddRecipe;

import java.util.List;
import entity.Ingredient;
import entity.User;

public class RecipeInputData {
    private String name;
    private List<Ingredient> ingredients;
    private String instructions;
    private User user;

    public RecipeInputData(String name, List<Ingredient> ingredients, String instructions, User user) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.user = user;
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

    public User getUser() {
        return user;
    }
}
