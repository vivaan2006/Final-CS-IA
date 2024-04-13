import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class contains the create account frame
 */
public class CreateAccount extends JFrame implements ActionListener {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton createButton;

    /**
     * Constructor for the create account frame
     */
    public CreateAccount() {
        setTitle("Create Account");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        createButton = new JButton("Create Account");
        createButton.addActionListener(this);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(createButton);
        add(panel);
        setVisible(true);
    }

    /**
     * Processing the events when buttons are clicked
     *
     * @param e the event to be processed
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (!username.isEmpty() && !password.isEmpty()) {
                try {
                    if (createAccount(username, password)) {
                        JOptionPane.showMessageDialog(this, "Account created");
                        dispose();
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }
    }

    /**
     * This method allows the user to create an account
     *
     * @param username of the user
     * @param password of the user
     * @return true if the account was created, false if not
     * @throws SQLException           if there is an error with the SQL
     * @throws ClassNotFoundException if the class is not found
     */
    private boolean createAccount(String username, String password) throws SQLException, ClassNotFoundException {
        String url = "jdbc:sqlite:src/main/resources/identifier.sqlite";

        Class.forName("org.sqlite.JDBC");
        String insertQuery = "INSERT INTO login_info (username, password) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(insertQuery)) {

            ps.setString(1, username);
            ps.setString(2, password);

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        }
    }
}
