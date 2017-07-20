package org.kondrak.archer.bot.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.dao.utils.DBOperation;
import org.kondrak.archer.bot.dao.utils.QueryExecutor;
import org.kondrak.archer.bot.dao.utils.parameter.BooleanParameter;
import org.kondrak.archer.bot.dao.utils.parameter.StringParameter;
import org.kondrak.archer.bot.dao.utils.result.StringResult;
import org.kondrak.archer.bot.model.User;
import org.postgresql.ds.PGConnectionPoolDataSource;
import sx.blah.discord.handle.obj.IUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/6/2016.
 */
public class UserDao extends AbstractDao {

    public UserDao(PGConnectionPoolDataSource ds, SqlSessionFactory factory) {
        super(ds, factory);
    }

    public List<User> getUsers() {
        String query = "SELECT user_id, username FROM users";

        ResultSet resultSet = QueryExecutor.execute(ds, DBOperation.QUERY, query);

        List<User> users = new ArrayList<>();
        try {
            while(resultSet.next()) {
                users.add(new User(new StringResult(resultSet, 2).get(),
                        new StringResult(resultSet, 1).get()));
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
                new StringParameter(Long.toUnsignedString(user.getLongID())),
                new StringParameter(user.getName()),
                new StringParameter(user.getAvatar()),
                new StringParameter(user.getAvatarURL()),
                new StringParameter(user.getDiscriminator()),
                new BooleanParameter(user.isBot()));

        return true;
    }

    public boolean userIsSaved(String userId) {
        String query = "SELECT user_id FROM users WHERE user_id = ?";

        ResultSet result = QueryExecutor.execute(ds, DBOperation.QUERY, query,
                new StringParameter(userId));

        try {
            return result.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
