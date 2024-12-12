import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.border.Border;
import com.toedter.calendar.JDateChooser;

public class AppointmentFormDialog extends JDialog {
    JComboBox<Patient> mriIdComboBox;
    private JComboBox<String> departmentComboBox, doctorNameComboBox, availableSlotsComboBox;
    private JComboBox<String> specializationComboBox;
    private JTextField patientNameField, patientPhoneField, patientEmailField;
    private JDateChooser appointmentDateChooser;
    private JSpinner appointmentTimeSpinner;
    private JButton submitButton, cancelButton;
    private Appointment appointment;

    public AppointmentFormDialog() {
        setTitle("Schedule Appointment");
        setModal(true);
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add form components
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("MRI ID:"), gbc);
        mriIdComboBox = new JComboBox<>();
        mriIdComboBox.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        formPanel.add(mriIdComboBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Patient Name:"), gbc);
        patientNameField = new JTextField();
        patientNameField.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        formPanel.add(patientNameField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Patient Phone:"), gbc);
        patientPhoneField = new JTextField();
        patientPhoneField.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        formPanel.add(patientPhoneField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Patient Email:"), gbc);
        patientEmailField = new JTextField();
        patientEmailField.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        formPanel.add(patientEmailField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Appointment Date:"), gbc);
        appointmentDateChooser = new JDateChooser();
        appointmentDateChooser.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        formPanel.add(appointmentDateChooser, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Appointment Time:"), gbc);
        appointmentTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(appointmentTimeSpinner, "HH:mm");
        appointmentTimeSpinner.setEditor(timeEditor);
        appointmentTimeSpinner.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        formPanel.add(appointmentTimeSpinner, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Department:"), gbc);
        departmentComboBox = new JComboBox<>();
        departmentComboBox.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        formPanel.add(departmentComboBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Specialization:"), gbc);
        specializationComboBox = new JComboBox<>();
        specializationComboBox.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        formPanel.add(specializationComboBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Doctor Name:"), gbc);
        doctorNameComboBox = new JComboBox<>();
        doctorNameComboBox.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        formPanel.add(doctorNameComboBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Schedule Assistant:"), gbc);
        availableSlotsComboBox = new JComboBox<>(new String[]{"9:00 AM", "10:00 AM", "11:00 AM"});
        availableSlotsComboBox.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        formPanel.add(availableSlotsComboBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Add event listeners
        submitButton.addActionListener(e -> submit());
        cancelButton.addActionListener(e -> cancel());
    }

    private void submit() {
        try {
            LocalDate appointmentDate = appointmentDateChooser.getDate()
                .toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
            java.util.Date appointmentTime = (java.util.Date) appointmentTimeSpinner.getValue();

            String mriId = (String) mriIdComboBox.getSelectedItem();
            String patientName = patientNameField.getText();
            String patientPhone = patientPhoneField.getText();
            String patientEmail = patientEmailField.getText();
            String department = (String) departmentComboBox.getSelectedItem();
            String doctorName = (String) doctorNameComboBox.getSelectedItem();
            String availableSlot = (String) availableSlotsComboBox.getSelectedItem();

            if (patientName.isEmpty() || patientPhone.isEmpty() || patientEmail.isEmpty() ||
                mriId == null || department == null || doctorName == null ||
                availableSlot == null || appointmentDate == null || appointmentTime == null) {
                JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Doctor doctor = new Doctor();
            doctor.setName(doctorName);

            Patient patient = new Patient();
            patient.setName(patientName);
            patient.setPhoneNumber(Long.parseLong(patientPhone));
            patient.setEmail(patientEmail);

            appointment = new Appointment(patient, doctor, department, availableSlot);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancel() {
        appointment = null;
        dispose();
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
