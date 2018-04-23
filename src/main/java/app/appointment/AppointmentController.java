package app.appointment;

import java.util.UUID;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;
import spark.Route;

import static app.Application.appointmentDao;
import static app.util.JsonUtil.dataToJson;

public class AppointmentController {

    public static Route getAllAppointments = (Request req, Response res) ->
        dataToJson(appointmentDao.getAppointments());

    public static Route getRecentAppointments = (Request req, Response res) -> {
        DateTime now = new DateTime();
        int year = now.getYear();
        int month = now.getMonthOfYear();
        return dataToJson(appointmentDao.getByYearAndMonth(year, month));
    };

    public static Route getAppointmentsByYearAndMonth = (Request req, Response res) -> {
        int year = Integer.parseInt(req.params(":year"));
        int month = Integer.parseInt(req.params(":month"));
        return dataToJson(appointmentDao.getByYearAndMonth(year, month));
    };

    public static Route createAppointment = (Request req, Response res) -> {
        Appointment appt = req.attribute("appt");
        appt.setId(UUID.randomUUID().toString());
        return dataToJson(appointmentDao.updateAppointment(appt));
    };

    public static Route updateAppointment = (Request req, Response res) ->
        dataToJson(appointmentDao.updateAppointment(req.attribute("appt")));
}
