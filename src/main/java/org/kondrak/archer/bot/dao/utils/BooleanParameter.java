package org.kondrak.archer.bot.dao.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by nosferatu on 7/12/17.
 */
public class BooleanParameter implements Parameter<Boolean> {

    private final Boolean value;

    public BooleanParameter(boolean value) {
        this.value = value;
    }

    @Override
    public void set(int index, PreparedStatement st) throws SQLException {
        st.setBoolean(index, value);
    }
}
