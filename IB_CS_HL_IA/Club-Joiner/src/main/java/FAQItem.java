import javax.swing.*;
import java.awt.*;

/**
 * This class represents a single FAQ item.
 */
public class FAQItem extends JPanel {
    // These store the questions and answers for the FAQs
    private final String question;
    private final String answer;

    /**
     * Constructor that sets the text size and format for the text
     *
     * @param question that is being asked
     * @param answer   of the question
     */
    public FAQItem(String question, String answer) {
        this.question = question;
        this.answer = answer;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // question and answer label and formatting
        JLabel questionLabel = new JLabel("<html><b>Q:</b> " + question + "</html>");
        JLabel answerLabel = new JLabel("<html><b>A:</b> " + answer + "</html>");

        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        answerLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        // adding the labels and format to the panel
        add(questionLabel);
        add(answerLabel);
        add(Box.createRigidArea(new Dimension(0, 40)));
    }

    /**
     * Gets the question
     *
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Gets the answer
     *
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }
}

