import java.util.stream.Collectors;

public class AppointmentController {
    private List<Appointment> appointments;
    private AppointmentView view;
    private List<Appointment> filteredAppointments;

    public AppointmentController(AppointmentView view) {
        this.view = view;
        appointments = new ArrayList<>();
        filteredAppointments = new ArrayList<>();
        loadAppointments();

        view.getScheduleButton().addActionListener(e -> scheduleAppointment());
        view.getRescheduleButton().addActionListener(e -> rescheduleAppointment());
        view.getCancelButton().addActionListener(e -> cancelAppointment());
        view.getResetButton().addActionListener(e -> resetFilters());
        
        view.getFilterMrdField().addActionListener(e -> applyFilters());
        view.getFilterDoctorField().addActionListener(e -> applyFilters());
        view.getFilterSpecialityField().addActionListener(e -> applyFilters());
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

    private void updateTable(List<Appointment> listToDisplay) {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0); // Clear existing rows

        for (Appointment a : listToDisplay) {
            model.addRow(new Object[] { a.getPatient().getName(), a.getPatient().getTokenNumber(),
                    a.getPatient().getPhoneNumber(), a.getDoctor().getName(), a.getDoctor().getConsultationFee(),
                    a.getDoctor().getSpecialization(), a.getAppointmentDate(), a.getAppointmentTime() });
        }
    }

    private void applyFilters() {
        String mrdFilter = view.getFilterMrdField().getText();
        String doctorFilter = view.getFilterDoctorField().getText();
        String specialityFilter = view.getFilterSpecialityField().getText();

        filteredAppointments = appointments.stream()
            .filter(a -> (mrdFilter.isEmpty() || a.getPatient().getTokenNumber().equalsIgnoreCase(mrdFilter)) &&
                         (doctorFilter.isEmpty() || a.getDoctor().getName().equalsIgnoreCase(doctorFilter)) &&
                         (specialityFilter.isEmpty() || a.getDoctor().getSpecialization().equalsIgnoreCase(specialityFilter)))
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
