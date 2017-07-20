package org.kondrak.archer.bot.dao;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.dao.mappers.MessageMapper;
import org.kondrak.archer.bot.model.Statistic;
import org.postgresql.ds.PGConnectionPoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;

import java.util.List;

/**
 * Created by Administrator on 11/6/2016.
 */
public class MessageDao extends AbstractDao {

    private static final Logger LOG = LoggerFactory.getLogger(MessageDao.class);

    public MessageDao(SqlSessionFactory factory) {
        super(factory);
    }

    public void saveMessage(IMessage msg) {
        SqlSession session = factory.openSession();
        try {
            session.getMapper(MessageMapper.class).saveMessage(
                msg.getChannel().getStringID(),
                msg.getAuthor().getStringID(),
                msg.getStringID(),
                msg.getContent(),
                msg.getCreationDate(),
                (msg.getEditedTimestamp().isPresent() ? msg.getEditedTimestamp().get() : null),
                msg.mentionsEveryone(),
                msg.isPinned());
            session.commit();
            session.close();
        } catch(PersistenceException ex) {
            LOG.error("Error saving message: ", ex);
            session.rollback();
        } finally {
            session.close();
        }
    }

    public List<Statistic> getTimesSaidByUser(final String guild, final String word) {
        SqlSession session = factory.openSession();
        try {
            List<Statistic> stats = session.getMapper(MessageMapper.class)
                    .getMessageCountsForGuildByUser(guild, word);
            session.close();
            return stats;
        } finally {
            session.close();
        }
    }

    public boolean messageExists(final String messageId) {
        SqlSession session = factory.openSession();
        try {
            Integer result = session.getMapper(MessageMapper.class).messageExists(messageId);
            session.close();
            return null != result && result > 0;
        } finally {
            session.close();
        }
    }
}