package org.kondrak.archer.bot.dao;

import sx.blah.discord.handle.obj.IUser;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Administrator on 11/6/2016.
 */
public class UserDao {

    private final DataSource ds;

    public UserDao(DataSource ds) {
        this.ds = ds;
    }

    public void getUser(IUser user) throws SQLException {
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

//        st.setString(1, msg.getChannel().getID());

        st.executeQuery();
    }
}
