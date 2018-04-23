package app

import app.appointment.Appointment
import app.util.DateFormats
import org.joda.time.DateTime
import spock.lang.Specification
import com.mashape.unirest.http.Unirest


class ApplicationTest extends Specification {
    def port = Integer.parseInt(System.getProperty("server.port", Application.PORT));
    def host = "http://localhost"
    def baseUrl = host + ":" + port
    def newAppt = "{" +
        "\"title\" : \"Test Appointment\"," +
        "\"description\" : \"Don't miss this appointment\"," +
        "\"date\" : \"11/11/2018\"," +
        "\"time\" : \"12:00:00 AM\"," +
        "\"dateTime\" : \"11/11/2018 12:00:00 AM\"" +
        "}"

    void setupSpec() {
        Application.main(null)
        Unirest.setObjectMapper(new UnirestJacksonMapper())
    }

    def "Get recent appointments (GET /)"() {
        def appts = Unirest.get(baseUrl + "/")
            .header("Accept", "application/json")
            .asObject(Appointment[].class)
            .getBody()

        expect:
            assert appts.size() != 0

            appts.each { appt ->
                assert appt.id != null
                assert appt.title != null
                assert appt.description != null
                assert appt.dateTime != null
                assert appt.date != null
                assert appt.time != null
            }
    }

    def "Create new appointment (POST /)"() {
        def request = Unirest.post(baseUrl)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(newAppt)
                .asObject(Appointment.class)

        def appt = request.getBody();

        expect:
            appt.getTitle() == "Test Appointment"
            appt.getDescription() == "Don't miss this appointment"
            appt.getDate() == "11/11/2018"
            appt.getTime() == "12:00:00 AM"
            appt.getDateTime().toString(DateFormats.DATETIME) == "11/11/2018 12:00:00 AM"
    }

    def "Get appointments for a month (GET /:year/:month)"() {
        def now = DateTime.now()

        def appts = Unirest.get(baseUrl + "/" + now.year + "/" + now.monthOfYear)
            .asObject(Appointment[].class).getBody()

        expect:
            appts.each { appt ->
                assert appt.getDateTime().monthOfYear == now.monthOfYear
                assert appt.getDateTime().year == now.year
            }
    }

    def "Update appointment (PUT /:id)"() {
        def appt = Unirest.post(baseUrl)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(newAppt)
                .asObject(Appointment.class)
                .getBody();

        def id = appt.getId();

        appt.setTitle("An updated appointment title")

        def updatedAppt = Unirest.put(baseUrl + "/" + id)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .body(appt)
            .asObject(Appointment.class)
            .getBody()

        expect:
            updatedAppt.getTitle() == "An updated appointment title"
    }
}
