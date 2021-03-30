package no.entra.bacnet.json.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeHelper {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC));

    public static String iso8601DateTime() {
        return iso8601DateTime(Instant.now());
    }

    public static String iso8601DateTime(Instant instant) {
        return formatter.format( instant.truncatedTo(ChronoUnit.MILLIS));
    }

    public static Instant fromIso8601Json(String jsonDateTime) {
        return Instant.parse(jsonDateTime);
    }
}
