package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for organizing the recipes by categories.
 */
public class RecipeOrganizer {
    private final Map<String, List<Recipe>> recipeCategories;

    public RecipeOrganizer() {
        recipeCategories = new HashMap<>();
    }

    /**
     * Adds a recipe to the specified category.
     *
     * @param category the category to add the recipe to
     * @param recipe the recipe to add
     */
    public void addRecipe(String category, Recipe recipe) {
        recipeCategories.putIfAbsent(category, new ArrayList<>());
        recipeCategories.get(category).add(recipe);
    }

    /**
     * Gets all the recipes in the specified category.
     *
     * @param category the category to get the recipes from
     * @return a list of recipes in the specified category, or an empty list if there are no recipes in that category
     */
    public List<Recipe> getRecipesByCategory(String category) {
        return recipeCategories.getOrDefault(category, Collections.emptyList());
    }

    /**
     * Displays all the recipes in all categories.
     */
    public void displayAllRecipes() {
        recipeCategories.forEach((category, recipes) -> {
            System.out.println("Category: " + category);
            recipes.forEach(recipe -> System.out.println(recipe + "\n"));
        });
    }
}
