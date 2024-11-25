package use_case.favorite;

public class FavoriteInteractor {
    private final FavoriteUserDataAccessInterface userDataAccessInterface;
    private final FavoriteOutputBoundary favoritePresenter;

    public FavoriteInteractor(FavoriteUserDataAccessInterface userDataAccessInterface,
                              FavoriteOutputBoundary favoriteOutputBoundary) {
        this.userDataAccessInterface = userDataAccessInterface;
        this.favoritePresenter = favoriteOutputBoundary;
    }

    @Override
    public void execute(FavoriteInputData) {

    }
}
