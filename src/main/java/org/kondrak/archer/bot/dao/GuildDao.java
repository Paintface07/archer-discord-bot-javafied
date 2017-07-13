package org.kondrak.archer.bot.dao;

import org.kondrak.archer.bot.dao.utils.DBOperation;
import org.kondrak.archer.bot.dao.utils.QueryExecutor;
import org.kondrak.archer.bot.dao.utils.StringParameter;
import org.postgresql.ds.PGConnectionPoolDataSource;
import sx.blah.discord.handle.obj.IGuild;

import javax.management.Query;
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
        String query = "SELECT guild_id " +
                            "FROM guild " +
                            "WHERE guild_id = ?";

        ResultSet resultSet = QueryExecutor.execute(ds, DBOperation.QUERY, query, new StringParameter(guild.getID()));
        try {
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addGuild(IGuild guild) {
        String query = "INSERT INTO guild (guild_id, name, icon, iconurl, owner_id) VALUES (?, ?, ?, ?, ?)";

        QueryExecutor.execute(ds, DBOperation.INSERT, query,
                new StringParameter(guild.getID()),
                new StringParameter(guild.getName()),
                new StringParameter(guild.getIcon()),
                new StringParameter(guild.getIconURL()),
                new StringParameter(guild.getOwnerID()));
    }
}
