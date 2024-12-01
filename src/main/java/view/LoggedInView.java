package view;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import data_access.ApiExploreDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_recipe.AddRecipeController;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.favorite.FavoriteController;
import interface_adapter.logout.LogoutController;
import interface_adapter.present_by_tag.PresentByTagController;
import interface_adapter.searchengine.SearchEngineController;
import interface_adapter.searchengine.SearchEnginePresenter;
import interface_adapter.searchengine.SearchEngineViewModel;
import use_case.search.SearchEngineInteractor;

/**
 * The View for when the user is logged into the program.
 */
public class LoggedInView extends JPanel implements PropertyChangeListener {

    private final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    private final JLabel passwordErrorField = new JLabel();
    private ChangePasswordController changePasswordController;
    private LogoutController logoutController;
    private AddRecipeController addRecipeController;
    private SearchEngineViewModel searchEngineViewModel;
    private SearchEngineController searchEngineController;
    private FavoriteController favoriteController;
    private PresentByTagController presentByTagController;

    private final JLabel username;

    private final JButton logOut;

    private final JTextField passwordInputField = new JTextField(15);
    private final JButton changePassword;
    private final JButton searchEngine;
    private final JButton addRecipe;
    private final JButton presentByTag;

    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;

        this.loggedInViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Logged In Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel("Password"), passwordInputField);

        final JLabel usernameInfo = new JLabel("Currently logged in: ");
        username = new JLabel();

        final JPanel buttons = new JPanel();
        logOut = new JButton("Log Out");
        buttons.add(logOut);

        addRecipe = new JButton("Add Recipe");
        buttons.add(addRecipe);

        searchEngine = new JButton("explore");
        buttons.add(searchEngine);

        changePassword = new JButton("Change Password");
        buttons.add(changePassword);

        presentByTag = new JButton("Present By Tag");
        buttons.add(presentByTag);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoggedInState currentState = loggedInViewModel.getState();
                currentState.setPassword(passwordInputField.getText());
                loggedInViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        changePassword.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(changePassword)) {
                        final LoggedInState currentState = loggedInViewModel.getState();

                        this.changePasswordController.execute(
                                currentState.getUsername(),
                                currentState.getPassword()
                        );
                    }
                }
        );

        logOut.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(logOut)) {
                        // 1. get the state out of the loggedInViewModel. It contains the username.
                        // 2. Execute the logout Controller.
                        loggedInViewModel.firePropertyChanged();
                        logoutController.execute(loggedInViewModel.getViewName());
                    }
                }
        );

        searchEngine.addActionListener(
                evt -> {
                    if (evt.getSource().equals(searchEngine)) {
                        searchEngineViewModel = new SearchEngineViewModel();
                        final LoggedInState currentState = loggedInViewModel.getState();
                        final String username = currentState.getUsername();
                        final SearchEngineView searchEngineView = new SearchEngineView(searchEngineViewModel, username);
                        searchEngineView.setVisible(true);
                    }
                }
        );

        addRecipe.addActionListener(
                evt -> {
                    if (evt.getSource().equals(addRecipe)) {
                        AddRecipeView addRecipeView = new AddRecipeView(addRecipeController);
                        addRecipeView.setVisible(true);
                    }
                }
        );

        presentByTag.addActionListener(
                evt -> {
                    if (evt.getSource().equals(presentByTag)) {
                        final PresentByTagView presentByTagView = new PresentByTagView(presentByTagController);
                        presentByTagView.setVisible(true);
                    }
                }
        );

        this.add(title);
        this.add(usernameInfo);
        this.add(username);

        this.add(passwordInfo);
        this.add(passwordErrorField);
        this.add(buttons);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            username.setText(state.getUsername());
        }
        else if (evt.getPropertyName().equals("password")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, "password updated for " + state.getUsername());
        }
        else if (evt.getPropertyName().equals("error")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, "Please set password to non-empty string");
        }

    }

    public String getViewName() {
        return viewName;
    }

    public void setChangePasswordController(ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setAddRecipeController(AddRecipeController addRecipeController) {
        this.addRecipeController = addRecipeController;
    }

    public void setFavoriteController(FavoriteController favoritecontroller) {
        this.favoriteController = favoritecontroller;
    }

    public void setSearchEngineController(SearchEngineController searchEngineController) {
        this.searchEngineController = searchEngineController;
    }

    public void setPresentByTagController(PresentByTagController presentByTagController) {
        this.presentByTagController = presentByTagController;
    }
}
