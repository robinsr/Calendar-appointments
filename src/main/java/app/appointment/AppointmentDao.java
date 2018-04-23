package app.appointment;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import lombok.Getter;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import app.util.DateFormats;

public class AppointmentDao {

    @Getter
    private final List<Appointment> appointments = new ArrayList<>();

    public List<Appointment> getByYearAndMonth(int year, int month) {
        return appointments.stream()
                .filter(a -> a.getDateTime().monthOfYear().get() == month)
                .filter(a -> a.getDateTime().year().get() == year)
                .collect(Collectors.toList());
    }

    public Appointment updateAppointment(Appointment newApp) {
        appointments.removeIf(a -> a.getId().equals(newApp.getId()));
        appointments.add(newApp);
        return newApp;
    }

    public void bootstrap() {
        Lorem lorem = LoremIpsum.getInstance();
        long start = DateTime.now().minusYears(1).getMillis();
        long end = DateTime.now().plusYears(1).getMillis();
        ThreadLocalRandom t = ThreadLocalRandom.current();

        for (int i = 0; i < 500; i++) {
            long randomMillis = t.nextLong(start, end);
            DateTime now = new DateTime(randomMillis);

            Appointment newAppt = new Appointment();

            newAppt.setId(UUID.randomUUID().toString());
            newAppt.setDateTime(now);
            newAppt.setDate(now.toString(DateFormats.DATE));
            newAppt.setTime(now.toString(DateFormats.TIME));
            newAppt.setDescription(lorem.getWords(10, 20));
            newAppt.setTitle(lorem.getTitle(2, 5));

            appointments.add(newAppt);
        }
    }

}
