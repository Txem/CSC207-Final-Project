package interface_adapter.add_recipe;

import interface_adapter.ViewModel;

public class AddRecipeViewModel extends ViewModel<AddRecipeState> {
    public AddRecipeViewModel() {
        super("add_recipe");
        this.setState(new AddRecipeState());
    }

    public void setMessage(String message) {
        AddRecipeState state = this.getState();
        state.setMessage(message);
        this.firePropertyChanged("message");
    }

    public void setSuccess(boolean success) {
        AddRecipeState state = this.getState();
        state.setSuccess(success);
        this.firePropertyChanged("success");
    }

    public String getMessage() {
        return this.getState().getMessage();
    }

    public boolean isSuccess() {
        return this.getState().isSuccess();
    }
}
