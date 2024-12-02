package use_case.favorite;


public interface FavoriteUserDataAccessInterface {

    /**
     * Return the recipe of that id.
     * @param recipeId the recipe the user chose to favorite.
     * @param username the username
     */
    void saveInFile(String recipeId, String username);
}
