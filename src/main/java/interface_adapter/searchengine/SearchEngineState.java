package interface_adapter.searchengine;

/**
 * The search state for the search Engine.
 */
public class SearchEngineState {
    private String keyword;
    private String searchError;

    public String getKeyword() {
        return keyword;
    }

    public String getSearchError() {
        return searchError;
    }

    public void setSearchError(String searchError) {
        this.searchError = searchError;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
