package org.kondrak.archer.bot.dao.utils.result;

import java.sql.SQLException;

/**
 * Created by nosferatu on 7/13/17.
 */
public interface Result<T> {
    T get() throws SQLException;
}
