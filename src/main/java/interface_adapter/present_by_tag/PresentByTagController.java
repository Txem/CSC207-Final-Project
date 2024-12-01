package interface_adapter.present_by_tag;

import entity.CommonRecipe;
import entity.Recipe;
import use_case.present_by_tag.PresentByTagInputBoundary;
import use_case.present_by_tag.PresentByTagInputData;

import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for handling the presentation of presentations by tag.
 */
public class PresentByTagController {
    private final PresentByTagInputBoundary presentByTagInteractor;

    public PresentByTagController(PresentByTagInputBoundary presentByTagInteractor) {
        this.presentByTagInteractor = presentByTagInteractor;
    }

    /**
     * This method is responsible for handling the presentation of presentations by tag.
     * @param tag The tag to search for.
     * @throws IOException If there is an error in reading or writing files.
     */
    public List<CommonRecipe> execute(String tag) throws IOException {
        final PresentByTagInputData presentByTagInputData = new PresentByTagInputData(tag);

        return presentByTagInteractor.execute(presentByTagInputData);
    }
}
