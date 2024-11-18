package entity;

import java.util.List;

public interface Recipe {

    String getName();

    List<Ingredient> getIngredients();

    String getInstructions();

    User getUser();
}
