package org.kondrak.archer.bot.dao;

import org.kondrak.archer.bot.dao.utils.*;
import org.kondrak.archer.bot.dao.utils.parameter.BooleanParameter;
import org.kondrak.archer.bot.dao.utils.parameter.StringParameter;
import org.kondrak.archer.bot.dao.utils.parameter.TimestampParameter;
import org.kondrak.archer.bot.dao.utils.result.LongResult;
import org.kondrak.archer.bot.dao.utils.result.StringResult;
import org.postgresql.ds.PGConnectionPoolDataSource;
import sx.blah.discord.handle.obj.IMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 11/6/2016.
 */
public class MessageDao {
    private final PGConnectionPoolDataSource ds;

    public MessageDao(PGConnectionPoolDataSource ds) {
        this.ds = ds;
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

    public Map<String, Long> getTimesSaidByUser(final String word) {
        String searchWord = "%" + QueryExecutor.sanitize(word.toUpperCase()) + "%";

        // TODO: make the bot's "self" ID configurable, or dynamically populated
        String like = "SELECT users.username, count(*) " +
                "FROM message " +
                "  JOIN users " +
                "    ON message.author = users.user_id " +
                "WHERE UPPER(content) LIKE ? ESCAPE '!'" +
                "AND content NOT LIKE '%!word%' " +
                "AND author <> '239471420470591498'" +
                "group by users.username";

        ResultSet resultSet = QueryExecutor.execute(ds, DBOperation.QUERY, like, new StringParameter(searchWord));

        Map<String, Long> result = new HashMap<>();
        try {
            while(resultSet.next()) {
                String username = new StringResult(resultSet, 1).get();
                Long count = new LongResult(resultSet, 2).get();
                result.put(username, count);
            }

            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new HashMap<>();
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