package org.kondrak.archer.bot.dao;

import org.kondrak.archer.bot.context.ArcherBotContext;
import org.kondrak.archer.bot.dao.utils.parameter.BooleanParameter;
import org.kondrak.archer.bot.dao.utils.DBOperation;
import org.kondrak.archer.bot.dao.utils.QueryExecutor;
import org.kondrak.archer.bot.dao.utils.parameter.StringParameter;
import org.kondrak.archer.bot.model.User;
import org.postgresql.ds.PGConnectionPoolDataSource;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.obj.PresenceImpl;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.StatusType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Administrator on 11/6/2016.
 */
public class UserDao {

    private final PGConnectionPoolDataSource ds;
    private final IDiscordClient client;

    public UserDao(ArcherBotContext ctx) {
        this.ds = ctx.getDatasource();
        this.client = ctx.getClient();
    }

    public List<User> getUsers() {
        String query = "SELECT user_id, username FROM users";

        ResultSet resultSet = QueryExecutor.execute(ds, DBOperation.QUERY, query);

        List<User> users = new ArrayList<>();
        try {
            while(resultSet.next()) {
                users.add(new User(client, resultSet.getString(2), resultSet.getString(1),
                        "", "", new PresenceImpl(Optional.empty(), Optional.empty(), StatusType.ONLINE), false));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public boolean insertUser(IUser user) {
        String query = "INSERT INTO users (" +
                "user_id," +
                "username," +
                "avatar," +
                "avatarurl," +
                "discriminator," +
                "isbot" +
                ") VALUES (?, ?, ?, ?, ?, ?)";

        QueryExecutor.execute(ds, DBOperation.INSERT, query,
                new StringParameter(user.getID()),
                new StringParameter(user.getName()),
                new StringParameter(user.getAvatar()),
                new StringParameter(user.getAvatarURL()),
                new StringParameter(user.getDiscriminator()),
                new BooleanParameter(user.isBot()));

        return true;
    }

    public boolean userIsSaved(String userId) {
        String query = "SELECT user_id FROM users WHERE user_id = ?";

        ResultSet result = QueryExecutor.execute(ds, DBOperation.QUERY, query, new StringParameter(userId));

        try {
            return result.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
