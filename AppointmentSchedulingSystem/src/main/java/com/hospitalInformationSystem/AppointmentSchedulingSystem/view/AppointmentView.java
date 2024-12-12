package com.hospitalInformationSystem.AppointmentSchedulingSystem.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AppointmentView {
    private JFrame frame;
    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private JButton viewButton, scheduleButton, rescheduleButton, cancelButton;

    public AppointmentView() {
        frame = new JFrame("Appointment Scheduling System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Appointment Scheduling System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        setupTable();
        setupButtons();
    }

    private void setupTable() {
        String[] columns = { "Patient Name", "MRD ID", "Contact Info", "Doctor Name","Consultation fee","Speciality","AppointmentDate","Appointment Time" };
        tableModel = new DefaultTableModel(columns, 0);
        appointmentTable = new JTable(tableModel);
        appointmentTable.setVisible(false); 
        
        frame.add(new JScrollPane(appointmentTable), BorderLayout.CENTER);
    }

    private void setupButtons() {
        JPanel buttonPanel = new JPanel();
        
        scheduleButton = new JButton("Add appointment");
        rescheduleButton = new JButton("Reschedule");
        cancelButton = new JButton("Cancel");

        
        buttonPanel.add(scheduleButton);
        buttonPanel.add(rescheduleButton);
        buttonPanel.add(cancelButton);

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
}