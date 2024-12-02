//package view;
//
//import entity.CommonRecipe;
//import entity.Ingredient;
//import entity.Recipe;
//import interface_adapter.present_by_tag.PresentByTagController;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import javax.swing.text.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.io.IOException;
//import java.util.List;
//
///**
// * 按标签搜索菜谱界面.
// */
//public class PresentByTagView extends JFrame {
//    private JTextField tagInputField;
//    private JButton searchButton;
//    private JPanel resultPanel;
//    private final PresentByTagController presentByTagController;
//
//    public PresentByTagView(PresentByTagController presentByTagController) {
//        this.presentByTagController = presentByTagController;
//
//        // 设置 JFrame 的基本属性
//        setTitle("Recipe Search");
//        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // 设置背景图片
//        JLabel background = new JLabel(new ImageIcon("background.jpg"));
//        setContentPane(background);
//        setLayout(new BorderLayout(10, 10));
//
//        // 顶部输入区域
//        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
//        inputPanel.setBackground(new Color(255, 182, 193, 200)); // 半透明背景
//        JLabel label = new JLabel("Enter Tag:");
//        tagInputField = new JTextField(20);
//        searchButton = new JButton("Search");
//        searchButton.setBackground(new Color(100, 149, 237));
//        searchButton.setForeground(Color.WHITE);
//        searchButton.setFocusPainted(false);
//        inputPanel.add(label);
//        inputPanel.add(tagInputField);
//        inputPanel.add(searchButton);
//
//        // 结果显示区域
//        resultPanel = new JPanel();
//        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS)); // 垂直布局
//        resultPanel.setBackground(new Color(245, 245, 245, 200));
//        JScrollPane scrollPane = new JScrollPane(resultPanel);
//        scrollPane.setBorder(BorderFactory.createTitledBorder("Recipes Found"));
//
//        // 底部说明栏
//        JLabel footerLabel = new JLabel("Tip: Enter a tag and click 'Search' to find recipes.", JLabel.CENTER);
//        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
//        footerLabel.setForeground(Color.DARK_GRAY);
//        footerLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
//
//        // 添加组件到 JFrame
//        add(inputPanel, BorderLayout.NORTH);
//        add(scrollPane, BorderLayout.CENTER);
//        add(footerLabel, BorderLayout.SOUTH);
//
//        // 搜索按钮事件
//        searchButton.addActionListener((ActionEvent e) -> {
//            try {
//                searchRecipes();
//            } catch (IOException ex) {
//                JOptionPane.showMessageDialog(this, "Error occurred while searching: " + ex.getMessage(),
//                        "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//    }
//
//    private void searchRecipes() throws IOException {
//        String tag = tagInputField.getText().trim();
//        resultPanel.removeAll(); // 清空旧内容
//        if (!tag.isEmpty()) {
//            final List<CommonRecipe> recipes = this.presentByTagController.execute(tag);
//
//            if (recipes.isEmpty()) {
//                final JLabel noResultLabel = new JLabel("No recipes found for tag: " + tag, JLabel.CENTER);
//                noResultLabel.setForeground(Color.RED);
//                noResultLabel.setFont(new Font("Arial", Font.BOLD, 14));
//                resultPanel.add(noResultLabel);
//            } else {
//                for (Recipe recipe : recipes) {
//                    resultPanel.add(createRecipeCard(recipe));
//                }
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Tag cannot be empty!", "Warning", JOptionPane.WARNING_MESSAGE);
//        }
//        resultPanel.revalidate();
//        resultPanel.repaint();
//    }
//
//    private JPanel createRecipeCard(Recipe recipe) {
//        JPanel card = new JPanel();
//        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
//        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
//        card.setBackground(Color.WHITE);
//        card.setMaximumSize(new Dimension(750, 150));
//        card.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        JLabel title = new JLabel(recipe.getName(), JLabel.LEFT);
//        title.setFont(new Font("Arial", Font.BOLD, 16));
//        title.setForeground(Color.DARK_GRAY);
//
//        JTextArea description = new JTextArea(recipe.getUserName());
//        description.setWrapStyleWord(true);
//        description.setLineWrap(true);
//        description.setEditable(false);
//        description.setFont(new Font("Arial", Font.PLAIN, 12));
//        description.setBackground(Color.WHITE);
//        description.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
//
//        JLabel ingredientsLabel = new JLabel("Ingredients:");
//        ingredientsLabel.setFont(new Font("Arial", Font.BOLD, 14));
//        ingredientsLabel.setForeground(new Color(85, 107, 47));
//
//        JTextArea ingredients = new JTextArea();
//        ingredients.setWrapStyleWord(true);
//        ingredients.setLineWrap(true);
//        ingredients.setEditable(false);
//        ingredients.setFont(new Font("Arial", Font.PLAIN, 12));
//        ingredients.setBackground(Color.WHITE);
//        ingredients.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
//
//        List<Ingredient> ingredientList = recipe.getIngredients();
//        if (ingredientList != null) {
//            for (Ingredient ingredient : ingredientList) {
//                ingredients.append("- " + ingredient.getIngredientName() + "\n");
//            }
//        } else {
//            ingredients.append("No ingredients found.");
//        }
//
//        card.add(title);
//        card.add(description);
//        card.add(ingredientsLabel);
//        card.add(ingredients);
//
//        return card;
//    }
//}
package view;

