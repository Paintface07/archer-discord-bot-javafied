package org.kondrak.archer.bot.dao;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.dao.mappers.UserMapper;
import org.postgresql.ds.PGConnectionPoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IUser;

/**
 * Created by Administrator on 11/6/2016.
 */
public class UserDao extends AbstractDao {

    private static final Logger LOG = LoggerFactory.getLogger(UserDao.class);

    public UserDao(SqlSessionFactory factory) {
        super(factory);
    }

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
