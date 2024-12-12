package com.hospitalInformationSystem.AppointmentSchedulingSystem.model;

import java.time.LocalDate;

public class Patient {
	static private int tokenCounter;
    private String name;
    private LocalDate dob;
    private int age;
    private String address;
    private String email;
    private String bloodGroup;
    private long phoneNumber;
    private int tokenNumber;
    public Patient() {
    	
    }
 
   
    public Patient(String name, LocalDate dob, int age, String address, String email, String bloodGroup, long phoneNumber) {
        this.name = name;
        this.dob = dob;
        this.age = age;
        this.address = address;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.phoneNumber = phoneNumber;
        this.tokenNumber=++tokenCounter;
    }
 
    public String getName() {
    	return name;
    	}
    public LocalDate getDob() {
    	return dob;
    	}
    public int getAge() {
    	return age;
    	}
    public String getAddress() {
    	return address;
    	}
    public String getEmail() {
    	return email;
    	}
    public String getBloodGroup() {
    	return bloodGroup;
    	}
    public long getPhoneNumber() {
    	return phoneNumber;
    	}
    public int getTokenNumber() {
    	return tokenNumber;
    }
    public void setTokenNumber(int tokenNumber) {
    	this.tokenNumber=tokenNumber;
    }
 
	public static int getTokenCounter() {
		return tokenCounter;
	}
 
	public static void setTokenCounter(int tokenCounter) {
		Patient.tokenCounter = tokenCounter;
	}
    
}