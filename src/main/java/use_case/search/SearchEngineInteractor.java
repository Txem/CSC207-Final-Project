package use_case.search;

import java.util.List;

import entity.CommonRecipe;
import entity.Recipe;

/**
 * The Search Interactor.
 */
public class SearchEngineInteractor implements SearchEngineInputBoundary {
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
        userDataAccessObject.setCurrentKeyword(keyword);
        userDataAccessObject.getRecipeList(keyword);
//        if (userDataAccessObject.existByKeyword(keyword) == null) {
//            final SearchEngineOutputData searchEngineOutputData = new SearchEngineOutputData(null,
//                    true, keyword);
//            searchEnginePresenter.prepareSuccessView(searchEngineOutputData);
//        }
        if (!userDataAccessObject.existByKeyword(keyword)) {
            searchEnginePresenter.prepareErrorView(keyword + " can't be found in current database ");
        }
        else {
            final List<CommonRecipe> recipes = userDataAccessObject.getRecipeList(keyword);
            userDataAccessObject.setCurrentKeyword(keyword);
            final SearchEngineOutputData searchEngineOutputData = new SearchEngineOutputData(recipes,
                    true, keyword);
            searchEnginePresenter.prepareSuccessView(searchEngineOutputData);
        }
    }
}

