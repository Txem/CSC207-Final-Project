package use_case.present_by_tag;

import entity.CommonRecipe;

import java.io.IOException;
import java.util.List;

/**
 * This interface defines the input boundary of the present by tag use case.
 */
public interface PresentByTagInputBoundary {

    /**
     * Executes the login use case.
     *
     * @param PresentByTagInputData the input data
     * @return
     * @throws IOException if an I/O error occurs
     */
    List<CommonRecipe> execute(PresentByTagInputData PresentByTagInputData) throws IOException;
}
