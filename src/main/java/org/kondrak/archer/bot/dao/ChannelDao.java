package org.kondrak.archer.bot.dao;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.dao.mappers.ChannelMapper;
import org.kondrak.archer.bot.dao.mappers.GuildMapper;
import org.kondrak.archer.bot.dao.utils.DBOperation;
import org.kondrak.archer.bot.dao.utils.QueryExecutor;
import org.kondrak.archer.bot.dao.utils.parameter.BooleanParameter;
import org.kondrak.archer.bot.dao.utils.parameter.LongParameter;
import org.kondrak.archer.bot.dao.utils.parameter.StringParameter;
import org.postgresql.ds.PGConnectionPoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IChannel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2/24/2017.
 */
public class ChannelDao extends AbstractDao {

    public static final Logger LOG = LoggerFactory.getLogger(ChannelDao.class);

    public ChannelDao(PGConnectionPoolDataSource ds, SqlSessionFactory factory) {
        super(ds, factory);
    }

    public boolean channelIsSaved(IChannel channel) {
        SqlSession session = factory.openSession();
        try {
            Integer result = session.getMapper(ChannelMapper.class).channelExists(channel.getStringID());
            session.close();
            return null != result && result > 0;
        } finally {
            session.close();
        }
    }

    public void addChannel(IChannel channel) {
        SqlSession session = factory.openSession();
        try {
            session.getMapper(ChannelMapper.class).addChannel(channel.getStringID(), channel.getName(),
                    channel.isPrivate(), channel.getGuild().getStringID(), channel.getPosition());
            session.commit();
            session.close();
        } catch(PersistenceException ex) {
            LOG.error("Error saving channel: ", ex);
            session.rollback();
        } finally {
            session.close();
        }
    }
}
