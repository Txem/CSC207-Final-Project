package use_case.search;

import java.util.List;

import entity.Recipe;

/**
 * The Search Interactor.
 */
public class SearchEngineInteractor implements SearchEngineInputBoudary {
    private final SearchEngineUserDataAccessInterface userDataAccessObject;
    private final SearchEngineOutputBoundary searchEnginePresenter;

    public SearchEngineInteractor(SearchEngineUserDataAccessInterface userDataAccessInterface,
                           SearchEngineOutputBoundary searchEngineOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.searchEnginePresenter = searchEngineOutputBoundary;
    }

    @Override
    public void execute(SearchEngineInputData searchEngineInputData) {
        final String keyword = searchEngineInputData.getKeyword();
        if (!userDataAccessObject.existByKeyword(keyword)) {
            searchEnginePresenter.prepareErrorView(keyword + " can't be found in current database ");
        }
        else {
            final List<Recipe> recipes = userDataAccessObject.getRecipeList(keyword);
            userDataAccessObject.setCurrentKeyword(keyword);
            final SearchEngineOutputData searchEngineOutputData = new SearchEngineOutputData(recipes, true);
            searchEnginePresenter.prepareSuccessView(searchEngineOutputData);
        }
    }
}

