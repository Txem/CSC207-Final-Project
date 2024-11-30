package use_case.present_by_tag;

import java.io.IOException;

/**
 * This interface defines the input boundary of the present by tag use case.
 */
public interface PresentByTagInputBoundary {

    /**
     * Executes the login use case.
     * @param PresentByTagInputData the input data
     * @throws IOException if an I/O error occurs
     */
    void execute(PresentByTagInputData PresentByTagInputData) throws IOException;
}
