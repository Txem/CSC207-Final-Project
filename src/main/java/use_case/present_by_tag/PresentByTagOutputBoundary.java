package use_case.present_by_tag;

/**
 * The output boundary interface for the presenting by tag use case.
 */
public interface PresentByTagOutputBoundary {

    /**
     * Prepares the success view for the presenting by tag Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(PresentByTagOutputData outputData);

    /**
     * Prepares the failure view for the presenting by tag Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
