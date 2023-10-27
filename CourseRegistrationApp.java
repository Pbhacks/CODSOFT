import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class CourseRegistrationApp extends JFrame {
    private JComboBox<String> courseList;
    private JButton registerButton;
    private JButton recordButton;
    private JButton removeButton;
    private DefaultTableModel tableModel;
    private JTable table;
    private int uidCounter = 1;

    public CourseRegistrationApp() {
        setTitle("Course Registration System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        courseList = new JComboBox<>();
        courseList.addItem("Course 1");
        courseList.addItem("Course 2");
        courseList.addItem("Course 3");
        courseList.addItem("Course 4");
        courseList.addItem("Course 5");
        courseList.addItem("Course 6");
        courseList.addItem("Course 7");
        courseList.addItem("Course 8");
        courseList.addItem("Course 9");
        courseList.addItem("Course 10");

        JPanel registrationPanel = new JPanel(new FlowLayout());
        registrationPanel.add(new JLabel("Select a course: "));
        registrationPanel.add(courseList);
        registerButton = new JButton("Register");
        registrationPanel.add(registerButton);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();
        tableModel.addColumn("UID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Age");
        tableModel.addColumn("Location");
        tableModel.addColumn("Course");
        table = new JTable(tableModel);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        recordButton = new JButton("Record");
        buttonPanel.add(recordButton);
        removeButton = new JButton("Remove");
        buttonPanel.add(removeButton);
        tablePanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(registrationPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = (String) courseList.getSelectedItem();
                if (!selectedCourse.isEmpty()) {
                    StudentRegistrationDialog registrationDialog = new StudentRegistrationDialog(selectedCourse);
                    registrationDialog.setVisible(true);
                }
            }
        });

        recordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter your name:");
                String age = JOptionPane.showInputDialog("Enter your age:");
                String location = JOptionPane.showInputDialog("Enter your location:");
                String selectedCourse = (String) courseList.getSelectedItem();

                if (!name.isEmpty() && !age.isEmpty() && !location.isEmpty() && !selectedCourse.isEmpty()) {
                    tableModel.addRow(new Object[]{uidCounter++, name, age, location, selectedCourse});
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CourseRegistrationApp app = new CourseRegistrationApp();
                app.setVisible(true);
            }
        });
    }

    class StudentRegistrationDialog extends JDialog {
        public StudentRegistrationDialog(String selectedCourse) {
            setTitle("Student Registration");
            setSize(300, 150);
            setModal(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JPanel dialogPanel = new JPanel();
            dialogPanel.setLayout(new GridLayout(4, 2));

            dialogPanel.add(new JLabel("Name:"));
            JTextField nameField = new JTextField();
            dialogPanel.add(nameField);

            dialogPanel.add(new JLabel("Age:"));
            JTextField ageField = new JTextField();
            dialogPanel.add(ageField);

            dialogPanel.add(new JLabel("Location:"));
            JTextField locationField = new JTextField();
            dialogPanel.add(locationField);

            JButton submitButton = new JButton("Submit");
            dialogPanel.add(submitButton);

            add(dialogPanel);

            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();
                    String age = ageField.getText();
                    String location = locationField.getText();

                    if (!name.isEmpty() && !age.isEmpty() && !location.isEmpty()) {
                        tableModel.addRow(new Object[]{uidCounter++, name, age, location, selectedCourse});
                        dispose();
                    }
                }
            });
        }
    }
}
