package interface_adapter.favorite;

import use_case.favorite.FavoriteInputBoundary;
import use_case.favorite.FavoriteInputData;

/**
 * the controller for the favorite use case.
 */
public class FavoriteController {

    private final FavoriteInputBoundary favoriteUseCaseInteractor;

    public FavoriteController(FavoriteInputBoundary favoriteUseCaseInteractor) {
        this.favoriteUseCaseInteractor = favoriteUseCaseInteractor;
    }

    /**
     * Executes the FavoriteUseCase.
     * @param id the id of the recipe
     * @param username the username
     */
    public void execute(String id, String username) {
        final FavoriteInputData favoriteInputData = new FavoriteInputData(id, username);

        favoriteUseCaseInteractor.execute(favoriteInputData);
    }
}
