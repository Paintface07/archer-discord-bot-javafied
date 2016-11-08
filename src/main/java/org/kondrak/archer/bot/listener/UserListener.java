package org.kondrak.archer.bot.listener;

import org.kondrak.archer.bot.command.CommandRegistry;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;

import javax.sql.DataSource;

/**
 * Created by Administrator on 11/7/2016.
 */
public class UserListener {
    private final DataSource ds;
    private final CommandRegistry registry;

    public UserListener(DataSource ds, CommandRegistry registry) {
        this.ds = ds;
        this.registry = registry;
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
