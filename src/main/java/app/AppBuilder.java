package app;

import java.awt.CardLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.*;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.favorite.FavoriteController;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.present_by_tag.PresentByTagController;
import interface_adapter.present_by_tag.PresentByTagController;
import interface_adapter.present_by_tag.PresentByTagPresenter;
import interface_adapter.present_by_tag.PresentByTagPresenter;
import interface_adapter.present_by_tag.PresentByTagViewModel;
import interface_adapter.present_by_tag.PresentByTagViewModel;
import interface_adapter.searchengine.SearchEngineController;
import interface_adapter.searchengine.SearchEnginePresenter;
import interface_adapter.searchengine.SearchEngineState;
import interface_adapter.searchengine.SearchEngineViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.favorite.FavoriteInputBoundary;
import use_case.favorite.FavoriteInteractor;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.present_by_tag.PresentByTagDataAccessInterface;
import use_case.present_by_tag.PresentByTagDataAccessInterface;
import use_case.present_by_tag.PresentByTagInputBoundary;
import use_case.present_by_tag.PresentByTagInteractor;
import use_case.present_by_tag.PresentByTagOutputBoundary;
import use_case.search.SearchEngineInputBoundary;
import use_case.search.SearchEngineInteractor;
import use_case.search.SearchEngineOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    private FileUserDataAccessObject userDataAccessObject = null;
    private final SearchById recipeDataAccessObject = new SearchById();

    private final PresentByTagDataAccessInterface presentByTagDataAccessObject =
            new RecipeDataAccessObject("recipe.json");

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private SearchEngineViewModel searchEngineViewModel;
    private SearchEngineView searchEngineView;
    private PresentByTagViewModel presentByTagViewModel;
    private PresentByTagView presentByTagView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        try {
            userDataAccessObject = new FileUserDataAccessObject("user.csv", userFactory);
            System.out.println("FileUserDataAccessObject created successfully!");
        }
        catch (IOException e) {
            // Handle the exception (e.g., log it or show an error message)
            System.err.println("Error creating FileUserDataAccessObject: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Change Password Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        final ChangePasswordController changePasswordController =
                new ChangePasswordController(changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds PresentByTag Use Case to the application.
     */
    public AppBuilder addPresentByTagUseCase() {
        presentByTagViewModel = new PresentByTagViewModel();
        final PresentByTagOutputBoundary presentByTagPresnter = new PresentByTagPresenter(presentByTagViewModel);
        final PresentByTagInputBoundary presentByTagInteractor = new PresentByTagInteractor(
                presentByTagDataAccessObject, presentByTagPresnter);
        final PresentByTagController presentByTagController = new PresentByTagController(presentByTagInteractor);
        presentByTagView = new PresentByTagView(presentByTagController);
        loggedInView.setPresentByTagController(presentByTagController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Adds the favorite Use Case to the application.
     */
    public AppBuilder addFavoriteUseCase() {
        final FavoriteInputBoundary favoriteInteractor = new FavoriteInteractor(recipeDataAccessObject);

        final FavoriteController favoriteController = new FavoriteController(favoriteInteractor);
        loggedInView.setFavoriteController(favoriteController);

        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("MyRecipe");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);
        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }

}
