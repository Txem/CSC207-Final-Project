package use_case.AddRecipe;

import entity.CommonRecipe;
import entity.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.Mockito.*;

class AddRecipeInteractorTest {

    private RecipeDataAccessInterface recipeDataAccessMock;
    private RecipeOutputBoundary outputBoundaryMock;
    private use_case.recipe.RecipeInteractor interactor;

    @BeforeEach
    void setUp() {
        recipeDataAccessMock = mock(RecipeDataAccessInterface.class);
        outputBoundaryMock = mock(RecipeOutputBoundary.class);
        interactor = new use_case.recipe.RecipeInteractor(recipeDataAccessMock, outputBoundaryMock);
    }

    @Test
    void execute_successfulAdd() {
        // Arrange
        RecipeInputData inputData = new RecipeInputData(
                "Pasta",
                Collections.singletonList(new Ingredient("Tomato", "2")),
                "Boil pasta. Add sauce.",
                "testUser",
                "local"
        );

        // Act
        interactor.execute(inputData);

        // Assert
        verify(recipeDataAccessMock, times(1)).saveRecipeForUser(any(CommonRecipe.class));
        verify(outputBoundaryMock, times(1)).presentSuccess(new RecipeOutputData("Recipe added successfully", true));
        verifyNoMoreInteractions(recipeDataAccessMock, outputBoundaryMock);
    }

    @Test
    void execute_failureToAddRecipe() {
        // Arrange
        RecipeInputData inputData = new RecipeInputData(
                "Pasta",
                Collections.singletonList(new Ingredient("Tomato", "2")),
                "Boil pasta. Add sauce.",
                "testUser",
                "local"
        );

        doThrow(new RuntimeException("Database error")).when(recipeDataAccessMock).saveRecipeForUser(any(CommonRecipe.class));

        // Act
        interactor.execute(inputData);

        // Assert
        verify(recipeDataAccessMock, times(1)).saveRecipeForUser(any(CommonRecipe.class));
        verify(outputBoundaryMock, times(1)).presentError("Failed to add recipe: Database error");
        verifyNoMoreInteractions(recipeDataAccessMock, outputBoundaryMock);
    }

    @Test
    void execute_nullRecipeName() {
        // Arrange
        RecipeInputData inputData = new RecipeInputData(
                null,
                Collections.singletonList(new Ingredient("Tomato", "2")),
                "Boil pasta. Add sauce.",
                "testUser",
                "local"
        );

        // Act
        interactor.execute(inputData);

        // Assert
        verify(outputBoundaryMock, times(1)).presentError("Failed to add recipe: Recipe name is required.");
        verifyNoInteractions(recipeDataAccessMock);
    }

    @Test
    void execute_emptyIngredients() {
        // Arrange
        RecipeInputData inputData = new RecipeInputData(
                "Pasta",
                Collections.emptyList(),
                "Boil pasta. Add sauce.",
                "testUser",
                "local"
        );

        // Act
        interactor.execute(inputData);

        // Assert
        verify(outputBoundaryMock, times(1)).presentError("Failed to add recipe: Ingredients are required.");
        verifyNoInteractions(recipeDataAccessMock);
    }
}
