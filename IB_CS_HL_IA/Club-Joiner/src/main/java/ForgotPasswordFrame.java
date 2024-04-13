import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The frame that allows the reset of a password
 */
public class ForgotPasswordFrame extends JFrame {
    // creating the fields and buttons necessary to allow the user to change their password.
    private final JButton resetButton;
    private final JButton backButton;
    private final JTextField usernameField; // to ask the user for the username
    private final LoginScreen loginScreen;


    /**
     * constructor for the ForgotPasswordFrame
     */
    public ForgotPasswordFrame(LoginScreen loginScreen) {
        this.loginScreen = loginScreen;
        // setting the basic characteristics of the frame
        setTitle("Forgot Password");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // setting the panel sizes
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // creating the labels for the user to enter their username for the reset.
        JLabel usernameLabel = new JLabel("Username:");
        resetButton = new JButton("Reset Password");
        backButton = new JButton("Back");
        usernameField = new JTextField(20);

        resetButton.addActionListener(e -> {
            String username = usernameField.getText();
            if (resetPassword(username)) {
                JOptionPane.showMessageDialog(null, "Password reset successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to reset password, please check the username and try again.");
            }
        });

        backButton.addActionListener(e -> dispose());

        // adding the buttons and labels to the panel
        JPanel usernamePanel = new JPanel();
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        // adding the buttons to the panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(resetButton);
        buttonPanel.add(backButton);

        // adding the panels to the frame
        panel.add(usernamePanel);
        panel.add(buttonPanel);

        // setting the location of the frame
        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Method to reset the password for the user
     *
     * @param username of the person
     * @return true if the password was reset, false otherwise
     */
    private boolean resetPassword(String username) {
        String url =
                "jdbc:sqlite:src/main/resources/identifier.sqlite";
        String updateQuery = "UPDATE login_info SET password = ? WHERE username = ?";

        // creating a connection to the database (try catch)
        try (Connection connection = DriverManager.getConnection(url);
             // creating a prepared statement to update the password
             PreparedStatement ps = connection.prepareStatement(updateQuery)) {
            String newPassword = createPassword(); // creating a new password for the user

            // setting the parameters for the prepared statement
            ps.setString(1, newPassword);
            ps.setString(2, username);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                showPassword(username, newPassword);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Method to create a new password for the user
     *
     * @return the new password
     */
    private String createPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // set of characters to pick from randomly
        StringBuilder sb = new StringBuilder(); // building the password using a loop below and random characters
        // randomly iterates through a numbered list, and creates the random password
        for (int i = 0; i < 8; i++) {
            // utilized casting to convert the random number to an integer
            int index = (int) (Math.random() * characters.length());
            sb.append(characters.charAt(index)); // string function used
        }
        return sb.toString();
    }

    /**
     * Method to show the new password to the user
     *
     * @param username    of the person
     * @param newPassword the new password
     */
    private void showPassword(String username, String newPassword) {
        JTextField passwordField = new JTextField(newPassword);
        passwordField.setEditable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("New password for " + username + ":"), BorderLayout.NORTH);
        panel.add(passwordField, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(null, panel);
    }
}