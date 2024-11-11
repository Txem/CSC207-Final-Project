package entity;

import java.util.*;

public class RecipeOrganizer {
    private final Map<String, List<Recipe>> recipeCategories;

    public RecipeOrganizer() {
        recipeCategories = new HashMap<>();
    }

    public void addRecipe(String category, Recipe recipe) {
        recipeCategories.putIfAbsent(category, new ArrayList<>());
        recipeCategories.get(category).add(recipe);
    }

    public List<Recipe> getRecipesByCategory(String category) {
        return recipeCategories.getOrDefault(category, Collections.emptyList());
    }

    public void displayAllRecipes() {
        recipeCategories.forEach((category, recipes) -> {
            System.out.println("Category: " + category);
            recipes.forEach(recipe -> System.out.println(recipe + "\n"));
        });
    }
}
