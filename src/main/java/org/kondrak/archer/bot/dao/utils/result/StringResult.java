package org.kondrak.archer.bot.dao.utils.result;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nosferatu on 7/13/17.
 */
public class StringResult extends AbstractResult<String> implements Result<String> {

    public StringResult(ResultSet rs, int index) {
        super(rs, index);
    }

    @Override
    public String get() throws SQLException {
        return getResultSet().getString(getIndex());
    }
}
