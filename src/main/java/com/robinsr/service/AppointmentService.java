package com.robinsr.service;

import com.robinsr.model.Appointment;

import java.util.List;

public interface AppointmentService {

    List<Appointment> getRecent();
    List<Appointment> getAll();
    List<Appointment> getMonth(int year, int month);
}
