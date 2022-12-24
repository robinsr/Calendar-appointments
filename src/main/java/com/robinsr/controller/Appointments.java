package com.robinsr.controller;


import com.robinsr.model.Appointment;
import com.robinsr.service.AppointmentService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Path("/appointments")
public class Appointments {

    AppointmentService appService;

    @Autowired
    public Appointments(AppointmentService appService) {
        this.appService = appService;
    }

    @GET
    @Produces("application/json")
    public List<Appointment> getRecent() {
        return this.appService.getRecent();
    }

    @GET
    @Path("all")
    @Produces("application/json")
    public List<Appointment> getAll() {
        return this.appService.getAll();
    }

    @GET()
    @Path("/{year}/{month}")
    @Produces("application/json")
    public List<Appointment> getMonth(@PathParam("year") int year, @PathParam("month") int month) {
        return this.appService.getMonth(year, month);
    }
}
