package org.kondrak.archer.bot.dao.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Administrator on 11/19/2016.
 */
public class LongToLongFieldMapper implements FieldMapper<Long, Long> {
    private Long translate(Long input) {
        return input;
    }

    @Override
    public PreparedStatement mapTo(PreparedStatement p, int position, Long value) {
        try {
            p.setLong(position, translate(value));
            return p;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
