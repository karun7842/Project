package com.hospitalInformationSystem.AppointmentSchedulingSystem.controller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	private String filePath = "appointments.json";
	ObjectMapper mapper = new ObjectMapper();

	public AppointmentController(AppointmentView view) {
		mapper.registerModule(new JavaTimeModule());
		this.view = view;
		appointments = new ArrayList<>();
		loadAppointments();
		
		
		view.getScheduleButton().addActionListener(e -> scheduleAppointment());
		view.getRescheduleButton().addActionListener(e -> rescheduleAppointment());
		view.getCancelButton().addActionListener(e -> cancelAppointment());
	}

	private void loadAppointments() {
		
		File file = new File(filePath);
		if (file.exists()) {
			try {
				appointments = mapper.readValue(file, new TypeReference<List<Appointment>>() {
				});
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

	private void updateTable() {
		DefaultTableModel model = view.getTableModel();
		model.setRowCount(0);
		for (Appointment a : appointments) {
			model.addRow(new Object[] { a.getPatient().getName(), a.getPatient().getTokenNumber(),
					a.getPatient().getPhoneNumber(), a.getDoctor().getName(), a.getDoctor().getConsultationFee(),
					a.getDoctor().getSpecialization(), a.getAppointmentDate(), a.getAppointmentTime() });
		}
	}

	public void viewAppointments() {
		loadAppointments();
		updateTable();
		view.getAppointmentTable().setVisible(true);
	}

	private void scheduleAppointment() {
		AppointmentFormDialog formDialog = new AppointmentFormDialog();
		formDialog.setVisible(true);

		Appointment newAppointment = formDialog.getAppointment();
		if (newAppointment != null) {
			appointments.add(newAppointment);
			saveAppointments();
			updateTable();
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
				updateTable();
			}
			if (newDate != null) {
				appointments.get(selectedRow).setAppointmentDate(newDate);
				saveAppointments();
				updateTable();
			}
		}
	}

	private void cancelAppointment() {
		int selectedRow = view.getAppointmentTable().getSelectedRow();
		if (selectedRow != -1) {
			appointments.remove(selectedRow);
			saveAppointments();
			updateTable();
		}
	}
}