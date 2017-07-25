package org.kondrak.archer.bot.configuration;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.core.dao.AbstractDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ConfigDao extends AbstractDao {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigDao.class);

    public ConfigDao(SqlSessionFactory factory) {
        super(factory);
    }

    public List<Configuration> getConfigurationByNameScopeAndType(ConfigType type, ConfigScope scope, ConfigDatatype datatype) {
        SqlSession session = factory.openSession();
        try {
            List<Configuration> stats = session.getMapper(ConfigMapper.class).getConfigurationByNameScopeAndType(type, scope, datatype);
            session.close();
            return stats;
        } catch(PersistenceException e) {
            LOG.error("Could not query configuration: ", e);
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

}
