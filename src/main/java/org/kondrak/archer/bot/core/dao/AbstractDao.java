package org.kondrak.archer.bot.core.dao;

import org.apache.ibatis.session.SqlSessionFactory;

public abstract class AbstractDao {
    protected final SqlSessionFactory factory;

    public AbstractDao(SqlSessionFactory factory) {
        this.factory = factory;
    }
}
