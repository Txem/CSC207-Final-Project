package use_case.favorite;

/**
 * Input Data for favorite.
 */
public class FavoriteInputData {

    private final String id;
    private final String username;

    public FavoriteInputData(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
