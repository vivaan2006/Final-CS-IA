import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * This class contains the frame that allows the user to join clubs
 */
public class JoinClubsFrame extends JPanel {

    private final JFrame clubFrame;
    private final MainPage mainPage = new MainPage();
    private final HashMap<String, String[]> clubsMap;

    /**
     * Constructor for the JoinClubsFrame class
     */
    public JoinClubsFrame() {
        clubFrame = new JFrame("Join Clubs");
        clubFrame.setSize(1000, 600);
        clubFrame.setLayout(null);

        JPanel navbarPanel = mainPage.createNavbar();
        clubFrame.add(navbarPanel);

        JLabel welcomeLabel = new JLabel("Available Clubs");
        welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        welcomeLabel.setBounds(300, 80, 500, 50);
        clubFrame.add(welcomeLabel);

        clubsMap = new HashMap<>();

        clubsMap.put("<html> <b> Art Co. <b> <html>\"", new String[]{"Wed 2:40 - 3:40", "A407"});
        clubsMap.put("<html> <b> ASL <b> <html>", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Book Club<b> <html>", new String[]{"Friday 12:10-12:40", "Media Center"});
        clubsMap.put("<html> <b>Careers Club<b> <html>", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Chess Club<b> <html>", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Chinese Club<b> <html>", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Clay Club<b> <html>", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Debate<b> <html>", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>DECA<b> <html>", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>E Sports<b> <html>", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Earth Club<b> <html>", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>FBLA<b> <html>", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>FCCLA<b> <html>", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>French Club<b> <html> ", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Game Club<b> <html>", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Girls Who Code<b> <html>", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>GSL<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>HOSA<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Huskies United Champions<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Key Club<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Korean Club<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Latinos In Action<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Math Club<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Mock Trial<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Mountain Biking<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>MUN<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>National Honor Society<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Pickleball Club<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Computer Science Club<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>PTSA<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Psychology Club<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Red Cross Club<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Robotics Team<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Rock Climbing/Mountaineering<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Science Olympiad<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Skills USA<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>SSEP<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>TSA<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>UNICEF Unite<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>Volunteer Collaboration Board<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});
        clubsMap.put("<html> <b>YEI<b> <html>\"", new String[]{"Wed 2:35 - 3:35", "A318"});

        JPanel clubsPanel = new JPanel();
        clubsPanel.setLayout(new BoxLayout(clubsPanel, BoxLayout.Y_AXIS));

        for (String club : clubsMap.keySet()) {
            JPanel clubEntryPanel = new JPanel();
            clubEntryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel clubLabel = new JLabel(club);
            clubLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
            clubEntryPanel.add(clubLabel);

            JLabel meetingDatesLabel = new JLabel("                       Meeting Date: " + clubsMap.get(club)[0]);
            meetingDatesLabel.setToolTipText("Meeting Date");
            clubEntryPanel.add(meetingDatesLabel);

            JLabel locationLabel = new JLabel("                                           Location: " + clubsMap.get(club)[1]);
            locationLabel.setToolTipText("Location");
            clubEntryPanel.add(locationLabel);

            JButton joinButton = new JButton("Join");
            joinButton.addActionListener(e -> {
                try {
                    joinClub(club);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            clubEntryPanel.add(joinButton);

            clubsPanel.add(clubEntryPanel);
        }

        JScrollPane clubsScrollPane = new JScrollPane(clubsPanel);
        clubsScrollPane.setBounds(35, 160, 930, 380);
        clubFrame.add(clubsScrollPane);

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(255, 255, 240));
        backgroundPanel.setBounds(0, 70, 1000, 530);
        clubFrame.add(backgroundPanel);

        clubFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clubFrame.setLocationRelativeTo(null);
        clubFrame.setVisible(true);
    }

    /**
     * Allows the user to join a club database wise
     *
     * @param club the user wants to join
     * @throws SQLException if there is an error with SQL
     */
    private void joinClub(String club) throws SQLException {
        String url =
                "jdbc:sqlite:src/main/resources/identifier.sqlite";
        String sql = "INSERT INTO joinedclubs (username, clubname) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, LoginScreen.loggedInUser);
            ps.setString(2, club);
            ps.executeUpdate();
        }
    }
}
