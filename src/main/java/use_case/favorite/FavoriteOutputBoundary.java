package use_case.favorite;

/**
 * The output boundary for the Favorite Use Case.
 */
public interface FavoriteOutputBoundary {
    /**
     * Prepares the success view for the Favorite Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(FavoriteOutputData outputData);

    /**
     * Prepares the failure view for the Favorite Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
