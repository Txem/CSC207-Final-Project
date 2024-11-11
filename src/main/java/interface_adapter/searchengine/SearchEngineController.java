package interface_adapter.searchengine;

import use_case.search.SearchEngineInputBoundary;
import use_case.search.SearchEngineInputData;

/**
 * SearchEngineController for control the input.
 */
public class SearchEngineController {
    private final SearchEngineInputBoundary searchEngineInputBoundary;

    public SearchEngineController(SearchEngineInputBoundary searchEngineInputBoundary) {
        this.searchEngineInputBoundary = searchEngineInputBoundary;
    }

    /**
     * Execute the interactor.
     * @param keyword is the keyword user type in.
     */
    public void execute(String keyword) {
        final SearchEngineInputData searchEngineInputData = new SearchEngineInputData(keyword);
        searchEngineInputBoundary.execute(searchEngineInputData);
    }
}
