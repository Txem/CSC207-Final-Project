package interface_adapter.searchengine;

import interface_adapter.ViewManagerModel;
import use_case.search.SearchEngineOutputBoundary;
import use_case.search.SearchEngineOutputData;

/**
 * Display two situation of output of search.
 */
public class SearchEnginePresenter implements SearchEngineOutputBoundary {

    private final SearchEngineViewModel searchEngineViewModel;
    private final ViewManagerModel viewManagerModel;

    public SearchEnginePresenter(ViewManagerModel viewManagerModel,
                          SearchEngineViewModel searchEngineViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.searchEngineViewModel = searchEngineViewModel;
    }

    @Override
    public void prepareSuccessView(SearchEngineOutputData searchEngineOutputData) {
        final SearchEngineState searchEngineState = searchEngineViewModel.getState();
        searchEngineState.setKeyword(searchEngineOutputData.getKeyword());
        searchEngineViewModel.firePropertyChanged();
    }

    @Override
    public void prepareErrorView(String errorMessage) {
        final SearchEngineState searchEngineState = searchEngineViewModel.getState();
        searchEngineState.setSearchError(errorMessage);
        searchEngineViewModel.firePropertyChanged();
    }

}
