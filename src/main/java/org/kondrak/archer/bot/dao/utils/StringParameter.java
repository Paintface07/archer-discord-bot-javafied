package org.kondrak.archer.bot.dao.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by nosferatu on 7/12/17.
 */
public class StringParameter implements Parameter<String> {

    private final String value;

    public StringParameter(String parameter) {
        this.value = parameter;
    }

    @Override
    public void set(int index, PreparedStatement st) throws SQLException {
        st.setString(index, value);
    }
}
