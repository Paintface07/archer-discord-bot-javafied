package org.kondrak.archer.bot.dao;

import org.kondrak.archer.bot.dao.utils.DateUtils;
import org.postgresql.ds.PGConnectionPoolDataSource;
import sx.blah.discord.handle.obj.IMessage;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        PooledConnection pConn = null;
        try {
            String like = "SELECT users.username, count(*) " +
                    "FROM message " +
                    "  JOIN users " +
                    "    ON message.author = users.user_id " +
                    "WHERE UPPER(content) LIKE '%" + word.toUpperCase() + "%' " +
                    "group by users.username";

            pConn = ds.getPooledConnection();
            Connection conn = pConn.getConnection();
            PreparedStatement st = conn.prepareStatement(like);

            Map<String, Long> result = new HashMap<>();
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                String username = rs.getString(1);
                Long count = rs.getLong(2);
                result.put(username, count);
            }
            return result;
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

        return new HashMap<>();
    }
}