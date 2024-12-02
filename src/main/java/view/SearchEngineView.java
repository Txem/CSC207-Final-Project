package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import data_access.ApiExploreDataAccessObject;
import data_access.SearchById;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.favorite.FavoriteController;
import interface_adapter.searchengine.SearchEngineController;
import interface_adapter.searchengine.SearchEnginePresenter;
import interface_adapter.searchengine.SearchEngineState;
import interface_adapter.searchengine.SearchEngineViewModel;
import entity.CommonRecipe;
import use_case.favorite.FavoriteInputBoundary;
import use_case.favorite.FavoriteInteractor;
import use_case.search.SearchEngineInteractor;

/**
 * The View for the Search Engine Use Case.
 */
public class SearchEngineView extends JFrame implements ActionListener, PropertyChangeListener {
    private final String viewName = "search engine";
    private final String username;
    private final SearchEngineViewModel searchViewModel;
    private final JTextField searchInputField = new JTextField(20);
    private final JButton searchButton;
    private final JPanel resultsPanel;
    private SearchEngineController searchController;
    private final SearchById recipeDataAccessObject = new SearchById();
    private Image backgroundImage;

    public SearchEngineView(SearchEngineViewModel searchViewModel, String username) {
        this.searchViewModel = searchViewModel;
        this.username = username;
        final ApiExploreDataAccessObject apiExploreDataAccessObject = new ApiExploreDataAccessObject();
        final SearchEnginePresenter searchEnginePresenter = new SearchEnginePresenter(new ViewManagerModel(), searchViewModel);
        final SearchEngineInteractor searchEngineInteractor = new SearchEngineInteractor(apiExploreDataAccessObject, searchEnginePresenter);
        backgroundImage = new ImageIcon("img/images.jpg").getImage();
        this.searchController = new SearchEngineController(searchEngineInteractor);
        searchViewModel.addPropertyChangeListener(this);

        setTitle("Search Engine");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchButton = new JButton("Search");
        searchBar.add(new JLabel("Search:"));
        searchBar.add(searchInputField);
        searchBar.add(searchButton);
        add(searchBar, BorderLayout.NORTH);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Results"));
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        System.out.println("Search button pressed");
                        final SearchEngineState currentState = searchViewModel.getState();
                        searchController.execute(searchInputField.getText());
                    }
                }
        );
    }

    private void addSearchInputListener() {
        searchInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SearchEngineState currentState = searchViewModel.getState();
                currentState.setKeyword(searchInputField.getText());
                searchViewModel.setState(currentState);
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Action not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt);
        System.out.println("exe");
        updateResults(searchViewModel.getState().getRecipes());
        System.out.println("exe1");
        if (searchViewModel.getState().getSearchError() != null) {
            JOptionPane.showMessageDialog(this, searchViewModel.getState().getSearchError());
        }
    }

    private void updateResults(List<CommonRecipe> recipes) {
        System.out.println("updating");

        resultsPanel.removeAll();
        if (recipes == null || recipes.isEmpty()) {
            resultsPanel.add(new JLabel("No results found."));
        } else {
            for (CommonRecipe recipe : recipes) {
                System.out.println("this is a recipe");
                resultsPanel.add(createRecipeCard(recipe));
            }
        }
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

//    private JPanel createRecipeCard(CommonRecipe recipe) {
//        JPanel card = new JPanel();
//        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
//        card.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(Color.gray, 1),
//                BorderFactory.createEmptyBorder(10, 10, 10, 10)
//        ));
//        card.setBackground(Color.white);
//
//        // Recipe name
//        JLabel nameLabel = new JLabel("Name: " + recipe.getName());
//        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
//        card.add(nameLabel);
//
//        // Ingredients
//        JLabel ingredientsLabel = new JLabel("Ingredients:");
//        ingredientsLabel.setFont(new Font("Arial", Font.ITALIC, 12));
//        card.add(ingredientsLabel);
//
//        for (var ingredient : recipe.getIngredients()) {
//            JLabel ingredientLabel = new JLabel("- " + ingredient.getIngredientName() + " (" + ingredient.getQuantity() + ")");
//            ingredientLabel.setFont(new Font("Arial", Font.PLAIN, 12));
//            card.add(ingredientLabel);
//        }
//
//        // Instructions
//        JLabel instructionLabel = new JLabel("Instruction: " + recipe.getInstructions());
//        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
//        card.add(instructionLabel);
//
//        // Button to download recipe
//        JButton favoriteButton = new JButton("Download");
//        favoriteButton.setAlignmentX(Component.RIGHT_ALIGNMENT); // Align button to the right
//        favoriteButton.addActionListener(
//                evt -> {
//                    String recipeId = recipe.getRecipeId();
//                    System.out.println("Downloading recipe: " + recipeId);
//                    final FavoriteInputBoundary favoriteInteractor = new FavoriteInteractor(recipeDataAccessObject);
//                    final FavoriteController favoriteController = new FavoriteController(favoriteInteractor);
//                    favoriteController.execute(recipeId, username); // Replace "user2" with actual username if dynamic
//                }
//        );
//        card.add(favoriteButton);
//
//        return card;
//    }
private JPanel createRecipeCard(CommonRecipe recipe) {
    JPanel card = new JPanel();
    card.setLayout(new BorderLayout(10, 10)); // 水平布局，间距为10
    card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.gray, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));
    card.setBackground(Color.white);

    // 固定URL的照片
    String imageUrl = "https://www.edamam.com/food-img/ecb/ecb3f5aaed96d0188c21b8369be07765.jpg"; // 替换为实际图片URL
    JLabel imageLabel;
    try {
        ImageIcon imageIcon = new ImageIcon(new URL(imageUrl));
        Image scaledImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // 调整图片大小
        imageLabel = new JLabel(new ImageIcon(scaledImage));
    } catch (Exception e) {
        imageLabel = new JLabel("Image not available");
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(100, 100)); // 设置固定占位
    }
    card.add(imageLabel, BorderLayout.WEST);

    // 内容部分
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBackground(Color.white);

    // Recipe name
    JLabel nameLabel = new JLabel("Name: " + recipe.getName());
    nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
    contentPanel.add(nameLabel);

    // Ingredients
    JLabel ingredientsLabel = new JLabel("Ingredients:");
    ingredientsLabel.setFont(new Font("Arial", Font.ITALIC, 12));
    contentPanel.add(ingredientsLabel);

    for (var ingredient : recipe.getIngredients()) {
        JLabel ingredientLabel = new JLabel("- " + ingredient.getIngredientName() + " (" + ingredient.getQuantity() + ")");
        ingredientLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        contentPanel.add(ingredientLabel);
    }

    // Instructions
    JLabel instructionLabel = new JLabel("Instruction: " + recipe.getInstructions());
    instructionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    contentPanel.add(instructionLabel);

    // Button to download recipe
    JButton favoriteButton = new JButton("Download");
    favoriteButton.setAlignmentX(Component.RIGHT_ALIGNMENT); // Align button to the right
    favoriteButton.addActionListener(
            evt -> {
                String recipeId = recipe.getRecipeId();
                System.out.println("Downloading recipe: " + recipeId);
                final FavoriteInputBoundary favoriteInteractor = new FavoriteInteractor(recipeDataAccessObject);
                final FavoriteController favoriteController = new FavoriteController(favoriteInteractor);
                favoriteController.execute(recipeId, username); // Replace "user2" with actual username if dynamic
            }
    );
    contentPanel.add(favoriteButton);

    // 添加内容部分到卡片右边
    card.add(contentPanel, BorderLayout.CENTER);

    return card;
}


    public String getViewName() {
        return viewName;
    }

    public void setSearchController(SearchEngineController controller) {
        this.searchController = controller;
    }
}