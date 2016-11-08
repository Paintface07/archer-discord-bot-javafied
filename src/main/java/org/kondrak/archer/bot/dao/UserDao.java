package org.kondrak.archer.bot.dao;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.obj.User;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Presences;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/6/2016.
 */
public class UserDao {

    private final DataSource ds;
    private final IDiscordClient client;

    public UserDao(DataSource ds, IDiscordClient client) {
        this.ds = ds;
        this.client = client;
    }

    public List<IUser> getUsers() {
        try {
            Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(
                    "SELECT user_id," +
                            "username " +
                            "FROM \"ARCHER\".users"
            );

            ResultSet rs = st.executeQuery();
            List<IUser> users = new ArrayList<>();
            while (rs.next()) {
                IUser user = new User(client, rs.getString(2), rs.getString(1), "", "", Presences.ONLINE, false);
                users.add(user);
            }
            rs.close();
            st.close();
            return users;
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertUser(IUser user) {
        try {
            Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO \"ARCHER\".users (" +
                            "user_id," +
                            "username " +
                            ") VALUES (?, ?)"
            );

            st.setString(1, user.getID());
            st.setString(2, user.getName());

            st.execute();
            return true;
        } catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean userIsSaved(String userId) {
        try {
            Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(
                    "SELECT user_id," +
                            "FROM \"ARCHER\".users" +
                            "WHERE user_id = ?"
            );

            st.setString(1, userId);
            ResultSet rs = st.executeQuery();
            List<IUser> users = new ArrayList<>();
            boolean value = rs.next();
            rs.close();
            st.close();
            return value;
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

//    PreparedStatement st = conn.prepareStatement(
//            "INSERT INTO \"ARCHER\".users (" +
//                    "user_id," +
//                    "username," +
//                    ") VALUES (?, ?, ?, ?, ?)"
//    );
}
