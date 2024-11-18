package data_access;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.CommonRecipe;
import entity.Ingredient;
import use_case.present_by_tag.PresentByTagDataAccessInterface;

/**
 * This class reads the JSON file and returns a list of CommonRecipe objects.
 * Each CommonRecipe object contains the recipe name, ingredients, instruction, and tags.
 */
public class RecipeDataAccessObject implements PresentByTagDataAccessInterface {
    private final String filePath;
    private final ObjectMapper objectMapper;

    public RecipeDataAccessObject(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * This method reads the JSON file and returns a list of CommonRecipe objects.
     * Each CommonRecipe object contains the recipe name, ingredients, instruction, and tags.
     *
     * @return List<CommonRecipe> a list of CommonRecipe objects.
     * @throws IOException
     */
    public List<CommonRecipe> getAllRecipes() throws IOException {
        final List<CommonRecipe> commonRecipes = new ArrayList<>();
        final var jsonNode = objectMapper.readTree(new File(filePath));
        final var recipesNode = jsonNode.get("recipes");

        for (JsonNode recipeNode : recipesNode) {
            final String recipeName;
            recipeName = recipeNode.get("label").asText();
            final String instruction;
            instruction = "Visit: " + recipeNode.get("url").asText();
            final List<String> tags = new ArrayList<>();
            tags.add(recipeNode.get("tag").asText());
            if (recipeNode.has("dietLabels")) {
                recipeNode.get("dietLabels").forEach(label -> tags.add(label.asText()));
            }

            final List<Ingredient> ingredients = new ArrayList<>();
            recipeNode.get("ingredients").forEach(ingredientNode -> {
                final String name = ingredientNode.get("text").asText();
                final String quantity = ingredientNode.get("quantity").asText() + " "
                        + ingredientNode.get("measure").asText();
                ingredients.add(new Ingredient(name, quantity));
            });

            final CommonRecipe commonRecipe = new CommonRecipe(recipeName, ingredients, instruction, tags);
            commonRecipes.add(commonRecipe);
        }

        return commonRecipes;
    }

    /**
     * Checks if the given tag exists.
     *
     * @param tag the tag to look for
     * @return true if a tag exists; false otherwise
     */

    @Override
    public boolean exist(String tag) throws IOException {
        for (CommonRecipe recipe : getAllRecipes()) {
            if (recipe.getTags().contains(tag)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<CommonRecipe> getRecipesByTag(String tag) throws IOException {
        if (exist(tag)) {
            final List<CommonRecipe> presentations = new ArrayList<>();
            for (CommonRecipe recipe : getAllRecipes()) {
                if (recipe.getTags().contains(tag)) {
                    presentations.add(recipe);
                }
            }
            return presentations;
        }
        else {
            System.out.println("Tag not found.");
            return new ArrayList<>();
        }
    }
}

