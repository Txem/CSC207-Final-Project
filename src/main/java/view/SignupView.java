package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import components.StyledButton;
import components.StyledLabel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

// * The View for the Signup Use Case.
// */
//public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
//    private final String viewName = "sign up";
//
//    private final SignupViewModel signupViewModel;
//    private final JTextField usernameInputField = new JTextField(15);
//    private final JPasswordField passwordInputField = new JPasswordField(15);
//    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
//    private SignupController signupController;
//
//    private final JButton signUp;
////    private final JButton cancel;
//    private final JButton toLogin;
//
//    public SignupView(SignupViewModel signupViewModel) {
//        this.signupViewModel = signupViewModel;
//        signupViewModel.addPropertyChangeListener(this);
//
//        final BackgroundPanel backgroundPanel = new BackgroundPanel("img/images.jpg");
//        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
//
//        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
//        title.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        final LabelTextPanel usernameInfo = new LabelTextPanel(
//                new JLabel(SignupViewModel.USERNAME_LABEL), usernameInputField);
//        final LabelTextPanel passwordInfo = new LabelTextPanel(
//                new JLabel(SignupViewModel.PASSWORD_LABEL), passwordInputField);
//        final LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
//                new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL), repeatPasswordInputField);
//
//        final JPanel buttons = new JPanel();
//        toLogin = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
//        toLogin.setForeground(Color.WHITE); // Text color
//        toLogin.setBackground(new Color(70, 130, 180)); // Default background color
//        toLogin.setOpaque(true);
//        toLogin.setBorderPainted(false);
//        HoverEffect.apply(
//                toLogin,
//                new Color(70, 130, 180),
//                new Color(30, 144, 255),
//                new Color(25, 25, 112)
//        );
//        buttons.add(toLogin);
//        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
//        signUp.setForeground(Color.WHITE); // Text color
//        signUp.setBackground(new Color(70, 130, 180)); // Default background color
//        signUp.setOpaque(true);
//        signUp.setBorderPainted(false);
//        HoverEffect.apply(
//                signUp,
//                new Color(70, 130, 180),
//                new Color(30, 144, 255),
//                new Color(25, 25, 112)
//        );
//        buttons.add(signUp);
////        cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
////        cancel.setForeground(Color.WHITE); // Text color
////        toLogin.setBackground(new Color(70, 130, 180)); // Default background color
////        toLogin.setOpaque(true);
////        toLogin.setBorderPainted(false);
////        buttons.add(cancel);
//
//        signUp.addActionListener(
//                // This creates an anonymous subclass of ActionListener and instantiates it.
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(signUp)) {
//                            final SignupState currentState = signupViewModel.getState();
//
//                            signupController.execute(
//                                    currentState.getUsername(),
//                                    currentState.getPassword(),
//                                    currentState.getRepeatPassword()
//                            );
//                        }
//                    }
//                }
//        );
//
//        toLogin.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        signupController.switchToLoginView();
//                    }
//                }
//        );
//
////        cancel.addActionListener(this);
//
//        addUsernameListener();
//        addPasswordListener();
//        addRepeatPasswordListener();
//
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        this.add(title);
//        this.add(usernameInfo);
//        this.add(passwordInfo);
//        this.add(repeatPasswordInfo);
//        this.add(buttons);
//    }
//
//    private void addUsernameListener() {
//        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
//
//            private void documentListenerHelper() {
//                final SignupState currentState = signupViewModel.getState();
//                currentState.setUsername(usernameInputField.getText());
//                signupViewModel.setState(currentState);
//            }
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//        });
//    }
//
//    private void addPasswordListener() {
//        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
//
//            private void documentListenerHelper() {
//                final SignupState currentState = signupViewModel.getState();
//                currentState.setPassword(new String(passwordInputField.getPassword()));
//                signupViewModel.setState(currentState);
//            }
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//        });
//    }
//
//    private void addRepeatPasswordListener() {
//        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {
//
//            private void documentListenerHelper() {
//                final SignupState currentState = signupViewModel.getState();
//                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
//                signupViewModel.setState(currentState);
//            }
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//        });
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent evt) {
//        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
//    }
//
//    @Override
//    public void propertyChange(PropertyChangeEvent evt) {
//        final SignupState state = (SignupState) evt.getNewValue();
//        if (state.getUsernameError() != null) {
//            JOptionPane.showMessageDialog(this, state.getUsernameError());
//        }
//    }
//
//    public String getViewName() {
//        return viewName;
//    }
//
//    public void setSignupController(SignupController controller) {
//        this.signupController = controller;
//    }
//}
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private SignupController signupController;

    private final StyledButton signUp;
    private final StyledButton toLogin;

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        // Set transparent background
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

//        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL, JLabel.CENTER);
//        title.setFont(new Font("Serif", Font.BOLD, 28));
//        title.setAlignmentX(Component.CENTER_ALIGNMENT);
//        title.setForeground(Color.WHITE);

        // Add the title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false); // Transparent panel
        JLabel titleLabel = new JLabel("Genshin Recipe App", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48)); // Larger font size
        titleLabel.setForeground(Color.white); // Change font color to black
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent black background
        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new StyledLabel(SignupViewModel.USERNAME_LABEL), usernameInputField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new StyledLabel(SignupViewModel.PASSWORD_LABEL), passwordInputField);
        final LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                new StyledLabel(SignupViewModel.REPEAT_PASSWORD_LABEL), repeatPasswordInputField);

        final JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        toLogin = new StyledButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
        buttons.add(toLogin);
        signUp = new StyledButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        buttons.add(signUp);

        signUp.addActionListener(evt -> {
            final SignupState currentState = signupViewModel.getState();
            signupController.execute(
                    currentState.getUsername(),
                    currentState.getPassword(),
                    currentState.getRepeatPassword()
            );
        });

        toLogin.addActionListener(evt -> signupController.switchToLoginView());
        add(titleLabel, BorderLayout.CENTER);
        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();
        add(usernameInfo);
        add(passwordInfo);
        add(repeatPasswordInfo);
        add(buttons);
        this.setOpaque(false);
    }

    // Create a styled button with hover effects
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE); // Text color
        button.setBackground(new Color(70, 130, 180));
        button.setOpaque(true);
        button.setBorderPainted(false);
        HoverEffect.apply(button,
                new Color(70, 130, 180),
                new Color(30, 144, 255),
                new Color(25, 25, 112)
        );
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        // Draw a semi-transparent background
        g2d.setColor(new Color(0, 0, 0, 100)); // Black with 40% transparency
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }

    // Add listeners for inputs
    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signupViewModel.setState(currentState);
            }
            @Override public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        }
        );
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
            }
            @Override public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
    }

    private void addRepeatPasswordListener() {
        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signupViewModel.setState(currentState);
            }
            @Override public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }
}

