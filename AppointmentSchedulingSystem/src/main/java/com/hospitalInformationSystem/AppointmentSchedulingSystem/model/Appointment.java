package com.hospitalInformationSystem.AppointmentSchedulingSystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Appointment {
@JsonProperty("patient")
	private Patient patient;

@JsonProperty("doctor")
	private Doctor doctor;

@JsonProperty("appointment_Date")
	private String appointmentDate;

@JsonProperty("department")
	private String department;

@JsonProperty("consultation_fee")
	private double consultationFee;

@JsonProperty("appointment_time")
	private String appointmentTime;

	public Patient getPatient() {
		return patient;
	}

	public Appointment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Appointment(Patient patient, Doctor doctor, String appointmentDate, String appointmentTime) {
		super();
		this.patient = patient;
		this.doctor = doctor;
		this.appointmentDate = appointmentDate;
		this.department = doctor.getSpecialization();
		this.consultationFee = doctor.getConsultationFee();
		this.appointmentTime = appointmentTime;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public double getConsultationFee() {
		return consultationFee;
	}

	public void setConsultationFee(double consultationFee) {
		this.consultationFee = consultationFee;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
}