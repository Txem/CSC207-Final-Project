package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import data_access.ApiExploreDataAccessObject;
import data_access.SearchById;
import entity.Recipe;
import interface_adapter.ViewManagerModel;
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
    private JPanel resultsPanel;
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
        ImagePanel backgroundPanel = new ImagePanel(new ImageIcon("img/image.jpg").getImage());
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        setContentPane(backgroundPanel);
        this.searchController = new SearchEngineController(searchEngineInteractor);
        searchViewModel.addPropertyChangeListener(this);
//        BackgroundPanel mainPanel = new BackgroundPanel("img/image.jpg");
        setTitle("Search Engine");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchBar.setOpaque(true);
        searchButton = new JButton("Search");
        searchBar.add(new JLabel("Search:"));
        searchBar.add(searchInputField);
        searchBar.add(searchButton);
        searchBar.setOpaque(false);
//        mainPanel.setOpaque(false);
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255)),
                "Recipes Found", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(255, 255, 255)));

        // 底部说明栏
        JLabel footerLabel = new JLabel("Tip: Click 'Search' to explore recipes and click download button to save your recipes.", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(Color.BLACK);
        footerLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        backgroundPanel.add(searchBar, BorderLayout.NORTH);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);
//        backgroundPanel.add(footerLabel, BorderLayout.SOUTH);
//        JScrollPane scrollPane = new JScrollPane(resultsPanel);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        scrollPane.setBorder(BorderFactory.createTitledBorder("Results"));
//        scrollPane.setPreferredSize(new Dimension(500, 300));
//        add(searchBar, BorderLayout.NORTH);
//        add(scrollPane, BorderLayout.CENTER);
//        scrollPane.setOpaque(false);
        addSearchInputListener();
        searchButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        resultsPanel.removeAll();
                        resultsPanel.revalidate();
                        resultsPanel.repaint();
                        searchViewModel.getState().setRecipes(new ArrayList<CommonRecipe>());
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
        if (searchViewModel.getState().getRecipes().size() != 0){
            updateResults(searchViewModel.getState().getRecipes());
            System.out.println("propertyChange" + searchViewModel.getState().getRecipes().size());
        }
        else {
            resultsPanel.removeAll();
            resultsPanel.revalidate();
            resultsPanel.repaint();
        }
        if (searchViewModel.getState().getSearchError() != null) {
            JOptionPane.showMessageDialog(this, searchViewModel.getState().getSearchError());
        }
    }

    private void updateResults(List<CommonRecipe> recipes) {
        System.out.println("updating");

        resultsPanel.removeAll();
        if (recipes == null || recipes.isEmpty()) {
            resultsPanel.add(new JLabel("No results found."));
        }
        else {
            resultsPanel.removeAll();
            resultsPanel.revalidate();
            resultsPanel.repaint();
            searchViewModel.getState().setRecipes(new ArrayList<CommonRecipe>());
            System.out.println(searchViewModel.getState().getRecipes().size());
            for (CommonRecipe recipe : recipes) {
                resultsPanel.add(createRecipeCard(recipe));
            }
            searchViewModel.getState().setRecipes(recipes);
        }
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private JPanel createRecipeCard(CommonRecipe recipe) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(255, 255, 255), 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(new Color(0, 0, 0, 150));
        card.setMaximumSize(new Dimension(750, 200));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(240, 240, 240));
        leftPanel.setPreferredSize(new Dimension(150, 300));

        JLabel imageLabel;
        String targetUrl = "https://www.example.com";

        try {
            String imageUrl = "https://www.edamam.com/food-img/ecb/ecb3f5aaed96d0188c21b8369be07765.jpg";
            ImageIcon imageIcon = new ImageIcon(new URL(imageUrl));
            Image scaledImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
            imageLabel = new JLabel("Image not available");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setPreferredSize(new Dimension(100, 100));
        }
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        leftPanel.add(imageLabel);

        leftPanel.add(Box.createVerticalStrut(20));

        JButton favoriteButton = new JButton("Download");
        favoriteButton.setMaximumSize(new Dimension(100, 40)); // 固定按钮大小
        favoriteButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        favoriteButton.addActionListener(evt -> {
            String recipeId = recipe.getRecipeId();
            System.out.println("Downloading recipe: " + recipeId);
            final FavoriteInputBoundary favoriteInteractor = new FavoriteInteractor(recipeDataAccessObject);
            final FavoriteController favoriteController = new FavoriteController(favoriteInteractor);
            favoriteController.execute(recipeId, username);
        });
        leftPanel.add(favoriteButton);

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    // 打开绑定的 URL
                    Desktop.getDesktop().browse(new URI(targetUrl));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // 垂直排列文字
        rightPanel.setBackground(new Color(240, 240, 240)); // 背景一致

// Recipe name
        JLabel nameLabel = new JLabel(recipe.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(new Color(50, 50, 50)); // 深灰色字体
        rightPanel.add(nameLabel);

// Ingredients
        JLabel ingredientsLabel = new JLabel("Ingredients:");
        ingredientsLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        rightPanel.add(ingredientsLabel);

        for (var ingredient : recipe.getIngredients()) {
            JLabel ingredientLabel = new JLabel("- " + ingredient.getIngredientName() + " (" + ingredient.getQuantity() + ")");
            ingredientLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            rightPanel.add(ingredientLabel);
        }

// Instructions
        JLabel instructionLabel = new JLabel("<html>Instruction: " + recipe.getInstructions() + "</html>");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        rightPanel.add(instructionLabel);

// 添加两个子面板到主面板
        card.add(leftPanel, BorderLayout.WEST); // 左侧放置图片和按钮
        card.add(rightPanel, BorderLayout.CENTER); // 右侧放置文字

        return card;
    }

    // 动态计算卡片高度
    private int calculateCardHeight(CommonRecipe recipe) {
        int baseHeight = 150; // 基础高度
        int ingredientsCount = recipe.getIngredients().size();
        int extraHeight = ingredientsCount * 20; // 每个配料增加的高度
        return baseHeight + extraHeight;
    }

    public String getViewName() {
        return viewName;
    }

    public void setSearchController(SearchEngineController controller) {
        this.searchController = controller;
    }
    static class ImagePanel extends JPanel {
        private final Image image;

        public ImagePanel(Image image) {
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}