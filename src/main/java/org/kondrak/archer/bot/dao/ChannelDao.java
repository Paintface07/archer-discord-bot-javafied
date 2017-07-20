package org.kondrak.archer.bot.dao;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.dao.mappers.ChannelMapper;
import org.postgresql.ds.PGConnectionPoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IChannel;

/**
 * Created by Administrator on 2/24/2017.
 */
public class ChannelDao extends AbstractDao {

    public static final Logger LOG = LoggerFactory.getLogger(ChannelDao.class);

    public ChannelDao(SqlSessionFactory factory) {
        super(factory);
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
