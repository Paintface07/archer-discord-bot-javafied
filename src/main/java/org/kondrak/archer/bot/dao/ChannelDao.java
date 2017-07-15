package org.kondrak.archer.bot.dao;

import org.kondrak.archer.bot.dao.utils.DBOperation;
import org.kondrak.archer.bot.dao.utils.QueryExecutor;
import org.kondrak.archer.bot.dao.utils.parameter.BooleanParameter;
import org.kondrak.archer.bot.dao.utils.parameter.LongParameter;
import org.kondrak.archer.bot.dao.utils.parameter.StringParameter;
import org.postgresql.ds.PGConnectionPoolDataSource;
import sx.blah.discord.handle.obj.IChannel;

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
        String query = "SELECT channel_id FROM channel WHERE channel_id = ?";

        ResultSet result = QueryExecutor.execute(ds, DBOperation.QUERY, query, new StringParameter(channel.getID()));

        try {
            return result.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean addChannel(IChannel channel) {
        String query = "INSERT INTO channel (channel_id, name, isprivate, parent, position) VALUES (?, ?, ?, ?, ?)";

        QueryExecutor.execute(ds, DBOperation.INSERT, query,
                new StringParameter(channel.getID()),
                new StringParameter(channel.getName()),
                new BooleanParameter(channel.isPrivate()),
                new StringParameter(channel.getGuild().getID()),
                new LongParameter((long) channel.getPosition()));

        return true;
    }
}
