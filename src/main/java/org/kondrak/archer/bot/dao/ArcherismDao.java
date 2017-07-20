package org.kondrak.archer.bot.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.dao.mappers.ArcherismMapper;
import org.kondrak.archer.bot.model.Archerism;
import org.kondrak.archer.bot.model.Statistic;
import org.postgresql.ds.PGConnectionPoolDataSource;

import java.util.List;

/**
 * Created by Administrator on 2/24/2017.
 */
public class ArcherismDao extends AbstractDao {

    public ArcherismDao(PGConnectionPoolDataSource ds, SqlSessionFactory factory) {
        super(ds, factory);
    }

    public List<Archerism> getArcherisms() {
        SqlSession session = factory.openSession();
        try {
            List<Archerism> stats = session.getMapper(ArcherismMapper.class)
                    .getAllArcherisms();
            session.close();
            return stats;
        } finally {
            session.close();
        }
    }
}
