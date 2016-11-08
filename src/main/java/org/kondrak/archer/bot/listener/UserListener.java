package org.kondrak.archer.bot.listener;

import org.kondrak.archer.bot.command.CommandRegistry;
import org.kondrak.archer.bot.dao.UserDao;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.obj.IUser;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Administrator on 11/7/2016.
 */
public class UserListener {
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
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onPresenceChange(PresenceUpdateEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onStatusChange(StatusChangeEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onBan(UserBanEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onJoin(UserJoinEvent e) {
        System.out.println(e.getClass().getName());
        List<IUser> users = userDao.getUsers();
        users.forEach((user) -> {
            System.out.println(user.getName());
        });
        List<IUser> clientUsers = registry.getClient().getUsers();
        for(IUser u : clientUsers) {
            if(!userDao.userIsSaved(u.getID())) {
                userDao.insertUser(u);
            }
        }
    }

    @EventSubscriber
    public void onLeave(UserLeaveEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onPardon(UserPardonEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onRoleUpdate(UserRoleUpdateEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onUpdate(UserUpdateEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onVoiceJoin(UserVoiceChannelJoinEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onVoiceLeave(UserVoiceChannelLeaveEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onVoiceMove(UserVoiceChannelMoveEvent e) {
        System.out.println(e.getClass().getName());
    }
}
