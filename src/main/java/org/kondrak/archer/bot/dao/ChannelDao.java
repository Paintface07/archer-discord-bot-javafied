package org.kondrak.archer.bot.dao;

import org.postgresql.ds.PGConnectionPoolDataSource;
import sx.blah.discord.handle.obj.IChannel;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2/24/2017.
 */
public class ChannelDao {
    private final PGConnectionPoolDataSource ds;

    public ChannelDao(PGConnectionPoolDataSource ds) {
        this.ds = ds;
    }

    public boolean channelIsSaved(IChannel channel) {
        PooledConnection pConn = null;
        try {
            pConn = ds.getPooledConnection();
            Connection conn = pConn.getConnection();
            PreparedStatement st = conn.prepareStatement(
                    "SELECT channel_id " +
                            "FROM channel " +
                            "WHERE channel_id = ?"
            );

            st.setString(1, channel.getID());
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

    public boolean addChannel(IChannel channel) {
        PooledConnection pConn = null;
        try {
            pConn = ds.getPooledConnection();
            Connection conn = pConn.getConnection();
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO channel (channel_id, name, isprivate, parent, position) VALUES (?, ?, ?, ?, ?)"
            );

            st.setString(1, channel.getID());
            st.setString(2, channel.getName());
            st.setBoolean(3, channel.isPrivate());
            st.setString(4, channel.getGuild().getID());
            st.setLong(5, channel.getPosition());

            st.execute();
            return true;
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
}
