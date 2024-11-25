package use_case.favorite;

/**
 * Input Boundary for actions which are related to favorite.
 */
public interface FavoriteInputBoundary {

    /**
     * Executes the favorite use case.
     * @param favoriteInputData the input data
     */
    void execute(FavoriteInputData favoriteInputData);
}
