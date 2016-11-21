package org.kondrak.archer.bot.dao.utils;

import java.sql.PreparedStatement;

/**
 * Created by Administrator on 11/19/2016.
 */
public interface FieldMapper<I, O> {
    PreparedStatement mapTo(PreparedStatement p, int position, O value);
}
