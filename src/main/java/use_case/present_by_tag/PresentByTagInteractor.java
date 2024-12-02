package use_case.present_by_tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.CommonRecipe;

/**
 * This class represents the interactor of the present by tag use case.
 */
public class PresentByTagInteractor implements PresentByTagInputBoundary {
    private PresentByTagOutputBoundary presentByTagPresenter;
    private PresentByTagDataAccessInterface presentByTagDataAccessObject;

    public PresentByTagInteractor(PresentByTagDataAccessInterface presentByTagDataAccessObject,
                                  PresentByTagOutputBoundary presentByTagOutputBoundary) {
        this.presentByTagDataAccessObject = presentByTagDataAccessObject;
        this.presentByTagPresenter = presentByTagOutputBoundary;
    }

    /**
     * Executes the login use case.
     *
     * @param PresentByTagInputData the input data
     * @return
     */
    @Override
    public List<CommonRecipe> execute(PresentByTagInputData PresentByTagInputData) throws IOException {
        final String tag = PresentByTagInputData.getTag();
        if (!presentByTagDataAccessObject.exist(tag)) {
            presentByTagPresenter.prepareFailView("Tag not found");
            final List<CommonRecipe> recipes = new ArrayList<>();
            return recipes;
        }
        else {
            final List<CommonRecipe> recipes = presentByTagDataAccessObject.getRecipesByTag(tag);
            final PresentByTagOutputData presentByTagOutputData = new PresentByTagOutputData(tag, recipes, true);
            presentByTagPresenter.prepareSuccessView(presentByTagOutputData);
            return recipes;
        }
    }

    @Override
    public List<CommonRecipe> getAllRecipes() throws IOException {
        return presentByTagDataAccessObject.getAllRecipes();
    }
}
