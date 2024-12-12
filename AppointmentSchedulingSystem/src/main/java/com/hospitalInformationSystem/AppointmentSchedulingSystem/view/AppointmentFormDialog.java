package com.hospitalInformationSystem.AppointmentSchedulingSystem.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import com.hospitalInformationSystem.AppointmentSchedulingSystem.model.Appointment;
import com.hospitalInformationSystem.AppointmentSchedulingSystem.model.Doctor;
import com.hospitalInformationSystem.AppointmentSchedulingSystem.model.Patient;
import com.toedter.calendar.JDateChooser;

public class AppointmentFormDialog extends JDialog {
    private JTextField patientNameField, patientBloodGroupField, patientPhoneField, patientEmailField, patientAddressField;
    private JSpinner patientDobSpinner;
    private JTextField doctorNameField, doctorContactField, doctorEmailField, doctorSpecializationField;
    private JSpinner timeSpinner;
    private JDateChooser dateChooser;
    private JButton submitButton, cancelButton;
    private Appointment appointment;

    public AppointmentFormDialog() {
        setTitle("Schedule Appointment");
        setModal(true);
        setSize(600, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(12, 2, 10, 10));
        mainPanel.add(formPanel, BorderLayout.CENTER);

        
        formPanel.add(new JLabel("Patient Name:"));
        patientNameField = new JTextField();
        formPanel.add(patientNameField);

        formPanel.add(new JLabel("Patient Date of Birth:"));
        patientDobSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dobEditor = new JSpinner.DateEditor(patientDobSpinner, "yyyy-MM-dd");
        patientDobSpinner.setEditor(dobEditor);
        formPanel.add(patientDobSpinner);

        formPanel.add(new JLabel("Patient Blood Group:"));
        patientBloodGroupField = new JTextField();
        formPanel.add(patientBloodGroupField);

        formPanel.add(new JLabel("Patient Phone:"));
        patientPhoneField = new JTextField();
        formPanel.add(patientPhoneField);

        formPanel.add(new JLabel("Patient Email:"));
        patientEmailField = new JTextField();
        formPanel.add(patientEmailField);

        formPanel.add(new JLabel("Patient Address:"));
        patientAddressField = new JTextField();
        formPanel.add(patientAddressField);

       
        formPanel.add(new JLabel("Doctor Name:"));
        doctorNameField = new JTextField();
        formPanel.add(doctorNameField);

        formPanel.add(new JLabel("Doctor Contact:"));
        doctorContactField = new JTextField();
        formPanel.add(doctorContactField);

        formPanel.add(new JLabel("Doctor Email:"));
        doctorEmailField = new JTextField();
        formPanel.add(doctorEmailField);

        formPanel.add(new JLabel("Doctor Specialization:"));
        doctorSpecializationField = new JTextField();
        formPanel.add(doctorSpecializationField);

        
        formPanel.add(new JLabel("Appointment Date:"));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        formPanel.add(dateChooser);

        formPanel.add(new JLabel("Appointment Time:"));
        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "hh:mm a");
        timeSpinner.setEditor(timeEditor);
        formPanel.add(timeSpinner);

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        
        submitButton.addActionListener(e -> submit());
        cancelButton.addActionListener(e -> cancel());
    }

    private void submit() {
        try {
            String patientName = patientNameField.getText();
            LocalDate patientDob = ((Date) patientDobSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String patientBloodGroup = patientBloodGroupField.getText();
            long patientPhone = Long.parseLong(patientPhoneField.getText());
            String patientEmail = patientEmailField.getText();
            String patientAddress = patientAddressField.getText();

            String doctorName = doctorNameField.getText();
            String doctorContact = doctorContactField.getText();
            String doctorEmail = doctorEmailField.getText();
            String doctorSpecialization = doctorSpecializationField.getText();

            LocalDate appointmentDate = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Date appointmentTime = (Date) timeSpinner.getValue();
            SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm a");
            String formattedTime=timeFormat.format(appointmentTime);

            if (patientName.isEmpty() || patientBloodGroup.isEmpty() || patientEmail.isEmpty() || patientAddress.isEmpty()
                    || doctorName.isEmpty() || doctorContact.isEmpty() || doctorEmail.isEmpty()
                    || doctorSpecialization.isEmpty() || appointmentDate == null) {
                JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Patient patient = new Patient(patientName, patientDob, calculateAge(patientDob), patientAddress,
                    patientEmail, patientBloodGroup, patientPhone);

            Doctor doctor = new Doctor();
            doctor.setName(doctorName);
            doctor.setContact(doctorContact);
            doctor.setEmail(doctorEmail);
            doctor.setSpecialization(doctorSpecialization);

            
            appointment = new Appointment(patient, doctor, appointmentDate.toString(),formattedTime);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int calculateAge(LocalDate dob) {
        return LocalDate.now().getYear() - dob.getYear();
    }

    private void cancel() {
        appointment = null;
        dispose();
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
///////////////////
package com.hospitalInformationSystem.AppointmentSchedulingSystem.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import com.hospitalInformationSystem.AppointmentSchedulingSystem.model.Appointment;
import com.hospitalInformationSystem.AppointmentSchedulingSystem.model.Doctor;
import com.hospitalInformationSystem.AppointmentSchedulingSystem.model.Patient;

public class AppointmentFormDialog extends JDialog {
    private JComboBox<String> mriIdComboBox, departmentComboBox, doctorNameComboBox, availableSlotsComboBox;
    private JTextField patientNameField, patientPhoneField, patientEmailField;
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

        JPanel formPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // MRI ID
        formPanel.add(new JLabel("MRI ID:"));
        mriIdComboBox = new JComboBox<>(new String[]{"MRI001", "MRI002", "MRI003"});
        formPanel.add(mriIdComboBox);

        // Patient Details
        formPanel.add(new JLabel("Patient Name:"));
        patientNameField = new JTextField();
        formPanel.add(patientNameField);

        formPanel.add(new JLabel("Patient Phone:"));
        patientPhoneField = new JTextField();
        formPanel.add(patientPhoneField);

        formPanel.add(new JLabel("Patient Email:"));
        patientEmailField = new JTextField();
        formPanel.add(patientEmailField);

        // Separator
        formPanel.add(new JSeparator());
        formPanel.add(new JSeparator());

        // Department
        formPanel.add(new JLabel("Department:"));
        departmentComboBox = new JComboBox<>(new String[]{"Cardiology", "Neurology", "Orthopedics"});
        formPanel.add(departmentComboBox);

        // Doctor Name
        formPanel.add(new JLabel("Doctor Name:"));
        doctorNameComboBox = new JComboBox<>(new String[]{"Dr. Smith", "Dr. Johnson", "Dr. Williams"});
        formPanel.add(doctorNameComboBox);

        // Available Slots
        formPanel.add(new JLabel("Available Slots:"));
        availableSlotsComboBox = new JComboBox<>(new String[]{"9:00 AM", "10:00 AM", "11:00 AM"});
        formPanel.add(availableSlotsComboBox);

        // Submit Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        submitButton.addActionListener(e -> submit());
        cancelButton.addActionListener(e -> cancel());
    }

    private void submit() {
        try {
            String mriId = (String) mriIdComboBox.getSelectedItem();
            String patientName = patientNameField.getText();
            String patientPhone = patientPhoneField.getText();
            String patientEmail = patientEmailField.getText();
            String department = (String) departmentComboBox.getSelectedItem();
            String doctorName = (String) doctorNameComboBox.getSelectedItem();
            String availableSlot = (String) availableSlotsComboBox.getSelectedItem();

            if (patientName.isEmpty() || patientPhone.isEmpty() || patientEmail.isEmpty() || mriId == null || department == null || doctorName == null || availableSlot == null) {
                JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Doctor doctor = new Doctor();
            doctor.setName(doctorName);

            Patient patient = new Patient();
            patient.setName(patientName);
            patient.setPhone(Long.parseLong(patientPhone));
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
