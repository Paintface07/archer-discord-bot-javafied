package org.kondrak.archer.bot.listener;

import org.kondrak.archer.bot.command.CommandRegistry;
import org.kondrak.archer.bot.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.obj.IUser;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Administrator on 11/7/2016.
 */
public class UserListener {

    private final Logger LOG = LoggerFactory.getLogger(UserListener.class);

    private final DataSource ds;
    private final CommandRegistry registry;
    private final UserDao userDao;

    public UserListener(DataSource ds, CommandRegistry registry, UserDao userDao) {
        this.ds = ds;
        this.registry = registry;
        this.userDao = userDao;
    }

    @EventSubscriber
    public void onNickChange(NickNameChangeEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onPresenceChange(PresenceUpdateEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onStatusChange(StatusChangeEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onBan(UserBanEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onJoin(UserJoinEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
        List<IUser> clientUsers = registry.getClient().getUsers();
        clientUsers.forEach((user) -> {
            System.out.println(user.getName());
        });
        if(!userDao.userIsSaved(e.getUser().getID())) {
            System.out.println("Saved: " + e.getUser().getName());
            userDao.insertUser(e.getUser());
        }
    }

    @EventSubscriber
    public void onLeave(UserLeaveEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onPardon(UserPardonEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onRoleUpdate(UserRoleUpdateEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onUpdate(UserUpdateEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onVoiceJoin(UserVoiceChannelJoinEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onVoiceLeave(UserVoiceChannelLeaveEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onVoiceMove(UserVoiceChannelMoveEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }
}
