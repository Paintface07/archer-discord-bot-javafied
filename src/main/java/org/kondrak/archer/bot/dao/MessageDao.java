package org.kondrak.archer.bot.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.dao.mappers.StatisticMapper;
import org.kondrak.archer.bot.dao.utils.DBOperation;
import org.kondrak.archer.bot.dao.utils.DateUtils;
import org.kondrak.archer.bot.dao.utils.QueryExecutor;
import org.kondrak.archer.bot.dao.utils.parameter.BooleanParameter;
import org.kondrak.archer.bot.dao.utils.parameter.StringParameter;
import org.kondrak.archer.bot.dao.utils.parameter.TimestampParameter;
import org.kondrak.archer.bot.model.Statistic;
import org.postgresql.ds.PGConnectionPoolDataSource;
import sx.blah.discord.handle.obj.IMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 11/6/2016.
 */
public class MessageDao extends AbstractDao {

    public MessageDao(PGConnectionPoolDataSource ds, SqlSessionFactory factory) {
        super(ds, factory);
    }

    public void saveMessage(IMessage msg) {
        String insert = "INSERT INTO message (" +
                            "channel_id," +
                            "author," +
                            "message_id," +
                            "content," +
                            "created," +
                            "edited," +
                            "everyone," +
                            "ispinned" +
                            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        QueryExecutor.execute(ds, DBOperation.INSERT, insert,
                new StringParameter(msg.getChannel().getID()),
                new StringParameter(msg.getAuthor().getID()),
                new StringParameter(msg.getID()),
                new StringParameter(msg.getContent()),
                new TimestampParameter(DateUtils.formatDate(msg.getCreationDate())),
                new TimestampParameter(msg.getEditedTimestamp().isPresent() ? DateUtils.formatDate(msg.getEditedTimestamp().get()) : null),
                new BooleanParameter(msg.mentionsEveryone()),
                new BooleanParameter(msg.isPinned()));
    }

    public List<Statistic> getTimesSaidByUser(final String guild, final String word) {
        SqlSession session = factory.openSession();
        try {
            return session.getMapper(StatisticMapper.class)
                    .getMessageCountsForGuildByUser(guild, word);
        } finally {
            session.close();
        }
    }

    public boolean messageExists(final String messageId) {
        String like = "SELECT 1 FROM message WHERE message_id = ? ";
        ResultSet result = QueryExecutor.execute(ds, DBOperation.QUERY, like, new StringParameter(messageId));

        Long count = 0L;
        try {
            while (result.next()) {
                count = result.getLong(1);
            }
            return count > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;    // if there was an error return true to avoid getting "stuck"
    }
}