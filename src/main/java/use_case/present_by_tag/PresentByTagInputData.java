package use_case.present_by_tag;

/**
 * Represents input data for presenting by tag.
 */
public class PresentByTagInputData {
    private String tag;

    public PresentByTagInputData(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
