package interface_adapter.present_by_tag;

/**
 * PresentByTagState is the state of the PresentByTagView.
 */
public class PresentByTagState {
    private String tag;
    private String tagError;

    public String getTag() {
        return tag;
    }

    public String getTagError() {
        return tagError;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setTagError(String tagError) {
        this.tagError = tagError;
    }
}
