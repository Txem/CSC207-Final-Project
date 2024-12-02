package data_access;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            if (recipeNode.get("instructions") == null) {
                instruction = "No instructions provided.";
            }
            else {
                instruction = recipeNode.get("instructions").asText();
            }
            final String tag;
            tag = recipeNode.get("tag").asText();
            final String username = recipeNode.get("username").asText();

            final List<Ingredient> ingredients = new ArrayList<>();
            recipeNode.get("ingredients").forEach(ingredientNode -> {
                final String name = ingredientNode.get("text").asText();
                String quantity = "";
                if (ingredientNode.has("measure")) {
                    quantity = ingredientNode.get("quantity").asText() + " "
                            + ingredientNode.get("measure").asText();
                }
                else {
                    quantity = ingredientNode.get("quantity").asText();
                }

                ingredients.add(new Ingredient(name, quantity));
            });

            final CommonRecipe commonRecipe = new CommonRecipe(recipeName, ingredients, instruction, username, tag);
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
            if (Objects.equals(recipe.getTag(), tag)) {
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
                if (Objects.equals(recipe.getTag(), tag)) {
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
