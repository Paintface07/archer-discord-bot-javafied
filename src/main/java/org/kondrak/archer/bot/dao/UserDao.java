package org.kondrak.archer.bot.dao;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.dao.mappers.MessageMapper;
import org.kondrak.archer.bot.dao.mappers.UserMapper;
import org.kondrak.archer.bot.dao.utils.DBOperation;
import org.kondrak.archer.bot.dao.utils.QueryExecutor;
import org.kondrak.archer.bot.dao.utils.parameter.BooleanParameter;
import org.kondrak.archer.bot.dao.utils.parameter.StringParameter;
import org.kondrak.archer.bot.dao.utils.result.StringResult;
import org.kondrak.archer.bot.model.User;
import org.postgresql.ds.PGConnectionPoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/6/2016.
 */
public class UserDao extends AbstractDao {

    private static final Logger LOG = LoggerFactory.getLogger(UserDao.class);

    public UserDao(PGConnectionPoolDataSource ds, SqlSessionFactory factory) {
        super(ds, factory);
    }

//    public List<User> getUsers() {
//        String query = "SELECT user_id, username FROM users";
//
//        ResultSet resultSet = QueryExecutor.execute(ds, DBOperation.QUERY, query);
//
//        List<User> users = new ArrayList<>();
//        try {
//            while(resultSet.next()) {
//                users.add(new User(new StringResult(resultSet, 2).get(),
//                        new StringResult(resultSet, 1).get()));
//            }
//            return users;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return new ArrayList<>();
//    }

    public void insertUser(IUser user) {
        SqlSession session = factory.openSession();
        try {
            session.getMapper(UserMapper.class).insertUser(user.getStringID(), user.getName(),
                    user.getAvatar(), user.getAvatarURL(), user.getDiscriminator(), user.isBot());
            session.commit();
            session.close();
        } catch(PersistenceException ex) {
            LOG.error("Error saving user: ", ex);
            session.rollback();
        } finally {
            session.close();
        }
    }

    public boolean userIsSaved(String userId) {
        SqlSession session = factory.openSession();
        try {
            Integer i = session.getMapper(UserMapper.class).userExists(userId);
            session.close();
            return null != i && i > 0;
        } finally {
            session.close();
        }
    }
}
