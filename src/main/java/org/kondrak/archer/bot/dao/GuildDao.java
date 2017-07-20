package org.kondrak.archer.bot.dao;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.dao.mappers.GuildMapper;
import org.kondrak.archer.bot.dao.mappers.MessageMapper;
import org.kondrak.archer.bot.dao.utils.DBOperation;
import org.kondrak.archer.bot.dao.utils.QueryExecutor;
import org.kondrak.archer.bot.dao.utils.parameter.StringParameter;
import org.postgresql.ds.PGConnectionPoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IGuild;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2/24/2017.
 */
public class GuildDao extends AbstractDao {

    private static final Logger LOG = LoggerFactory.getLogger(GuildDao.class);

    public GuildDao(PGConnectionPoolDataSource ds, SqlSessionFactory factory) {
        super(ds, factory);
    }

    public boolean guildIsSaved(IGuild guild) {
        SqlSession session = factory.openSession();
        try {
            Integer result = session.getMapper(GuildMapper.class).guildExists(guild.getStringID());
            session.close();
            return null != result && result > 0;
        } finally {
            session.close();
        }
    }

    public void addGuild(IGuild guild) {
        SqlSession session = factory.openSession();
        try {
            session.getMapper(GuildMapper.class).insertGuild(guild.getStringID(), guild.getName(),
                    guild.getIcon(), guild.getIconURL(), guild.getOwner().getStringID());
            session.commit();
            session.close();
        } catch(PersistenceException ex) {
            LOG.error("Error saving guild: ", ex);
            session.rollback();
        } finally {
            session.close();
        }
    }
}
