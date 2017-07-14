package org.kondrak.archer.bot.dao.utils.result;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nosferatu on 7/13/17.
 */
public class LongResult extends AbstractResult<Long> implements Result<Long> {

    public LongResult(ResultSet rs, int index) {
        super(rs, index);
    }

    @Override
    public Long get() throws SQLException {
        return getResultSet().getLong(getIndex());
    }
}
