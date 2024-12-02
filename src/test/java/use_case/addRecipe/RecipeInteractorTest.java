package use_case.addRecipe;

import entity.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import entity.Ingredient;
import use_case.recipe.RecipeInteractor;
import use_case.recipe.RecipeInputBoundary;
import use_case.AddRecipe.RecipeInputData;
import use_case.AddRecipe.RecipeOutputBoundary;
import use_case.AddRecipe.RecipeOutputData;
import use_case.AddRecipe.RecipeDataAccessInterface;

public class RecipeInteractorTest {
    private RecipeInteractor interactor;
    private RecipeOutputBoundary outputBoundary;
    private RecipeDataAccessInterface dataAccess;

    @BeforeEach
    public void setUp() {
        // Initialize the actual dependencies
        outputBoundary = new RecipeOutputBoundary() {
            @Override
            public void presentSuccess(RecipeOutputData outputData) {
                assertNotNull(outputData);
            }

            @Override
            public void presentError(String errorMessage) {
                assertNotNull(errorMessage);
            }
        };

        dataAccess = new RecipeDataAccessInterface() {
            @Override
            public void saveRecipeForUser(Recipe recipe) {
                // Simulate saving the recipe without error
            }
        };

        // Initialize the interactor with actual dependencies
        interactor = new RecipeInteractor(dataAccess, outputBoundary);
    }

    @Test
    public void testAddRecipeSuccess() {
        // Create sample input data
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("Tomato", "2 pieces"),
                new Ingredient("Pasta", "200g"),
                new Ingredient("Cheese", "100g")
        );
        RecipeInputData inputData = new RecipeInputData("Pasta", ingredients, "Boil pasta, add sauce", "user123", "local");

        // Call the method under test
        interactor.execute(inputData);
    }

    @Test
    public void testAddRecipeFailureDueToEmptyName() {
        // Create sample input data with empty recipe name
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("Tomato", "2 pieces"),
                new Ingredient("Pasta", "200g"),
                new Ingredient("Cheese", "100g")
        );
        RecipeInputData inputData = new RecipeInputData("", ingredients, "Boil pasta, add sauce", "user123", "local");

        // Call the method under test
        interactor.execute(inputData);
    }

    @Test
    public void testAddRecipeDataAccessError() {
        // Create sample input data
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("Tomato", "2 pieces"),
                new Ingredient("Pasta", "200g"),
                new Ingredient("Cheese", "100g")
        );
        RecipeInputData inputData = new RecipeInputData("Pasta", ingredients, "Boil pasta, add sauce", "user123", "local");

        // Override the dataAccess to simulate an error
        dataAccess = new RecipeDataAccessInterface() {
            @Override
            public void saveRecipeForUser(Recipe recipe) {
                throw new RuntimeException("Database error");
            }
        };

        // Reinitialize the interactor with the new dataAccess
        interactor = new RecipeInteractor(dataAccess, outputBoundary);

        // Call the method under test
        interactor.execute(inputData);
    }
}
