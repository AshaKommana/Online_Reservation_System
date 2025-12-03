package reservation;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CancellationForm extends JFrame {

    private JTextField pnrField;
    private JTextArea resultArea;
    private JButton searchBtn, cancelBtn;

    public CancellationForm() {
        setTitle("Ticket Cancellation");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Enter PNR:"));
        pnrField = new JTextField(10);
        topPanel.add(pnrField);

        searchBtn = new JButton("Search");
        topPanel.add(searchBtn);

        add(topPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        cancelBtn = new JButton("Cancel Ticket");
        cancelBtn.setEnabled(false);
        add(cancelBtn, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> searchPNR());
        cancelBtn.addActionListener(e -> cancelTicket());
    }

    private void searchPNR() {
        String pnr = pnrField.getText();

        try {
            Connection con = DatabaseConnection.getConnection();

            String query = "SELECT * FROM reservations WHERE pnr=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, pnr);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String details =
                        "PNR: " + rs.getInt("pnr") + "\n" +
                                "Name: " + rs.getString("name") + "\n" +
                                "Train No: " + rs.getString("train_no") + "\n" +
                                "Train Name: " + rs.getString("train_name") + "\n" +
                                "Class: " + rs.getString("class_type") + "\n" +
                                "Date: " + rs.getString("journey_date") + "\n" +
                                "From: " + rs.getString("from_place") + "\n" +
                                "To: " + rs.getString("to_place");

                resultArea.setText(details);
                cancelBtn.setEnabled(true);

            } else {
                resultArea.setText("No ticket found with PNR " + pnr);
                cancelBtn.setEnabled(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cancelTicket() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel this ticket?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) return;

        try {
            Connection con = DatabaseConnection.getConnection();

            String query = "DELETE FROM reservations WHERE pnr=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, pnrField.getText());

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Ticket Cancelled Successfully!");
                resultArea.setText("");
                cancelBtn.setEnabled(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
