package use_case.search;

/**
 *  Input boundary for user to type in.
 */
public interface SearchEngineInputBoundary {
    /**
     * Executes the search use case.
     * @param  searchEngineInputData the input data
     */
    void execute(SearchEngineInputData searchEngineInputData);
}
