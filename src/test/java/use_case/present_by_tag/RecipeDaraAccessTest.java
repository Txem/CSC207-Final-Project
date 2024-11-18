package use_case.present_by_tag;

import data_access.RecipeDataAccessObject;

import java.io.IOException;

public class RecipeDaraAccessTest {
    public static void main(String[] args) throws IOException {
        RecipeDataAccessObject recipeDataAccessobject = new RecipeDataAccessObject("C:\\Users\\BX\\Desktop\\CSC207-Final-Project\\recipe.json");

        System.out.println(recipeDataAccessobject.getAllRecipes());

        System.out.println(recipeDataAccessobject.getRecipesByTag("my favorite"));
    }
}
