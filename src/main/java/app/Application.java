package app;

import app.appointment.AppointmentController;
import app.appointment.AppointmentDao;
import app.util.Filters;
import spark.Request;
import spark.Response;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.notFound;

public class Application {

    public static String PORT = "4567";

    public static AppointmentDao appointmentDao;

    public static void main(String[] args) {

        int port = Integer.parseInt(System.getProperty("server.port", PORT));

        appointmentDao = new AppointmentDao();

        // Configure Spark
        port(port);

        // Configure filters
        // Add content-type headers for all routes
        // parse JSON body into Appointment instance on POST/PUT routes
        before("*", Filters.addJsonHeader);
        before("*", Filters.parseAppointmentJson);

        // Routing URLs to controllers
        get("/", AppointmentController.getRecentAppointments);
        get("/all", AppointmentController.getAllAppointments);
        get("/:year/:month", AppointmentController.getAppointmentsByYearAndMonth);
        post("/", AppointmentController.createAppointment);
        put("/:id", AppointmentController.updateAppointment);

        // Not found handler
        notFound((Request req, Response res) -> {
            res.header("Content-Type", "text/html");
            return "Not found";
        });

        appointmentDao.bootstrap();
    }
}
