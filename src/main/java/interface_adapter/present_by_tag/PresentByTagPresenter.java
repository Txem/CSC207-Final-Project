package interface_adapter.present_by_tag;

import interface_adapter.ViewManagerModel;
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
        final PresentByTagState presentByTagState = presentByTagViewModel.getState();
        presentByTagState.setTag(response.getTag());
        presentByTagViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the presenting by tag Use Case.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final PresentByTagState presentByTagState = presentByTagViewModel.getState();
        presentByTagState.setTagError(errorMessage);
        presentByTagViewModel.firePropertyChanged();
    }
}
