import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class Attendance {
    private String studentName;
    private String attendanceStatus;
    private String date;

    public Attendance(String studentName, String attendanceStatus, String date) {
        this.studentName = studentName;
        this.attendanceStatus = attendanceStatus;
        this.date = date;
    }

   
    public String getStudentName() {
        return studentName;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public String getDate() {
        return date;
    }
}

public class AttendanceSystem {

    private JFrame mainFrame;
    private JFrame attendanceFrame;
    private JTextField dateField;
    private ArrayList<Attendance> attendanceList;
    private ArrayList<JRadioButton> presentButtons;
    private ArrayList<JRadioButton> absentButtons;

    public AttendanceSystem() {
        attendanceList = new ArrayList<>();
        presentButtons = new ArrayList<>();
        absentButtons = new ArrayList<>();
        setupMainFrame();
    }

    private void setupMainFrame() {
        mainFrame = new JFrame("Attendance System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 150);
        mainFrame.setLayout(new FlowLayout());

        JButton takeAttendanceButton = new JButton("Take Attendance");
        takeAttendanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setupAttendanceFrame();
            }
        });

        mainFrame.add(takeAttendanceButton);
        mainFrame.setVisible(true);
    }

    private void setupAttendanceFrame() {
        attendanceFrame = new JFrame("Attendance Page");
        attendanceFrame.setSize(400, 400);
        attendanceFrame.setLayout(new BorderLayout());

        JPanel datePanel = new JPanel();
        datePanel.add(new JLabel("Date (dd/MM/yyyy):"));
        dateField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), 10);
        datePanel.add(dateField);

        JPanel attendancePanel = new JPanel();
        attendancePanel.setLayout(new GridLayout(0, 3));
        
        String[] students = {"Alice", "Bob", "Charlie", "David"};
        for (String student : students) {
            JLabel nameLabel = new JLabel(student);
            JRadioButton presentButton = new JRadioButton("Present");
            JRadioButton absentButton = new JRadioButton("Absent");
            presentButtons.add(presentButton);
            absentButtons.add(absentButton);
            
            ButtonGroup group = new ButtonGroup();
            group.add(presentButton);
            group.add(absentButton);

            attendancePanel.add(nameLabel);
            attendancePanel.add(presentButton);
            attendancePanel.add(absentButton);
        }

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAttendance(students);
            }
        });

        attendanceFrame.add(datePanel, BorderLayout.NORTH);
        attendanceFrame.add(attendancePanel, BorderLayout.CENTER);
        attendanceFrame.add(submitButton, BorderLayout.SOUTH);
        attendanceFrame.setVisible(true);
    }

    private void saveAttendance(String[] students) {
        String date = dateField.getText();
        if (!isValidDate(date)) {
            JOptionPane.showMessageDialog(attendanceFrame, "Please enter a valid date in dd/MM/yyyy format.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < students.length; i++) {
            String status = presentButtons.get(i).isSelected() ? "Present" : (absentButtons.get(i).isSelected() ? "Absent" : "Not Selected");
            if (!status.equals("Not Selected")) {
                attendanceList.add(new Attendance(students[i], status, date));
            }
        }

        JOptionPane.showMessageDialog(attendanceFrame, "Attendance successfully saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
        attendanceFrame.dispose();
    }

    private boolean isValidDate(String date) {
        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        new AttendanceSystem();
    }
}
