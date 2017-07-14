package org.kondrak.archer.bot.dao.utils.result;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nosferatu on 7/13/17.
 */
public abstract class AbstractResult<T> implements Result<T> {

    private final ResultSet resultSet;
    private final int index;

    AbstractResult(ResultSet rs, int index) {
        this.resultSet = rs;
        this.index = index;
    }

    public abstract T get() throws SQLException;

    ResultSet getResultSet() {
        return resultSet;
    }

    int getIndex() {
        return index;
    }
}
