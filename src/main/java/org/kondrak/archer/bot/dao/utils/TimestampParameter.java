package org.kondrak.archer.bot.dao.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by nosferatu on 7/12/17.
 */
public class TimestampParameter implements Parameter<Timestamp> {

    private final Timestamp value;

    public TimestampParameter(Timestamp value) {
        this.value = value;
    }

    @Override
    public void set(int index, PreparedStatement st) throws SQLException {
        st.setTimestamp(index, value);
    }
}
