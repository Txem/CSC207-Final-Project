package view;

import entity.CommonRecipe;
import entity.Recipe;
import interface_adapter.present_by_tag.PresentByTagController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * 按标签搜索菜谱界面.
 */
public class PresentByTagView extends JFrame {
    private JTextField tagInputField;
    private JButton searchButton;
    private JTextArea resultArea;
    private final PresentByTagController presentByTagController;

    public PresentByTagView(PresentByTagController presentByTagController) {
        this.presentByTagController = presentByTagController;

        // 设置 JFrame 的基本属性
        setTitle("Recipe Search");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 创建顶部输入区域
        final JPanel inputPanel = new JPanel(new FlowLayout());
        final JLabel label = new JLabel("Enter Tag:");
        tagInputField = new JTextField(20);
        searchButton = new JButton("Search");
        inputPanel.add(label);
        inputPanel.add(tagInputField);
        inputPanel.add(searchButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchRecipes();
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void searchRecipes() throws IOException {
        final String tag = tagInputField.getText().trim();
        if (!tag.isEmpty()) {
            final List<CommonRecipe> recipes = this.presentByTagController.execute(tag);

            resultArea.setText("");
            if (recipes.isEmpty()) {
                resultArea.append("No recipes found for tag: " + tag);
            }
            else {
                for (Recipe recipe : recipes) {
                    resultArea.append(recipe + "\n");
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Tag cannot be empty!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // 示例搜索逻辑
    private List<String> searchFromJson(String tag) {
        return List.of("Recipe 1 for " + tag, "Recipe 2 for " + tag, "Recipe 3 for " + tag);
    }
}
