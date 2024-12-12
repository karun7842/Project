package com.hospitalInformationSystem.AppointmentSchedulingSystem.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hospitalInformationSystem.AppointmentSchedulingSystem.model.Appointment;
import com.hospitalInformationSystem.AppointmentSchedulingSystem.view.AppointmentFormDialog;
import com.hospitalInformationSystem.AppointmentSchedulingSystem.view.AppointmentView;

public class AppointmentController {
	private List<Appointment> appointments;
	private AppointmentView view;
	private List<Appointment> filteredAppointments;
	String filePath = "appointments.json";
	ObjectMapper mapper = new ObjectMapper();

	public AppointmentController(AppointmentView view) {
		mapper.registerModule(new JavaTimeModule());
		this.view = view;
		loadAppointments();

		view.getScheduleButton().addActionListener(e -> scheduleAppointment());
		view.getRescheduleButton().addActionListener(e -> rescheduleAppointment());
		view.getCancelButton().addActionListener(e -> cancelAppointment());
		view.getResetButton().addActionListener(e -> resetFilters());

		view.getFilterMrdField().addActionListener(e -> applyFilters());
		view.getFilterDoctorField().addActionListener(e -> applyFilters());
		view.getFilterSpecialityField().addActionListener(e -> applyFilters());
	}

	public void viewAppointments() {
		loadAppointments();
		updateTable(appointments);
	}

	private void loadAppointments() {
		File file = new File(filePath);
		if (file.exists()) {
			try {
				appointments = mapper.readValue(file, new TypeReference<List<Appointment>>() {});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveAppointments() {
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), appointments);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateTable(List<Appointment> listToDisplay) {
		DefaultTableModel model = view.getTableModel();
		model.setRowCount(0); // Clear existing rows

		for (Appointment a : appointments) {
			model.addRow(new Object[] { a.getPatient().getName(), a.getPatient().getTokenNumber(),
					a.getPatient().getPhoneNumber(), a.getDoctor().getName(), a.getDoctor().getConsultationFee(),
					a.getDoctor().getSpecialization(), a.getAppointmentDate(), a.getAppointmentTime() });
		}
		view.getTableModel().fireTableDataChanged();
	}

	private void applyFilters() {
		int mrdFilter = Integer.parseInt(view.getFilterMrdField().getText());
		String doctorFilter = view.getFilterDoctorField().getText();
		String specialityFilter = view.getFilterSpecialityField().getText();

		filteredAppointments = appointments.stream()
				.filter(a -> (mrdFilter == 0 || a.getPatient().getTokenNumber() == mrdFilter)
						&& (doctorFilter.isEmpty() || a.getDoctor().getName().equalsIgnoreCase(doctorFilter))
						&& (specialityFilter.isEmpty()
								|| a.getDoctor().getSpecialization().equalsIgnoreCase(specialityFilter)))
				.collect(Collectors.toList());

		updateTable(filteredAppointments);
	}

	private void resetFilters() {
		view.getFilterMrdField().setText("");
		view.getFilterDoctorField().setText("");
		view.getFilterSpecialityField().setText("");
		updateTable(appointments);
	}

	private void scheduleAppointment() {

		AppointmentFormDialog formDialog = new AppointmentFormDialog();
		formDialog.setVisible(true);

		Appointment newAppointment = formDialog.getAppointment();

		if (newAppointment != null) {

			appointments.add(newAppointment);

			saveAppointments();

			updateTable(appointments);

		}

	}

	private void rescheduleAppointment() {

		int selectedRow = view.getAppointmentTable().getSelectedRow();

		if (selectedRow != -1) {

			String newTime = JOptionPane.showInputDialog(view.getAppointmentTable(), "Enter new time:");

			String newDate = JOptionPane.showInputDialog(view.getAppointmentTable(), "Enter new date:");

			if (newTime != null) {

				appointments.get(selectedRow).setAppointmentTime(newTime);

				saveAppointments();

				updateTable(appointments);

			}

			if (newDate != null) {

				appointments.get(selectedRow).setAppointmentDate(newDate);

				saveAppointments();

				updateTable(appointments);

			}

		}

	}

	private void cancelAppointment() {

		int selectedRow = view.getAppointmentTable().getSelectedRow();

		if (selectedRow != -1) {

			appointments.remove(selectedRow);

			saveAppointments();

			updateTable(appointments);

		}

	}

}
