package use_case.present_by_tag;

import java.io.IOException;
import java.util.List;

import entity.CommonRecipe;

/**
 * This class represents the interactor of the present by tag use case.
 */
public class PresentByTagInteractor implements PresentByTagInputBoundary {
    private PresentByTagOutputBoundary outputPresenter;
    private PresentByTagDataAccessInterface presentByTagDataAccessObject;

    public PresentByTagInteractor(PresentByTagDataAccessInterface presentByTagDataAccessObject,
                                  PresentByTagOutputBoundary presentByTagOutputBoundary) {
        this.presentByTagDataAccessObject = presentByTagDataAccessObject;
        this.outputPresenter = presentByTagOutputBoundary;
    }

    /**
     * Executes the login use case.
     *
     * @param PresentByTagInputData the input data
     */
    @Override
    public void execute(PresentByTagInputData PresentByTagInputData) throws IOException {
        final String tag = PresentByTagInputData.getTag();
        if (!presentByTagDataAccessObject.exist(tag)) {
            outputPresenter.prepareFailView("Tag not found");
        }
        else {
            final List<CommonRecipe> recipes = presentByTagDataAccessObject.getRecipesByTag(tag);
            final PresentByTagOutputData presentByTagOutputData = new PresentByTagOutputData(tag, recipes, true);
            outputPresenter.prepareSuccessView(presentByTagOutputData);
        }
    }
}
