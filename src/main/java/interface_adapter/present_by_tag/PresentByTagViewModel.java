package interface_adapter.present_by_tag;

import interface_adapter.ViewModel;

/**
 * This class is the view model of the present by tag screen.
 */
public class PresentByTagViewModel extends ViewModel<PresentByTagState> {
    public PresentByTagViewModel() {
        super("Presented by Tag");
        setState(new PresentByTagState());
    }
}

