package parma.edu.reporting.util;

import java.sql.Timestamp;
import java.time.*;

/**
 * Класс утилит для работы с датами.
 */
public final class DateUtils {
    private static final ZoneId CURRENT_ZONE = ZoneId.of("UTC");
    //private static final String FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now();
    }

    public static OffsetDateTime getOffsetFromZonedDateTime(ZonedDateTime date) {
        if (date == null) {
            return null;
        }

        return date.withZoneSameInstant(CURRENT_ZONE).toOffsetDateTime();
    }

    public static ZonedDateTime getZonedDateTimeFromObject(Object date) {
        if (date == null) {
            return null;
        }

        LocalDateTime localDateTime;
        if (date instanceof OffsetDateTime) {
            localDateTime = ((OffsetDateTime)date).toLocalDateTime();
        } else if (date instanceof Timestamp) {
            localDateTime = ((Timestamp)date).toLocalDateTime();
        } else {
            return null;
        }

        return ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
    }

    public static ZonedDateTime getTheBiggestDate() {
        return ZonedDateTime.ofInstant(Instant.MAX, CURRENT_ZONE);
    }

    public static ZonedDateTime getTheSmallestDate() {
        return ZonedDateTime.ofInstant(Instant.MIN, CURRENT_ZONE);
    }
}