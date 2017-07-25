package org.kondrak.archer.bot.archerism;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.core.dao.AbstractDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/24/2017.
 */
public class ArcherismDao extends AbstractDao {

    public static final Logger LOG = LoggerFactory.getLogger(ArcherismDao.class);

    public ArcherismDao(SqlSessionFactory factory) {
        super(factory);
    }

    public List<Archerism> getArcherisms() {
        SqlSession session = factory.openSession();
        try {
            List<Archerism> stats = session.getMapper(ArcherismMapper.class)
                    .getAllArcherisms();
            session.commit();
            session.close();
            return stats;
        } catch(PersistenceException ex) {
            LOG.error("Error selecting archerisms: ", ex);
            session.rollback();
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }
}
