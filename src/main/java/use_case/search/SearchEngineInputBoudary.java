package use_case.search;

/**
 *  Input boundary for user to type in.
 */
public interface SearchEngineInputBoudary {
    /**
     * Executes the search use case.
     * @param  searchEngineInputData the input data
     */
    void execute(SearchEngineInputData searchEngineInputData);
}
