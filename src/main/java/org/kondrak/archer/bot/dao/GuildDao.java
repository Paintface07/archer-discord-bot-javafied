package org.kondrak.archer.bot.dao;

import org.postgresql.ds.PGConnectionPoolDataSource;
import sx.blah.discord.handle.obj.IGuild;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2/24/2017.
 */
public class GuildDao {
    private PGConnectionPoolDataSource ds;

    public GuildDao(PGConnectionPoolDataSource ds) {
        this.ds = ds;
    }

    public boolean guildIsSaved(IGuild guild) {
        PooledConnection pConn = null;
        try {
            pConn = ds.getPooledConnection();
            Connection conn = pConn.getConnection();
            PreparedStatement st = conn.prepareStatement(
                    "SELECT guild_id " +
                            "FROM guild " +
                            "WHERE guild_id = ?"
            );

            st.setString(1, guild.getID());
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

    public boolean addGuild(IGuild guild) {
        PooledConnection pConn = null;
        try {
            pConn = ds.getPooledConnection();
            Connection conn = pConn.getConnection();
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO guild (guild_id, name, icon, iconurl, owner_id) VALUES (?, ?, ?, ?, ?)"
            );

            st.setString(1, guild.getID());
            st.setString(2, guild.getName());
            st.setString(3, guild.getIcon());
            st.setString(4, guild.getIconURL());
            st.setString(5, guild.getOwnerID());

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
