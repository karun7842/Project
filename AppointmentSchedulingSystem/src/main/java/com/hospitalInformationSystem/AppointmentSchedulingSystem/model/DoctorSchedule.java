package com.hospitalInformationSystem.AppointmentSchedulingSystem.model;

import java.util.List;

public class DoctorSchedule {
    private String doctorName;
    private List<String> availableSlots; 
    private List<String> bookedSlots;

    public DoctorSchedule(String doctorName, List<String> availableSlots, List<String> bookedSlots) {
        this.doctorName = doctorName;
        this.availableSlots = availableSlots;
        this.bookedSlots = bookedSlots;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public List<String> getAvailableSlots() {
        return availableSlots;
    }

    public List<String> getBookedSlots() {
        return bookedSlots;
    }
}