package com.robinsr.config;

import com.robinsr.controller.Appointments;
import com.robinsr.service.AppointmentService;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(Appointments.class);
        register(AppointmentService.class);
    }
}
