package use_case.search;

import entity.User;

/**
 * Interface for search engine.
 */
public class SearchEngineUserDataAccessInterface {

    /**
     * Returns the keyword of the curren keyword of searchEngine.
     * @return the keyword of current search.
     */
    String getCurrentKeyWord();

    /**
     * Sets the username indicating who is the current user of the application.
     * @param username the new current username
     */
    void setCurrentUsername(String username);
}
