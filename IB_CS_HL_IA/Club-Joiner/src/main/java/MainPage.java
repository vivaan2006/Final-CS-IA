import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * This class contains the main page of the program.
 */
public class MainPage {
    private final JFrame mainFrame;
    private static final String DB_URL =
            "jdbc:sqlite:src/main/resources/identifier.sqlite";

    /**
     * Constructor for the main page
     */
    public MainPage() {
        mainFrame = new JFrame("Main Page");
        mainFrame.setSize(1000, 600);
        mainFrame.setLayout(null);

        // Navbar
        JPanel navbarPanel = createNavbar();
        mainFrame.add(navbarPanel);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome to Club Joiner");
        welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        welcomeLabel.setBounds(300, 80, 500, 50);
        mainFrame.add(welcomeLabel);

        // Events Today Column
        JLabel eventsTodayLabel = new JLabel("Events Today");
        eventsTodayLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        eventsTodayLabel.setBounds(10, 120, 300, 50);
        mainFrame.add(eventsTodayLabel);

        JScrollPane eventsTodayScrollPane = createEventsTodayScrollPane();
        eventsTodayScrollPane.setBounds(10, 160, 370, 400);
        mainFrame.add(eventsTodayScrollPane);

        // Upcoming Events
        JLabel upcomingEventsLabel = new JLabel("All Upcoming Events");
        upcomingEventsLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        upcomingEventsLabel.setBounds(690, 120, 300, 50);
        mainFrame.add(upcomingEventsLabel);

        JScrollPane upcomingEventsScrollPane = createUpcomingEventsScrollPane();
        upcomingEventsScrollPane.setBounds(620, 160, 370, 400);
        mainFrame.add(upcomingEventsScrollPane);

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(255, 255, 240));
        backgroundPanel.setBounds(0, 70, 1000, 530);
        mainFrame.add(backgroundPanel);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    /**
     * Create the navbar in the program
     *
     * @return the panel
     */
    public JPanel createNavbar() {
        JPanel navbarPanel = new JPanel();
        navbarPanel.setBackground(new Color(0, 120, 74));
        navbarPanel.setBounds(0, 0, 1000, 70);
        navbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 130, 10));
        navbarPanel.setOpaque(true);

        ActionListener homeListener = e -> {
            mainFrame.dispose();
            new MainPage();
        };
        ActionListener yourClubsListener = e -> {
            mainFrame.dispose();
            new YourClubs().setVisible(true);
        };

        ActionListener joinClubsListener = e -> {
            mainFrame.dispose();
            new JoinClubsFrame().setVisible(true);
        };

        ActionListener helpListener = e -> {
            mainFrame.dispose();
            new HelpFrame().setVisible(true);
        };

        navbarPanel.add(createMenuItem("Home", homeListener));
        navbarPanel.add(createMenuItem("Join Clubs", joinClubsListener));
        navbarPanel.add(createMenuItem("Your Clubs", yourClubsListener));
        navbarPanel.add(createMenuItem("Help", helpListener));
        navbarPanel.add(createLogoutMenuItem());

        return navbarPanel;
    }

    /**
     * Creating an option within the navbar
     *
     * @param text           of the option
     * @param actionListener to perform an action
     * @return the label
     */
    private JLabel createMenuItem(String text, ActionListener actionListener) {
        JLabel menuItem = new JLabel(text);
        menuItem.setForeground(Color.WHITE);
        menuItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
        menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actionListener.actionPerformed(new ActionEvent(menuItem, ActionEvent.ACTION_PERFORMED, null));
            }
        });

        return menuItem;
    }

    /**
     * Creating the logout option in the navbar
     *
     * @return the label
     */
    private JLabel createLogoutMenuItem() {
        JLabel logoutMenuItem = new JLabel("Logout");
        logoutMenuItem.setForeground(Color.WHITE);
        logoutMenuItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
        logoutMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        logoutMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.dispose();
                new LoginScreen();
            }
        });

        return logoutMenuItem;
    }

    /**
     * Creating the scroll pane on the right
     *
     * @return the scroll pane
     */
    private JScrollPane createUpcomingEventsScrollPane() {
        JPanel eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));

        // Fetch all events
        ArrayList<String> allEvents = fetchAllEvents();

        for (String event : allEvents) {
            JLabel eventLabel = new JLabel(event);
            eventLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
            eventLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5)); // Spacing
            eventsPanel.add(eventLabel);
        }

        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }

    /**
     * Using recursion to fetch the events
     *
     * @return the events list
     */
    private ArrayList<String> fetchAllEvents() {
        return fetchEventsRecursive(1, new ArrayList<>());
    }

    /**
     * Fetch all events from the database, for the upcoming events side by using recursion
     *
     * @param page      the page number
     * @param allEvents the list of all events
     * @return the list of all events
     */
    private ArrayList<String> fetchEventsRecursive(int page, ArrayList<String> allEvents) {
        String query = "SELECT club, eventDate, eventName FROM events ORDER BY eventDate LIMIT ?, 10";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, (page - 1) * 10);

            try (ResultSet resultSet = ps.executeQuery()) {
                boolean hasNextPage = false;
                while (resultSet.next()) {
                    String clubname = resultSet.getString("club");
                    String eventDate = resultSet.getString("eventDate");
                    String eventName = resultSet.getString("eventName");
                    String eventInfo = clubname + " (" + eventDate + "): " + eventName;
                    allEvents.add(eventInfo);
                    hasNextPage = true;
                }

                if (hasNextPage) {
                    fetchEventsRecursive(page + 1, allEvents);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allEvents;
    }

    /**
     * Create the scroll pane on the left
     *
     * @return the scroll pane
     */
    private JScrollPane createEventsTodayScrollPane() {
        JPanel eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter);

        ArrayList<String> eventsToday = fetchEventsToday(formattedDate);

        for (String event : eventsToday) {
            JLabel eventLabel = new JLabel(event);
            eventLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
            eventLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5)); // Spacing
            eventsPanel.add(eventLabel);
        }

        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }

    /**
     * Fetching events from the database, and displaying on the left side
     *
     * @param currentDate today's date
     * @return the event list
     */
    private ArrayList<String> fetchEventsToday(String currentDate) {
        ArrayList<String> eventsToday = new ArrayList<>();
        String query = "SELECT club, eventDate, eventName FROM events WHERE eventDate = ? ORDER BY eventDate";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, currentDate);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    String clubname = resultSet.getString("club");
                    String eventDate = resultSet.getString("eventDate");
                    String eventName = resultSet.getString("eventName");
                    String eventInfo = clubname + " (" + eventDate + "): " + eventName;
                    eventsToday.add(eventInfo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventsToday;
    }
}

