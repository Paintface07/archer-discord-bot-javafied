package org.kondrak.archer.bot.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.postgresql.ds.PGConnectionPoolDataSource;

abstract class AbstractDao {
    protected final PGConnectionPoolDataSource ds;
    protected final SqlSessionFactory factory;

    AbstractDao(PGConnectionPoolDataSource ds, SqlSessionFactory factory) {
        this.ds = ds;
        this.factory = factory;
    }
}
