package com.robinsr.service;

import app.util.DateFormats;
import com.robinsr.model.Appointment;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@Profile("default")
public class TestAppointmentService implements AppointmentService {

    private static Logger log = LogManager.getLogger(TestAppointmentService.class);

    private List<Appointment> bootstrapApps;

    private List<UUID> sampleUsers;

    public TestAppointmentService() {
        this.sampleUsers = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            sampleUsers.add(UUID.randomUUID());
        }


        log.info("Sample users: {}", sampleUsers);

        this.bootstrapApps = new ArrayList<>();

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
            newAppt.setOwner(sampleUsers.get(i % 4));

            this.bootstrapApps.add(newAppt);
        }
    }


    @Override
    public List<Appointment> getRecent() {
        int month = DateTime.now().getMonthOfYear();
        int year = DateTime.now().getYear();

        return this.getMonth(year, month);
    }

    @Override
    public List<Appointment> getAll() {
        return this.bootstrapApps;
    }

    @Override
    public List<Appointment> getMonth(int year, int month) {
        return this.bootstrapApps.stream()
                .filter(a -> a.getDateTime().year().get() == year)
                .filter(a -> a.getDateTime().monthOfYear().get() == month)
                .collect(Collectors.toList());
    }
}
