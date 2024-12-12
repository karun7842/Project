
package com.his.AppointmentSchedulingSystem.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import com.his.AppointmentSchedulingSystem.controller.AppointmentController;
import com.his.AppointmentSchedulingSystem.model.Appointment;
import com.his.AppointmentSchedulingSystem.model.Doctor;
import com.his.AppointmentSchedulingSystem.model.Patient;
import com.toedter.calendar.JDateChooser;

public class AppointmentFormDialog extends JDialog {
	JComboBox<Patient> mriIdComboBox;
	private JComboBox<String> departmentComboBox, doctorNameComboBox, availableSlotsComboBox;
	private JComboBox<String> specializationComboBox;
	private JTextField patientNameField, patientPhoneField, patientEmailField;
	private JButton submitButton, cancelButton;
	private Appointment appointment;

	private Doctor doctor;
	private ArrayList<Doctor> doctors;
	private ArrayList<Patient> patients;
	Set<String> specializationArray = new HashSet<String>();
	Set<String> doctorsSet = new HashSet<String>();
	DefaultComboBoxModel<String> specialComboBoxModel = new DefaultComboBoxModel<String>();
	DefaultComboBoxModel<String> doctorComboBoxModel = new DefaultComboBoxModel<String>();
	DefaultComboBoxModel<String> emptyModel = new DefaultComboBoxModel<String>();
	Set<String> departments = new HashSet<String>();

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

		patients = AppointmentController.loadPatients();
		doctors = AppointmentController.loadDoctors();

		formPanel.add(new JLabel("MRI ID:"));
		mriIdComboBox = new JComboBox<Patient>(patients.toArray(new Patient[0]));
		// mriIdComboBox.setEditable(true);

		formPanel.add(mriIdComboBox);

		formPanel.add(new JLabel("Patient Name:"));
		patientNameField = new JTextField();
		formPanel.add(patientNameField);

		formPanel.add(new JLabel("Patient Phone:"));
		patientPhoneField = new JTextField();
		formPanel.add(patientPhoneField);

		formPanel.add(new JLabel("Patient Email:"));
		patientEmailField = new JTextField();
		formPanel.add(patientEmailField);

		formPanel.add(new JSeparator());
		formPanel.add(new JSeparator());

		formPanel.add(new JLabel("Department:"));

		for (Doctor doctor : doctors) {
			departments.add(doctor.getDepartment());
		}

		departmentComboBox = new JComboBox<>(departments.toArray(new String[0]));
		formPanel.add(departmentComboBox);

		// specializationArray = doctors.stream().filter(doctor->
		// doctor.getDepartment().equalsIgnoreCase(departments[departmentComboBox.getSelectedIndex()])).collect(Collectors.toList());

		formPanel.add(new JLabel("Specialization:"));
		// comboBoxModel.addAll(doctors);
		specializationComboBox = new JComboBox<String>();
		formPanel.add(specializationComboBox);

		formPanel.add(new JLabel("Doctor Name:"));
		doctorNameComboBox = new JComboBox<>();
		formPanel.add(doctorNameComboBox);

		formPanel.add(new JLabel("Schedule Assistant:"));
		availableSlotsComboBox = new JComboBox<>(new String[] { "9:00 AM", "10:00 AM", "11:00 AM" });
		formPanel.add(availableSlotsComboBox);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");
		buttonPanel.add(submitButton);
		buttonPanel.add(cancelButton);

		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		add(mainPanel);

		submitButton.addActionListener(e -> submit());
		cancelButton.addActionListener(e -> cancel());

		mriIdComboBox.addActionListener((e) -> {
			if (mriIdComboBox.getSelectedItem() != null) {
				patientNameField.setText(patients.get(mriIdComboBox.getSelectedIndex()).getName());
				patientEmailField.setText(patients.get(mriIdComboBox.getSelectedIndex()).getEmail());
				patientPhoneField
						.setText(new Long(patients.get(mriIdComboBox.getSelectedIndex()).getPhoneNumber()).toString());
			}
		});
		departmentComboBox.addActionListener((e) -> {
			if (departmentComboBox.getSelectedItem() != null) {
				specializationArray.removeAll(specializationArray);
				specialComboBoxModel.removeAllElements();
				specializationComboBox.setModel(specialComboBoxModel);
				for (Doctor doctor : doctors) {
					if (doctor.getDepartment()
							.equals(departments.toArray(new String[0])[departmentComboBox.getSelectedIndex()])) {
						specializationArray.add(doctor.getSpecialization());
					}
				}
				// specializationArray = doctors.stream().filter(doctor->
				// doctor.getDepartment().equalsIgnoreCase(departments[departmentComboBox.getSelectedIndex()]
				// )).collect(Collectors.toList());
				specialComboBoxModel.addAll(specializationArray);
				specializationComboBox.setModel(specialComboBoxModel);
			}
		});
		specializationComboBox.addActionListener((e) -> {
			if (specializationComboBox.getSelectedItem() != null) {
				doctorsSet.removeAll(doctorsSet);
				doctorComboBoxModel.removeAllElements();
				doctorNameComboBox.setModel(doctorComboBoxModel);
				for (Doctor doctor : doctors) {
					if (doctor.getSpecialization().equals(
							specializationArray.toArray(new String[0])[specializationComboBox.getSelectedIndex()])) {
						doctorsSet.add(doctor.getName());
					}
				}
				// specializationArray = doctors.stream().filter(doctor->
				// doctor.getDepartment().equalsIgnoreCase(departments[departmentComboBox.getSelectedIndex()]
				// )).collect(Collectors.toList());
				doctorComboBoxModel.addAll(doctorsSet);
				doctorNameComboBox.setModel(doctorComboBoxModel);
			}
		});

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

			if (patientName.isEmpty() || patientPhone.isEmpty() || patientEmail.isEmpty() || mriId == null
					|| department == null || doctorName == null || availableSlot == null) {
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
			JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
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
