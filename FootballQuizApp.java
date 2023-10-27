import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class FootballQuizApp extends JFrame {

    private JPanel mainPanel;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup buttonGroup;
    private JButton submitButton;

    private String[] questions;
    private String[][] options;
    private int[] correctAnswers;
    private int currentQuestion;
    private int score;
    private int timeRemaining;

    public FootballQuizApp() {
        setTitle("Football Quiz App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        questions = new String[] {
            "Who is known as the 'Samba King' in football?",
            "Which Indian football club is known as 'The Mariners'?",
            "Who is often referred to as the 'King of Football'?",
            "Which country has won the most FIFA World Cup titles?",
            "Who won the FIFA Ballon d'Or in 2021?",
            "What is the home stadium of FC Barcelona?",
            "Which club did Neymar join after leaving Barcelona?",
            "Who is the all-time top scorer in the history of FIFA World Cup?",
            "Which country hosted the first FIFA World Cup in 1930?",
            "What is the nickname of Pelé?"
        };

        options = new String[][] {
            {"Neymar", "Lionel Messi", "Cristiano Ronaldo", "Ronaldinho"},
            {"East Bengal", "Mohun Bagan", "ATK Mohun Bagan", "Chennaiyin FC"},
            {"Pele", "Diego Maradona", "Johan Cruyff", "Zinedine Zidane"},
            {"Brazil", "Germany", "Italy", "Argentina"},
            {"Lionel Messi", "Robert Lewandowski", "Karim Benzema", "Neymar"},
            {"Camp Nou", "Santiago Bernabeu", "Old Trafford", "Anfield"},
            {"Paris Saint-Germain", "Real Madrid", "Manchester United", "Bayern Munich"},
            {"Pelé", "Miroslav Klose", "Ronaldo", "Lionel Messi"},
            {"Uruguay", "Brazil", "Argentina", "Italy"},
            {"The Black Pearl", "The Magician", "The King", "The Wizard"}
        };

        correctAnswers = new int[] {3, 1, 0, 0, 0, 0, 0, 1, 1, 0}; // Index of correct answers in the options array

        currentQuestion = 0;
        score = 0;

        displayQuestion();

        add(mainPanel);
    }

    private void displayQuestion() {
        if (currentQuestion < questions.length) {
            questionLabel = new JLabel(questions[currentQuestion]);
            mainPanel.add(questionLabel, BorderLayout.NORTH);

            optionButtons = new JRadioButton[options[currentQuestion].length];
            buttonGroup = new ButtonGroup();
            JPanel optionsPanel = new JPanel(new GridLayout(4, 1));

            for (int i = 0; i < options[currentQuestion].length; i++) {
                optionButtons[i] = new JRadioButton(options[currentQuestion][i]);
                optionsPanel.add(optionButtons[i]);
                buttonGroup.add(optionButtons[i]);
            }

            mainPanel.add(optionsPanel, BorderLayout.CENTER);

            submitButton = new JButton("Submit");
            mainPanel.add(submitButton, BorderLayout.SOUTH);

            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    checkAnswer();
                    currentQuestion++;
                    mainPanel.removeAll();
                    displayQuestion();
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
            });

            timeRemaining = 20; // Set a 20-second timer for each question
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> {
                        if (timeRemaining <= 0) {
                            timer.cancel();
                            checkAnswer();
                            currentQuestion++;
                            mainPanel.removeAll();
                            displayQuestion();
                            mainPanel.revalidate();
                            mainPanel.repaint();
                        } else {
                            submitButton.setText("Submit (" + timeRemaining + "s)");
                            timeRemaining--;
                        }
                    });
                }
            }, 1000, 1000);
        } else {
            showResults();
        }
    }

    private void checkAnswer() {
        int selectedOption = -1;
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) {
                selectedOption = i;
                break;
            }
        }

        if (selectedOption == correctAnswers[currentQuestion]) {
            score++;
        }
    }

    private void showResults() {
        mainPanel.removeAll();
        JLabel resultLabel = new JLabel("Football Quiz completed! Your score: " + score + " out of " + questions.length);
        mainPanel.add(resultLabel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FootballQuizApp app = new FootballQuizApp();
                app.setVisible(true);
            }
        });
    }
}
