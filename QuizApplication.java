
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizApplication extends JFrame implements ActionListener {

    String[] questions = {
        "Which language is used for Android development?",
        "Which company developed Java?",
        "Which keyword creates an object in Java?",
        "Which data type stores true or false?",
        "Which method starts Java execution?"
    };

    String[][] options = {
        {"Python", "Java", "C", "HTML"},
        {"Microsoft", "Google", "Sun Microsystems", "Apple"},
        {"class", "new", "this", "static"},
        {"int", "String", "boolean", "double"},
        {"start()", "run()", "main()", "execute()"}
    };

    int[] answers = {1, 2, 1, 2, 2};

    int current = 0;
    int score = 0;

    JLabel questionLabel, titleLabel, resultLabel;
    JRadioButton[] choices = new JRadioButton[4];
    ButtonGroup group;
    JButton nextButton;

    QuizApplication() {

        setTitle("Java Quiz Application");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        titleLabel = new JLabel("JAVA QUIZ APPLICATION");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(110, 25, 400, 35);
        panel.add(titleLabel);

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 17));
        questionLabel.setBounds(40, 90, 520, 35);
        panel.add(questionLabel);

        group = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            choices[i] = new JRadioButton();
            choices[i].setBounds(65, 145 + (i * 45), 450, 30);
            choices[i].setFont(new Font("Arial", Font.PLAIN, 16));
            choices[i].setBackground(Color.WHITE);
            group.add(choices[i]);
            panel.add(choices[i]);
        }

        nextButton = new JButton("Next Question");
        nextButton.setBounds(190, 345, 200, 40);
        nextButton.setFont(new Font("Arial", Font.BOLD, 15));
        nextButton.addActionListener(this);
        panel.add(nextButton);

        resultLabel = new JLabel("");
        resultLabel.setBounds(180, 300, 300, 30);
        panel.add(resultLabel);

        add(panel);

        loadQuestion();

        setVisible(true);
    }

    void loadQuestion() {

        questionLabel.setText(
            "Q" + (current + 1) + ". " + questions[current]
        );

        group.clearSelection();

        for (int i = 0; i < 4; i++) {
            choices[i].setText(options[current][i]);
        }

    }

    public void actionPerformed(ActionEvent e) {

        int selected = -1;

        for (int i = 0; i < 4; i++) {
            if (choices[i].isSelected()) {
                selected = i;
                break;
            }
        }

        if (selected == -1) {
            JOptionPane.showMessageDialog(
                this, "Please select an answer!"
            );
            return;
        }

        if (selected == answers[current]) {
            score++;
        }

        current++;

        if (current < questions.length) {
            loadQuestion();
        } else {
            showResult();
        }
    }

    void showResult() {

        getContentPane().removeAll();

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(4, 1));
        resultPanel.setBackground(Color.WHITE);

        JLabel heading = new JLabel(
            "QUIZ COMPLETED!", JLabel.CENTER
        );
        heading.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel marks = new JLabel(
            "Your Score: " + score + " / " + questions.length,
            JLabel.CENTER
        );
        marks.setFont(new Font("Arial", Font.BOLD, 23));

        JButton restart = new JButton("Restart Quiz");
        restart.addActionListener(e -> {
            current = 0;
            score = 0;
            getContentPane().removeAll();
            initializeAgain();
        });

        resultPanel.add(heading);
        resultPanel.add(marks);
        resultPanel.add(new JLabel("", JLabel.CENTER));
        resultPanel.add(restart);

        add(resultPanel);
        revalidate();
        repaint();
    }

    void initializeAgain() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        titleLabel = new JLabel("JAVA QUIZ APPLICATION");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(110, 25, 400, 35);
        panel.add(titleLabel);

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 17));
        questionLabel.setBounds(40, 90, 520, 35);
        panel.add(questionLabel);

        group = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            choices[i] = new JRadioButton();
            choices[i].setBounds(65, 145 + i * 45, 450, 30);
            choices[i].setBackground(Color.WHITE);
            group.add(choices[i]);
            panel.add(choices[i]);
        }

        nextButton = new JButton("Next Question");
        nextButton.setBounds(190, 345, 200, 40);
        nextButton.addActionListener(this);
        panel.add(nextButton);

        add(panel);
        loadQuestion();
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizApplication::new);
    }
}
