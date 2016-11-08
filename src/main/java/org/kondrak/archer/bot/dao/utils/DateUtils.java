package org.kondrak.archer.bot.dao.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by Administrator on 11/6/2016.
 */
public final class DateUtils {
    private DateUtils() {
        // empty by design
    }

    public static Timestamp formatDate(LocalDateTime ts) {
        return new Timestamp(ts.toInstant(ZoneOffset.UTC).toEpochMilli());
    }
}
