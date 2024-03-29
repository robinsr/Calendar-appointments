package com.robinsr.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import org.joda.time.DateTime;

import app.util.DateFormats;
import org.joda.time.format.DateTimeFormat;

import java.util.UUID;

@Data
public class Appointment {
    String title;
    String description;
    DateTime dateTime;
    String date;
    String time;
    String id;
    UUID owner;

    @JsonGetter("dateTime")
    public String getDateTimeJson() {
        return dateTime.toString(DateFormats.DATETIME);
    }

    @JsonSetter("dateTime")
    public void setDateTimeFromJson(String dateTimeString) {
        this.dateTime = DateTimeFormat.forPattern(DateFormats.DATETIME).parseDateTime(dateTimeString);
    }
}
