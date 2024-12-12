package com.hospitalInformationSystem.AppointmentSchedulingSystem;

import com.hospitalInformationSystem.AppointmentSchedulingSystem.controller.AppointmentController;
import com.hospitalInformationSystem.AppointmentSchedulingSystem.view.AppointmentView;

public class AppointmentScheduling {
    public static void main(String[] args) {
        AppointmentView view = new AppointmentView();
        AppointmentController controller = new  AppointmentController(view);
        controller.viewAppointments();
        view.display();
    }
}