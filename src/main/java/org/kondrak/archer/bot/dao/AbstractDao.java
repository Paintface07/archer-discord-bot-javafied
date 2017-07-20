package org.kondrak.archer.bot.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.postgresql.ds.PGConnectionPoolDataSource;

abstract class AbstractDao {
    protected final SqlSessionFactory factory;

    AbstractDao(SqlSessionFactory factory) {
        this.factory = factory;
    }
}
