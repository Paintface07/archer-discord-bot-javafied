package org.kondrak.archer.bot.dao;

import org.kondrak.archer.bot.dao.utils.DateUtils;
import org.postgresql.ds.PGConnectionPoolDataSource;
import sx.blah.discord.handle.obj.IMessage;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
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
        PooledConnection pConn = null;
        try {
            pConn = ds.getPooledConnection();
            Connection conn = pConn.getConnection();
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO message (" +
                            "channel_id," +
                            "author," +
                            "message_id," +
                            "content," +
                            "created," +
                            "edited," +
                            "everyone," +
                            "ispinned" +
                            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );

            st.setString(1, msg.getChannel().getID());
            st.setString(2, msg.getAuthor().getID());
            st.setString(3, msg.getID());
            st.setString(4, msg.getContent());
            st.setTimestamp(5, DateUtils.formatDate(msg.getCreationDate()));
            st.setTimestamp(6, msg.getEditedTimestamp().isPresent() ? DateUtils.formatDate(msg.getEditedTimestamp().get()) : null);
            st.setBoolean(7, msg.mentionsEveryone());
            st.setBoolean(8, msg.isPinned());

            st.execute();
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(pConn != null) {
                try {
                    pConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Map<String, Long> getTimesSaidByUser(final String word) {
        String searchWord = "%" + word.toUpperCase()
                .replace("'", "''")
                .replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![") + "%";

        String like = "SELECT users.username, count(*) " +
                "FROM message " +
                "  JOIN users " +
                "    ON message.author = users.user_id " +
                "WHERE UPPER(content) LIKE ? ESCAPE '!'" +
                "AND content NOT LIKE '%!word%' " +
                "group by users.username";

        ResultSet resultSet = execute(ds, like, searchWord);

        Map<String, Long> result = new HashMap<>();
        try {
            while(resultSet.next()) {
                String username = resultSet.getString(1);
                Long count = resultSet.getLong(2);
                result.put(username, count);
            }

            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new HashMap<>();
    }

    public boolean messageExists(final String messageId) {
        String like = "SELECT 1 " +
                    "FROM message " +
                    "WHERE message_id = ? ";
        ResultSet result = execute(ds, like, messageId);

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

    public static ResultSet execute(PGConnectionPoolDataSource ds, String query, String... arguments) {
        if(arguments.length > 0) {
            PooledConnection pConn = null;
            try {
                pConn = ds.getPooledConnection();
                Connection conn = pConn.getConnection();
                System.out.println(query);
                PreparedStatement st = conn.prepareStatement(query);

                for (int a = 0; a < arguments.length; a++) {
                    st.setString(a + 1, arguments[a]);
                }

                return st.executeQuery();

            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if (pConn != null) {
                    try {
                        pConn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}