package use_case.favorite;

import entity.CommonRecipe;

/**
 * Output Data for the Favorite Use Case.
 */
public class FavoriteOutputData {

    private final CommonRecipe recipe;

    public FavoriteOutputData(CommonRecipe recipe, boolean useCaseFailed) {

        this.recipe = recipe;
    }

    public CommonRecipe getRecipe() {
        return recipe;
    }
}
