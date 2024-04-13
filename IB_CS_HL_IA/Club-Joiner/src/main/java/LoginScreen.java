import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Main class that starts up the programs loginscreen
 */
public class LoginScreen implements ActionListener {
    // setting the backgrounds, labels etc here
    private final JFrame frame;
    private final ImageIcon backgroundImage;
    private final JLabel background;
    private final JLabel studentCenterTitle;
    private final JPanel panel;

    // credentials
    private final JLabel userID;
    private final JLabel password;
    private final JTextField userIDField;
    private final JPasswordField passwordField;
    public static String loggedInUser;
    // buttons
    private final JButton studentLoginButton;
    private final JButton studentForgotPassword;
    private JButton createAccountButton;

    /**
     * Constructor for the login screen
     */
    public LoginScreen() {
        // setting the background image for login screen
        backgroundImage = new ImageIcon(this.getClass().getResource("images/hhs.jpg"));
        background = new JLabel(backgroundImage);
        background.setSize(1000, 600);
        background.setBounds(0, 0, 1000, 600);

        // Panel of the gui
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 300, 600);
        panel.setSize(300, 600);

        // Main student login title
        studentCenterTitle = new JLabel("Welcome To Club Joiner! Your Go-To Club Management System!");
        studentCenterTitle.setBounds(50, 70, 1000, 100);
        studentCenterTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        studentCenterTitle.setForeground(Color.BLACK);
        background.add(studentCenterTitle); // adding title here

        // UserID text
        userID = new JLabel("User Name");
        userID.setBounds(350, 160, 100, 50);
        userID.setFont(new Font("Tahoma", Font.BOLD, 16));
        background.add(userID);

        // Student password text
        password = new JLabel("Password");
        password.setBounds(350, 260, 100, 50);
        password.setFont(new Font("Tahoma", Font.BOLD, 16));
        background.add(password);

        // Student UserID Field
        userIDField = new JTextField();
        userIDField.setBounds(350, 200, 225, 33);
        background.add(userIDField);

        // Student Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(350, 300, 225, 33);
        background.add(passwordField);

        // Student Login Button
        studentLoginButton = new JButton("Log In");
        studentLoginButton.addActionListener(this);
        studentLoginButton.setBounds(350, 360, 225, 33);
        background.add(studentLoginButton);

        studentForgotPassword = new JButton("Change Password");
        studentForgotPassword.addActionListener(this);
        studentForgotPassword.setBounds(350, 400, 225, 33);
        background.add(studentForgotPassword);

        // create account button
        createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(350, 440, 225, 33);
        createAccountButton.addActionListener(this);
        background.add(createAccountButton);

        // Frame
        frame = new JFrame("Login Screen");
        frame.add(background);
        frame.add(panel);
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * main method to start the program
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.frame.setVisible(true); // allowing us to see the screen
    }

    /**
     * Allos us to proocess the events from the buttons on the login page
     *
     * @param e event processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == studentLoginButton) {
            // getting the texts entered an submitted
            String enteredUsername = userIDField.getText();
            String enteredPassword = new String(passwordField.getPassword());
            // try-catch to validate login and allow user into the main app page (catching potential errors)
            try {
                if (validateLogin(enteredUsername, enteredPassword)) {
                    frame.dispose();
                    new MainPage();
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
                loggedInUser = enteredUsername;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
        // 2 remaining conditionals for forgot password and creating account.
        else if (e.getSource() == studentForgotPassword) {
            new ForgotPasswordFrame(this);
        } else if (e.getSource() == createAccountButton) {
            new CreateAccount();
        }

    }

    /**
     * Method to validate the login of the user
     *
     * @param username of the user
     * @param password of the user
     * @return true if the login is valid, false if not
     * @throws SQLException           if there is an error with the SQL
     * @throws ClassNotFoundException if the class is not found
     */
    private boolean validateLogin(String username, String password) throws SQLException, ClassNotFoundException {
        String url =
                "jdbc:sqlite:src/main/resources/identifier.sqlite";
        Class.forName("org.sqlite.JDBC");
        String query = "SELECT * FROM login_info WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet resultSet = ps.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}