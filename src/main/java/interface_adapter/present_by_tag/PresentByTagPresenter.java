package interface_adapter.present_by_tag;

import use_case.present_by_tag.PresentByTagOutputBoundary;
import use_case.present_by_tag.PresentByTagOutputData;

/**
 * The Presenter for the presenting by tag Use Case.
 */
public class PresentByTagPresenter implements PresentByTagOutputBoundary {

    private final PresentByTagViewModel presentByTagViewModel;
    /**
     * Prepares the success view for the presenting by tag Use Case.
     * @param presentByTagViewModel the view model for the presenting by tag Use Case.
     */

    public PresentByTagPresenter(PresentByTagViewModel presentByTagViewModel) {
        this.presentByTagViewModel = presentByTagViewModel;
    }

    @Override
    public void prepareSuccessView(PresentByTagOutputData response) {
        // todo
    }

    /**
     * Prepares the failure view for the presenting by tag Use Case.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final PresentByTagState state = presentByTagViewModel.getState();
        state.setTagError(errorMessage);
        presentByTagViewModel.updateState(state);

    }
}
