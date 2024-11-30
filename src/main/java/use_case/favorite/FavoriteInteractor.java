package use_case.favorite;

import data_access.SearchById;
import entity.CommonRecipe;

/**
 * The favorite Interactor.
 */
public class FavoriteInteractor implements FavoriteInputBoundary {
    private final FavoriteUserDataAccessInterface userDataAccessInterface;

    public FavoriteInteractor(FavoriteUserDataAccessInterface userDataAccessInterface) {
        this.userDataAccessInterface = userDataAccessInterface;
    }

    @Override
    public void execute(FavoriteInputData favoriteInputData) {
        final String id = favoriteInputData.getId();
        final String username = favoriteInputData.getUsername();
        userDataAccessInterface.fetchAndWriteRecipeById(id, username);
    }
}
