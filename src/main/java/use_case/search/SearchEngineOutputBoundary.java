package use_case.search;

/**
 * Search Engines output boundary.
 */
public interface SearchEngineOutputBoundary {
    /**
     * Prepare if we got something from api.
     * @param searchEngineOutputData what we got from api search.
     */
    void prepareSuccessView(SearchEngineOutputData searchEngineOutputData);

    /**
     * Prepare if we got nothing from api.
     * @param errorMessage is the error.
     */
    void prepareErrorView(String errorMessage);
}
