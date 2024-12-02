package use_case.present_by_tag;

import java.io.IOException;
import java.util.List;

import entity.CommonRecipe;

/**
 * This interface defines the data access methods for the present_by_tag use case.
 */
public interface PresentByTagDataAccessInterface {

    /**
     * Checks if the given tag exists.
     * @param tag the tag to look for
     * @return true if a tag exists; false otherwise
     * @throws IOException if an error occurs while accessing the data source
     */
    boolean exist(String tag) throws IOException;

    /**
     * Gets all presentations with the given tag.
     * @param tag the tag to look for
     * @return a list of presentations with the given tag
     * @throws IOException if an error occurs while accessing the data source
     */
    List<CommonRecipe> getRecipesByTag(String tag) throws IOException;

    List<CommonRecipe> getAllRecipes()throws IOException;
}
