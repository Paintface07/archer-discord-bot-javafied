package org.kondrak.archer.bot.dao;

import org.postgresql.ds.PGConnectionPoolDataSource;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.obj.User;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Presences;

import javax.sql.PooledConnection;
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

    private final PGConnectionPoolDataSource ds;
    private final IDiscordClient client;

    public UserDao(PGConnectionPoolDataSource ds, IDiscordClient client) {
        this.ds = ds;
        this.client = client;
    }

    public List<IUser> getUsers() {
        PooledConnection pConn = null;
        try {
            pConn = getConnection();
            Connection conn = pConn.getConnection();
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
        } finally {
            if(pConn != null) {
                try {
                    pConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public boolean insertUser(IUser user) {
        PooledConnection pConn = null;
        try {
            pConn = getConnection();
            Connection conn = pConn.getConnection();
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO \"ARCHER\".users (" +
                            "user_id," +
                            "username " +
                            ") VALUES (?, ?)"
            );

            st.setString(1, user.getID());
            st.setString(2, user.getName());

            System.out.println("Inserting user: " + user.getName() + " " + user.getID());
            st.execute();
            return true;
        } catch(SQLException ex) {
            ex.printStackTrace();
            return false;
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

    public boolean userIsSaved(String userId) {
        PooledConnection pConn = null;
        try {
            pConn = getConnection();
            Connection conn = pConn.getConnection();
            PreparedStatement st = conn.prepareStatement(
                    "SELECT user_id " +
                            "FROM \"ARCHER\".users " +
                            "WHERE user_id = ?"
            );

            st.setString(1, userId);
            ResultSet rs = st.executeQuery();
            boolean value = rs.next();
            rs.close();
            st.close();
            return value;
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
        return false;
    }

    private PooledConnection getConnection() throws SQLException {
        return ds.getPooledConnection();
    }

    private ResultSet executeQuery(PreparedStatement st) {
        PooledConnection pConn = null;
        try {
            pConn = getConnection();
            Connection conn = pConn.getConnection();
            ResultSet rs = st.executeQuery();
            rs.close();
            st.close();
            return rs;
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
        return null;
    }

//    PreparedStatement st = conn.prepareStatement(
//            "INSERT INTO \"ARCHER\".users (" +
//                    "user_id," +
//                    "username," +
//                    ") VALUES (?, ?, ?, ?, ?)"
//    );
}
