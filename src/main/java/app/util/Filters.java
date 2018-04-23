package app.util;

import app.appointment.Appointment;
import spark.Filter;
import spark.Request;
import spark.Response;

import static app.util.JsonUtil.jsonToData;

public class Filters {

    public static Filter parseAppointmentJson = (Request req, Response res) -> {
        if (req.requestMethod().equals("POST") || req.requestMethod().equals("PUT")) {
            Appointment appt = (Appointment) jsonToData(req.body(), Appointment.class);
            req.attribute("appt", appt);
        }
    };

    public static Filter addJsonHeader = (Request req, Response res) -> {
        res.header("Content-Type", "application/json");
    };
}
