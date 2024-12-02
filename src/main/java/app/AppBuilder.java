package app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

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
import interface_adapter.present_by_tag.PresentByTagPresenter;
import interface_adapter.present_by_tag.PresentByTagViewModel;
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
import use_case.present_by_tag.PresentByTagInputBoundary;
import use_case.present_by_tag.PresentByTagInteractor;
import use_case.present_by_tag.PresentByTagOutputBoundary;
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
        final JFrame application = new JFrame("Genshin Impact Recipe App");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Load the background image
        String imagePath = "img/image.jpg";
        BufferedImage backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load background image.");
        }

        // Get the image dimensions and set the frame size accordingly
        if (backgroundImage != null) {
            application.setSize(backgroundImage.getWidth(), backgroundImage.getHeight());
            System.out.println("use image size");
        }
        else {
            application.setSize(1024, 768); // Default size if image fails to load
        }

        application.setLocationRelativeTo(null); // Center the frame

        // Create the main panel with the background
        BackgroundPanel mainPanel = new BackgroundPanel(imagePath);
        mainPanel.setLayout(new BorderLayout());

//        // Add the title panel
//        JPanel titlePanel = new JPanel(new BorderLayout());
//        titlePanel.setOpaque(false); // Transparent panel
//        JLabel titleLabel = new JLabel("Welcome to Genshin Impact Recipe App", JLabel.CENTER);
//        titleLabel.setFont(new Font("Serif", Font.BOLD, 48)); // Larger font size
//        titleLabel.setForeground(Color.BLACK); // Change font color to black
//        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//        titlePanel.add(titleLabel, BorderLayout.CENTER);
//        titlePanel.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent black background
//        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Add the card panel (transparent)
        cardPanel.setOpaque(false);
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        application.setContentPane(mainPanel);

        // Initialize view state
        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;

    }

}
