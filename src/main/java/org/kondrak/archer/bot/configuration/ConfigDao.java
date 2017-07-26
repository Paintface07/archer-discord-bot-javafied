package org.kondrak.archer.bot.configuration;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.core.dao.AbstractDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;

import java.util.ArrayList;
import java.util.List;

public class ConfigDao extends AbstractDao {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigDao.class);

    public ConfigDao(SqlSessionFactory factory) {
        super(factory);
    }

    public List<Configuration> getConfigurationByNameScopeAndType(ConfigType type, ConfigScope scope, String fkey) {
        SqlSession session = factory.openSession();
        try {
            List<Configuration> stats = session.getMapper(ConfigMapper.class).getConfigurationByNameScopeAndType(type, scope, fkey);
            session.close();
            return stats;
        } catch(PersistenceException e) {
            LOG.error("Could not query configuration: ", e);
        } finally {
            session.close();
        }
        return new ArrayList<>();
    }

    public void addConfiguration(ConfigType parameter, ConfigScope scope, String msg) {
        SqlSession session = factory.openSession();
        try {
            session.getMapper(ConfigMapper.class).addConfiguration(parameter, scope, msg);
            session.close();
        } catch(PersistenceException e) {
            LOG.error("Could not add configuration: ", e);
        } finally {
            session.close();
        }
    }
}
