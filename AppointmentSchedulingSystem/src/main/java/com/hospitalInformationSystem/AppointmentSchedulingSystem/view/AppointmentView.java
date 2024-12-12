package com.hospitalInformationSystem.AppointmentSchedulingSystem.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AppointmentView {
    private JFrame frame;
    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private JButton viewButton, scheduleButton, rescheduleButton, cancelButton, resetButton;
    private JTextField filterMrdField, filterDoctorField, filterSpecialityField;

    public AppointmentView() {
        frame = new JFrame("Appointment Scheduling System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Appointment Scheduling System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        setupTable();
        setupFilters();
        setupButtons();
    }

    private void setupTable() {
        String[] columns = { "Patient Name", "MRD ID", "Contact Info", "Doctor Name", "Consultation fee", 
                             "Speciality", "Appointment Date", "Appointment Time","Status" };
        tableModel = new DefaultTableModel(columns, 0);
        appointmentTable = new JTable(tableModel);
//        appointmentTable.setVisible(false);

        appointmentTable.setVisible(true);

        frame.add(new JScrollPane(appointmentTable), BorderLayout.CENTER);
    }

    private void setupFilters() {
        JPanel filterPanel = new JPanel();
        filterMrdField = new JTextField(10);
        filterDoctorField = new JTextField(10);
        filterSpecialityField = new JTextField(10);

        filterPanel.add(new JLabel(" MRD ID:"));
        filterPanel.add(filterMrdField);
        filterPanel.add(new JLabel("Doctor:"));
        filterPanel.add(filterDoctorField);
        filterPanel.add(new JLabel("Speciality:"));
        filterPanel.add(filterSpecialityField);

        frame.add(filterPanel, BorderLayout.NORTH);


        private void setupFilters() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BorderLayout());
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(1, 6, 10, 10));

        // MRD Filter
        JLabel mrdLabel = new JLabel("Filter by MRD ID:");
        filterMrdField = new JTextField();
        fieldsPanel.add(mrdLabel);
        fieldsPanel.add(filterMrdField);

        // Doctor Filter
        JLabel doctorLabel = new JLabel("Filter by Doctor:");
        filterDoctorField = new JTextField();
        fieldsPanel.add(doctorLabel);
        fieldsPanel.add(filterDoctorField);

        // Speciality Filter
        JLabel specialityLabel = new JLabel("Filter by Speciality:");
        filterSpecialityField = new JTextField();
        fieldsPanel.add(specialityLabel);
        fieldsPanel.add(filterSpecialityField);

        filterPanel.add(fieldsPanel, BorderLayout.NORTH);
        frame.add(filterPanel, BorderLayout.SOUTH);
        }
    }

    private void setupButtons() {
        JPanel buttonPanel = new JPanel();

        scheduleButton = new JButton("Add appointment");
        rescheduleButton = new JButton("Reschedule");
        cancelButton = new JButton("Cancel");
        resetButton = new JButton("Reset");

        buttonPanel.add(scheduleButton);
        buttonPanel.add(rescheduleButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(resetButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void display() {
        frame.setVisible(true);
    }

    public JTable getAppointmentTable() {
        return appointmentTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getViewButton() {
        return viewButton;
    }

    public JButton getScheduleButton() {
        return scheduleButton;
    }

    public JButton getRescheduleButton() {
        return rescheduleButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JTextField getFilterMrdField() {
        return filterMrdField;
    }

    public JTextField getFilterDoctorField() {
        return filterDoctorField;
    }

    public JTextField getFilterSpecialityField() {
        return filterSpecialityField;
    }
}
