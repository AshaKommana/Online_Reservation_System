package reservation;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

public class ReservationForm extends JFrame {

    private JTextField nameField, trainNoField, trainNameField, dateField, fromField, toField;
    private JComboBox<String> classBox;
    private JButton submitBtn;

    // Train Number → Train Name mapping
    private HashMap<String, String> trainMap = new HashMap<>();

    public ReservationForm() {

        setTitle("Train Reservation System");
        setSize(500, 520);
        setLayout(new GridLayout(10, 2, 15, 15));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // === LOAD TRAIN LIST ===
        loadTrainList();

        // Passenger Name
        add(new JLabel("Passenger Name:"));
        nameField = new JTextField();
        add(nameField);

        // Train Number
        add(new JLabel("Train Number:"));
        trainNoField = new JTextField();
        add(trainNoField);

        // Train Name (auto-filled)
        add(new JLabel("Train Name:"));
        trainNameField = new JTextField();
        trainNameField.setEditable(false);
        add(trainNameField);

        // Class Type
        add(new JLabel("Class Type:"));
        classBox = new JComboBox<>(new String[]{"Sleeper", "AC", "General"});
        add(classBox);

        // Date
        add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        add(dateField);

        // From Place
        add(new JLabel("From:"));
        fromField = new JTextField();
        add(fromField);

        // To Place
        add(new JLabel("To:"));
        toField = new JTextField();
        add(toField);

        // Submit Button
        submitBtn = new JButton("Reserve Ticket");
        add(submitBtn);

        // Cancellation Page Button
        JButton cancelFormBtn = new JButton("Go to Cancellation Page");
        add(cancelFormBtn);

        // Auto-fill train name when typing
        trainNoField.addCaretListener(e -> autoFillTrainName());

        // Validation + Save
        submitBtn.addActionListener(e -> validateAndSave());

        // Open cancellation page
        cancelFormBtn.addActionListener(e -> new CancellationForm().setVisible(true));
    }

    // === LOAD TRAIN LIST INTO HASHMAP ===
    private void loadTrainList() {
        trainMap.put("101", "Intercity Express");
        trainMap.put("102", "Rajdhani Express");
        trainMap.put("103", "Shatabdi Express");
        trainMap.put("12001", "New Delhi Shatabdi");
        trainMap.put("12627", "Karnataka Express");
        trainMap.put("12723", "Telangana Express");
        trainMap.put("12220", "Mumbai Duronto");
        trainMap.put("22691", "Rajdhani Bangalore");
        trainMap.put("12841", "Coromandel Express");
        trainMap.put("11019", "Konark Express");
        trainMap.put("12296", "Sanghamitra Express");
    }

    // === AUTO-FILL TRAIN NAME ===
    private void autoFillTrainName() {
        String trainNo = trainNoField.getText().trim();

        if (trainMap.containsKey(trainNo)) {
            trainNameField.setText(trainMap.get(trainNo));
        } else {
            trainNameField.setText("");
        }
    }

    // === VALIDATION + SAVING TO DATABASE ===
    private void validateAndSave() {

        if (nameField.getText().isEmpty() ||
                trainNoField.getText().isEmpty() ||
                trainNameField.getText().isEmpty() ||
                dateField.getText().isEmpty() ||
                fromField.getText().isEmpty() ||
                toField.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "❌ Please fill all fields!");
            return;
        }

        reserveTicket();
    }

    private void reserveTicket() {
        try {
            Connection con = DatabaseConnection.getConnection();

            String query = "INSERT INTO reservations(name, train_no, train_name, class_type, journey_date, from_place, to_place) VALUES(?,?,?,?,?,?,?)";

            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, nameField.getText());
            pst.setString(2, trainNoField.getText());
            pst.setString(3, trainNameField.getText());
            pst.setString(4, classBox.getSelectedItem().toString());
            pst.setString(5, dateField.getText());
            pst.setString(6, fromField.getText());
            pst.setString(7, toField.getText());

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this,
                        "🎉 Reservation Successful!\nPNR is generated automatically.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
