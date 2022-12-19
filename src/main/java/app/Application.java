package app;

import app.appointment.AppointmentController;
import app.appointment.AppointmentDao;
import app.util.Filters;
import org.apache.commons.cli.*;
import spark.Request;
import spark.Response;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.notFound;

public class Application {

    public static AppointmentDao appointmentDao;

    public static void main(String[] args) {

        Options options = new Options();
        options.addOption(Option.builder("port")
                .option("p")
                .longOpt("port")
                .desc("port for server to listen on")
                .hasArg(true)
                .required(true)
                .build());

        CommandLineParser parser = new DefaultParser();

        try {
            start(parser.parse(options, args));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void start(CommandLine cmd) {

        int port = Integer.parseInt(cmd.getOptionValue('p'));

        appointmentDao = new AppointmentDao();

        // Configure server listener

        ipAddress("0.0.0.0");
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
