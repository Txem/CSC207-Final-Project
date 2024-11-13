package interface_adapter.searchengine;

import interface_adapter.ViewModel;

/**
 * This init the view model of search engine.
 */
public class SearchEngineViewModel extends ViewModel<SearchEngineState> {
    public SearchEngineViewModel() {
        super("explore recipe");
        setState(new SearchEngineState());
    }
}
