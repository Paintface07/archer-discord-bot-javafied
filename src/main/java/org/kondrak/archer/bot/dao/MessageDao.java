package org.kondrak.archer.bot.dao;

import org.kondrak.archer.bot.dao.utils.DateUtils;
import org.postgresql.ds.PGConnectionPoolDataSource;
import sx.blah.discord.handle.obj.IMessage;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
                            "created" +
                            ") VALUES (?, ?, ?, ?, ?)"
            );

            st.setString(1, msg.getChannel().getID());
            st.setString(2, msg.getAuthor().getID());
            st.setString(3, msg.getID());
            st.setString(4, msg.getContent());
            st.setTimestamp(5, DateUtils.formatDate(msg.getCreationDate()));

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
}