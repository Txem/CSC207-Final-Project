package use_case.present_by_tag;

import entity.CommonRecipe;

import java.util.List;

public class PresentByTagOutputData {
    private String tag;
    private List<CommonRecipe> recipes;
    private final Boolean success;

    public PresentByTagOutputData(String tag, List<CommonRecipe> recipes, Boolean success) {
        this.tag = tag;
        this.recipes = recipes;
        this.success = success;
    }

    public String getTag() {
        return tag;
    }

    public List<CommonRecipe> getRecipes() {
        return recipes;
    }

    public Boolean getSuccess() {
        return success;

    }
}
