package org.kondrak.archer.bot.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.dao.mappers.MessageMapper;
import org.kondrak.archer.bot.model.Statistic;
import org.postgresql.ds.PGConnectionPoolDataSource;
import sx.blah.discord.handle.obj.IMessage;

import java.util.List;

/**
 * Created by Administrator on 11/6/2016.
 */
public class MessageDao extends AbstractDao {

    public MessageDao(PGConnectionPoolDataSource ds, SqlSessionFactory factory) {
        super(ds, factory);
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
//        String like = "SELECT 1 FROM message WHERE message_id = ? ";
//        ResultSet result = QueryExecutor.execute(ds, DBOperation.QUERY, like, new StringParameter(messageId));
//
//        Long count = 0L;
//        try {
//            while (result.next()) {
//                count = result.getLong(1);
//            }
//            return count > 0;
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return true;    // if there was an error return true to avoid getting "stuck"
    }
}