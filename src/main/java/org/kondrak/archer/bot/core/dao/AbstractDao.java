package org.kondrak.archer.bot.core.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.postgresql.ds.PGConnectionPoolDataSource;

public abstract class AbstractDao {
    protected final SqlSessionFactory factory;

    public AbstractDao(SqlSessionFactory factory) {
        this.factory = factory;
    }
}
