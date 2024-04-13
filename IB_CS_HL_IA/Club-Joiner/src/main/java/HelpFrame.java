import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Main HelpFrame class that allows for the full functionality of the Help Page in the program.
 */
public class HelpFrame extends JPanel {
    // Set of variables that define the frame, call the main page, stores question lists and has a search field.
    private final MainPage mainPage = new MainPage();
    private final JFrame clubFrame;
    private final List<FAQItem> faqs;
    private final JTextField search;

    /**
     * This is the constructor for the HelpFrame class, and essentially sets up the frame.
     */
    public HelpFrame() {
        // The following lines essentially create the frame for the help page
        clubFrame = new JFrame("Help");
        clubFrame.setSize(1000, 600);
        clubFrame.setLayout(null);

        // adding the navbar here
        JPanel navbarPanel = mainPage.createNavbar(); // calls the function to create the navbar
        clubFrame.add(navbarPanel);

        // Welcome to the help page
        JLabel welcomeLabel = new JLabel("Frequently Asked Questions");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setBounds(300, 80, 500, 50);
        clubFrame.add(welcomeLabel);

        // Search Field to search for questions for users
        search = new JTextField();
        search.setFont(new Font("Arial", Font.PLAIN, 16));
        search.setBounds(730, 90, 200, 30);
        clubFrame.add(search);

        // allows for the question lists to be scrollable
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 140, 930, 380);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        clubFrame.add(scrollPane);

        // creating the panel for the FAQ items,
        JPanel faqPanel = new JPanel();
        faqPanel.setLayout(new BoxLayout(faqPanel, BoxLayout.Y_AXIS));
        faqs = createFAQItems();
        refreshPanel(faqPanel); // function call to refresh the panel
        scrollPane.setViewportView(faqPanel); // adding the panel to scrollpane

        // lambda function to search for questions
        search.addActionListener(e -> {
            String search = this.search.getText().toLowerCase();
            List<FAQItem> searchedItems = searchItems(search);
            refreshPanel(faqPanel, searchedItems);
        });

        // final set up of the frame, allowing it to close, be visible and centered
        clubFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clubFrame.setLocationRelativeTo(null);
        clubFrame.setVisible(true);
    }

    /**
     * This method creates the list of items on the FAQ page, and stores it in a Linked List
     *
     * @return the items/ question and answer sets
     */
    private LinkedList<FAQItem> createFAQItems() {
        LinkedList<FAQItem> items = new LinkedList<>();
        // all additions call the FAQ item class that stores the question and answer
        items.add(new FAQItem("How can I join a club?", "To join a club, navigate to the 'Join Clubs' tab, find the club you're interested in, and click the 'Join' button."));
        items.add(new FAQItem("Can I join multiple clubs?", "Yes, you can join multiple clubs. There is no limit to the number of clubs you can join."));
        items.add(new FAQItem("How can I leave a club?", "Navigate to the 'Your Clubs' tab and then click leave next to the club you'd like to leave"));
        items.add(new FAQItem("How can I exit the program?", "Click the logout button at the top right button of the application."));
        items.add(new FAQItem("How can I Learn more about each club?", "To do so, you must navigate to the high school's website, and contact the advisor."));
        items.add(new FAQItem("How many hours is Club Joiner open?", "The club joiner program is available everyday for looking at and joining clubs"));
        items.add(new FAQItem("Further Questions?", "Email Club joiner at <a href=\"clubjoinerapplication@gmail.com\">clubjoinerapplication@gmail.com</a> for further information."));
        return items;
    }

    /**
     * This method searches for items based on the user's input
     *
     * @param query is the user's input
     * @return the filtered items
     */
    private List<FAQItem> searchItems(String query) {
        LinkedList<FAQItem> filteredItems = new LinkedList<>();
        // for each loop to iterate through items, the conditional calls many functions to make search visible
        for (FAQItem item : faqs) {
            if (item.getQuestion().toLowerCase().contains(query) || item.getAnswer().toLowerCase().contains(query)) {
                filteredItems.add(item); // filtering results based on users input
            }
        }
        return filteredItems;
    }

    /**
     * Method overloading element (essentially refreshes every panel and list a search is made; polymorphism)
     *
     * @param faqPanel is the panel that is being refreshed
     */
    private void refreshPanel(JPanel faqPanel) {
        faqPanel.removeAll(); // removing everything from panel, for each used below
        for (FAQItem item : faqs) {
            faqPanel.add(item);
        }
        faqPanel.revalidate(); // called with repaint to refresh contents and repaint the panel
        faqPanel.repaint();
    }

    /**
     * This is another method that refreshes the panel, and the list in which narrows down the search.
     *
     * @param faqPanel is the panel that's being refreshed
     * @param items    that are being refreshed
     */
    private void refreshPanel(JPanel faqPanel, List<FAQItem> items) {
        faqPanel.removeAll(); // removing everything from panel, for each used below
        for (FAQItem item : items) {
            faqPanel.add(item);
        }
        faqPanel.revalidate(); // called with repaint to refresh contents and repaint the panel
        faqPanel.repaint();
    }

}