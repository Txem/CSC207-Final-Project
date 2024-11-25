package use_case.favorite;

/**
 * Input Data for favorite.
 */
public class FavoriteInputData {

    private final String url;

    public FavoriteInputData(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
