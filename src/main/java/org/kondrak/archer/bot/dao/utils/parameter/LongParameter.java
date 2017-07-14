package org.kondrak.archer.bot.dao.utils.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by nosferatu on 7/13/17.
 */
public class LongParameter implements Parameter<Long> {

    private final Long value;

    public LongParameter(Long value) {
        this.value = value;
    }

    @Override
    public void set(int index, PreparedStatement st) throws SQLException {
        st.setLong(index, value);
    }
}
