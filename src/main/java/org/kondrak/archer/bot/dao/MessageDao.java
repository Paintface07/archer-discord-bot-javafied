package org.kondrak.archer.bot.dao;

import org.kondrak.archer.bot.dao.utils.DateUtils;
import sx.blah.discord.handle.obj.IMessage;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Administrator on 11/6/2016.
 */
public class MessageDao {
    private final DataSource ds;

    public MessageDao(DataSource ds) {
        this.ds = ds;
    }

    public void saveMessage(IMessage msg) throws SQLException {
        Connection conn = ds.getConnection();
        PreparedStatement st = conn.prepareStatement(
                "INSERT INTO \"ARCHER\".message (" +
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
    }
}
