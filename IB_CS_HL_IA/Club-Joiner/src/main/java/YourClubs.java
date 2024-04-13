import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * This class contains the frame that consists of the clubs that the user is a part of
 */
public class YourClubs extends JPanel {

    private final JFrame clubFrame;
    MainPage mainPage = new MainPage();

    /**
     * Constructor for the YourClubs class
     */
    public YourClubs() {
        clubFrame = new JFrame("Your Clubs");
        clubFrame.setSize(1000, 600);
        clubFrame.setLayout(null);

        JPanel navbarPanel = mainPage.createNavbar();
        clubFrame.add(navbarPanel);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Your Clubs");
        welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        welcomeLabel.setBounds(400, 100, 500, 50);
        clubFrame.add(welcomeLabel);

        // Fetch clubs for the logged-in user
        ArrayList<String> userClubs = fetchUserClubs(LoginScreen.loggedInUser);

        JScrollPane clubsScrollPane = createClubsScrollPane(userClubs.toArray(new String[0]));
        clubsScrollPane.setBounds(200, 160, 600, 400);
        clubFrame.add(clubsScrollPane);

        // Background
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(255, 255, 240));
        backgroundPanel.setBounds(0, 70, 1000, 530);
        clubFrame.add(backgroundPanel);

        clubFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clubFrame.setLocationRelativeTo(null);
        clubFrame.setVisible(true);
    }

    /**
     * Fetches the clubs that the user is a part of
     *
     * @param username of the user
     * @return the list of clubs that the user is part of
     */
    private ArrayList<String> fetchUserClubs(String username) {
        ArrayList<String> userClubs = new ArrayList<>();
        String url = "jdbc:sqlite:src/main/resources/identifier.sqlite";
        String query = "SELECT clubname FROM joinedclubs WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, username);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    userClubs.add(resultSet.getString("clubname"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userClubs;
    }

    /**
     * Creates a scroll pane with the clubs that the user is a part of
     *
     * @param clubs that the user is a part of
     * @return the scroll pane
     */
    private JScrollPane createClubsScrollPane(String... clubs) {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        for (String club : clubs) {
            JPanel clubPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel clubLabel = new JLabel(club);
            clubPanel.add(clubLabel);

            JButton leaveButton = new JButton("Leave Club");
            leaveButton.addActionListener(e -> {
                String clubName = clubLabel.getText();
                leaveClub(LoginScreen.loggedInUser, clubName);
                panel.remove(clubPanel);
                panel.revalidate();
                panel.repaint();
            });
            clubPanel.add(leaveButton);

            panel.add(clubPanel);
        }

        JScrollPane clubsScrollPane = new JScrollPane(panel);
        return clubsScrollPane;
    }

    /**
     * Method to leave a club
     *
     * @param username of the user
     * @param clubName of the club
     */
    private void leaveClub(String username, String clubName) {
        String url =
                "jdbc:sqlite:src/main/resources/identifier.sqlite";
        String query = "DELETE FROM joinedclubs WHERE username = ? AND clubname = ?";

        // try catch statement
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, clubName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
