package org.kondrak.archer.bot.dao.utils.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by nosferatu on 7/12/17.
 */
public interface Parameter<T> {
    void set(int index, PreparedStatement st) throws SQLException;
}
