package use_case.search;

/**
 * Input Data for search Engine.
 */
public class SearchEngineInputData {

    private final String keyword;

    public SearchEngineInputData(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