import entity.CommonRecipe;
import entity.Ingredient;
import entity.Recipe;
import interface_adapter.present_by_tag.PresentByTagController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PresentByTagView extends JFrame {
    private JTextField tagInputField;
    private JButton searchButton;
    private JButton favoriteButton;
    private JPanel resultPanel;
    private final PresentByTagController presentByTagController;

    public PresentByTagView(PresentByTagController presentByTagController) {
        this.presentByTagController = presentByTagController;

        // 设置 JFrame 的基本属性
        setTitle("Recipe Search");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 设置背景图片面板
        ImagePanel backgroundPanel = new ImagePanel(new ImageIcon("background.gif").getImage());
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        setContentPane(backgroundPanel);

        // 顶部输入区域
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        inputPanel.setOpaque(false);
        JLabel label = new JLabel("Enter Tag:");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(200, 200, 200)); // 使用亮灰色
        tagInputField = new JTextField(20);
        tagInputField.setFont(new Font("Arial", Font.PLAIN, 14));
        tagInputField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(255, 255, 255), 2, true), // 白色边框
                new EmptyBorder(5, 5, 5, 5)
        ));
        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(40, 40, 40)); // 深色背景
        searchButton.setForeground(new Color(255, 255, 255)); // 白色文字
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setFocusPainted(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255))); // 白色边框

        // 添加 Favorite 按钮
        favoriteButton = new JButton("Favorite");
        favoriteButton.setBackground(new Color(40, 40, 40)); // 深色背景
        favoriteButton.setForeground(new Color(255, 255, 255)); // 白色文字
        favoriteButton.setFont(new Font("Arial", Font.BOLD, 14));
        favoriteButton.setFocusPainted(false);
        favoriteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        favoriteButton.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255))); // 白色边框

        inputPanel.add(label);
        inputPanel.add(tagInputField);
        inputPanel.add(searchButton);
        inputPanel.add(favoriteButton);

        // 结果显示区域
        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS)); // 垂直布局
        resultPanel.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255)),
                "Recipes Found", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(255, 255, 255)));

        // 底部说明栏
        JLabel footerLabel = new JLabel("Tip: Enter a tag and click 'Search' to find recipes.", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(200, 200, 200));
        footerLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // 添加组件到 JFrame
        backgroundPanel.add(inputPanel, BorderLayout.NORTH);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);
        backgroundPanel.add(footerLabel, BorderLayout.SOUTH);

        // 搜索按钮事件
        searchButton.addActionListener((ActionEvent e) -> {
            try {
                searchRecipes(tagInputField.getText().trim());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error occurred while searching: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 为 Favorite 按钮添加事件
        favoriteButton.addActionListener((ActionEvent e) -> {
            try {
                searchRecipes("favorite");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error occurred while searching: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 为按钮添加点击动画
        addAnimationToButton(searchButton);
        addAnimationToButton(favoriteButton);
    }

    private void searchRecipes(String tag) throws IOException {
        resultPanel.removeAll(); // 清空旧内容

        List<CommonRecipe> recipes;
        if (tag.isEmpty()) {
            // 如果标签为空，展示所有配方
            recipes = this.presentByTagController.getAllRecipes();
        } else {
            recipes = this.presentByTagController.execute(tag);

            // 如果没有完全匹配结果，则查找相关内容
            if (recipes.isEmpty()) {
                recipes = findSimilarRecipes(tag);
                if (recipes.isEmpty()) {
                    JLabel noResultLabel = new JLabel("No recipes found for tag: " + tag, JLabel.CENTER);
                    noResultLabel.setForeground(new Color(255, 0, 0)); // 红色
                    noResultLabel.setFont(new Font("Arial", Font.BOLD, 16));
                    resultPanel.add(noResultLabel);
                }
            }
        }

        for (Recipe recipe : recipes) {
            resultPanel.add(createRecipeCard(recipe));
        }

        resultPanel.revalidate();
        resultPanel.repaint();
    }

    private List<CommonRecipe> findSimilarRecipes(String tag) throws IOException {
        List<CommonRecipe> allRecipes = this.presentByTagController.getAllRecipes(); // 假设获取所有配方
        List<CommonRecipe> similarRecipes = new ArrayList<>();

        for (CommonRecipe recipe : allRecipes) {
            if (isTagSimilar(tag, recipe.getTag())) {
                similarRecipes.add(recipe);
            }
        }
        return similarRecipes;
    }

    private boolean isTagSimilar(String inputTag, String recipeTag) {
        int distance = calculateLevenshteinDistance(inputTag.toLowerCase(), recipeTag.toLowerCase());
        return distance <= 2; // 允许最多两个字符的编辑距离
    }

    private int calculateLevenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    private JPanel createRecipeCard(Recipe recipe) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(255, 255, 255), 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(new Color(0, 0, 0, 150));
        card.setMaximumSize(new Dimension(750, 200));

        JLabel title = new JLabel(recipe.getName(), JLabel.LEFT);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(255, 255, 255));

        JTextArea details = new JTextArea();
        details.setText("By: " + recipe.getUserName() + "\nTag: " + recipe.getTag() + "\n\nInstructions: "
                + recipe.getInstructions() + "\n\nIngredients:\n");
        for (Ingredient ingredient : recipe.getIngredients()) {
            details.append("- " + ingredient.getIngredientName() + "\n");
        }
        details.setWrapStyleWord(true);
        details.setLineWrap(true);
        details.setEditable(false);
        details.setFont(new Font("Arial", Font.PLAIN, 14));
        details.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        details.setBackground(new Color(240, 240, 240));

        card.add(title, BorderLayout.NORTH);
        card.add(details, BorderLayout.CENTER);

        return card;
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

    private void addAnimationToButton(JButton button) {
        button.addActionListener(e -> {
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setBackground(new Color(50, 50, 50));

            Timer timer = new Timer(100, ae -> {
                button.setFont(new Font("Arial", Font.BOLD, 14));
                button.setBackground(new Color(40, 40, 40));
            });
            timer.setRepeats(false);
            timer.start();
        });
    }
}
