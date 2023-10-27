import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradeCalculatorApp extends JFrame {

    private JLabel[] subjectLabels;
    private JTextField[] subjectTextFields;
    private JButton calculateButton;
    private JButton resetButton;
    private JLabel totalMarksLabel;
    private JLabel averagePercentageLabel;
    private JLabel gradeLabel;

    public GradeCalculatorApp() {
        setTitle("Grade Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 2));

        int numSubjects = 5; // You can change this to the number of subjects you want to consider.

        subjectLabels = new JLabel[numSubjects];
        subjectTextFields = new JTextField[numSubjects];

        for (int i = 0; i < numSubjects; i++) {
            subjectLabels[i] = new JLabel("Subject " + (i + 1) + " Marks:");
            subjectTextFields[i] = new JTextField(5);
            mainPanel.add(subjectLabels[i]);
            mainPanel.add(subjectTextFields[i]);
        }

        calculateButton = new JButton("Calculate");
        resetButton = new JButton("Reset");
        mainPanel.add(calculateButton);
        mainPanel.add(resetButton);

        totalMarksLabel = new JLabel("Total Marks: ");
        averagePercentageLabel = new JLabel("Average Percentage: ");
        gradeLabel = new JLabel("Grade: ");

        mainPanel.add(totalMarksLabel);
        mainPanel.add(averagePercentageLabel);
        mainPanel.add(gradeLabel);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateResults();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        add(mainPanel);
    }

    private void calculateResults() {
        int totalMarks = 0;
        int numSubjects = subjectTextFields.length;

        for (int i = 0; i < numSubjects; i++) {
            try {
                int marks = Integer.parseInt(subjectTextFields[i].getText());
                totalMarks += marks;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input for Subject " + (i + 1));
                return;
            }
        }

        double averagePercentage = (double) totalMarks / (numSubjects * 100) * 100;
        String grade = getGrade(averagePercentage);

        totalMarksLabel.setText("Total Marks: " + totalMarks);
        averagePercentageLabel.setText("Average Percentage: " + averagePercentage + "%");
        gradeLabel.setText("Grade: " + grade);
    }

    private void resetFields() {
        for (JTextField textField : subjectTextFields) {
            textField.setText("");
        }

        totalMarksLabel.setText("Total Marks: ");
        averagePercentageLabel.setText("Average Percentage: ");
        gradeLabel.setText("Grade: ");
    }

    private String getGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return "A+";
        } else if (averagePercentage >= 80) {
            return "A";
        } else if (averagePercentage >= 70) {
            return "B";
        } else if (averagePercentage >= 60) {
            return "C";
        } else if (averagePercentage >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GradeCalculatorApp app = new GradeCalculatorApp();
                app.setVisible(true);
            }
        });
    }
}
