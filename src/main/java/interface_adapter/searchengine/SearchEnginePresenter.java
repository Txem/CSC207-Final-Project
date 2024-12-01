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
        System.out.println(this.searchEngineViewModel);

    }

    @Override
    public void prepareSuccessView(SearchEngineOutputData searchEngineOutputData) {
        System.out.println("we got some answer");
        final SearchEngineState searchEngineState = searchEngineViewModel.getState();
        searchEngineState.setKeyword(searchEngineOutputData.getKeyword());
        searchEngineState.setRecipes(searchEngineOutputData.getRecipes());
        searchEngineViewModel.firePropertyChanged("state");
    }

    @Override
    public void prepareErrorView(String errorMessage) {
        System.out.println("we got some error");
        final SearchEngineState searchEngineState = searchEngineViewModel.getState();
        searchEngineState.setSearchError(errorMessage);
        searchEngineViewModel.firePropertyChanged("state");
    }

}
