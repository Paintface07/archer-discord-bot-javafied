package org.kondrak.archer.bot.core.listener;

import org.kondrak.archer.bot.core.ArcherBotContext;
import org.kondrak.archer.bot.core.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.StatusChangeEvent;
import sx.blah.discord.handle.impl.events.guild.member.NicknameChangedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserBanEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserLeaveEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserPardonEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserRoleUpdateEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelJoinEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelLeaveEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelMoveEvent;
import sx.blah.discord.handle.impl.events.user.PresenceUpdateEvent;
import sx.blah.discord.handle.impl.events.user.UserUpdateEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

/**
 * Created by Administrator on 11/7/2016.
 */
public class UserListener extends AbstractListener {

    private final Logger LOG = LoggerFactory.getLogger(UserListener.class);

    private final UserDao userDao;

    public UserListener(ArcherBotContext ctx) {
        super(ctx);
        this.userDao = new UserDao(ctx.getFactory());
    }

    @EventSubscriber
    public void onNickChange(NicknameChangedEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onPresenceChange(PresenceUpdateEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onStatusChange(PresenceUpdateEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onBan(UserBanEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onJoin(UserJoinEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
        List<IUser> clientUsers = getRegistry().getClient().getUsers();
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
        // TODO: Add a command to configure this setting (and others)
        LOG.info("Event triggered: {}", e.getClass().getName());
        String channelName = e.getVoiceChannel().getName();
        for(IChannel c : e.getGuild().getChannels()) {
            String textChannelName = e.getVoiceChannel().getName().toLowerCase();
            if(c.getName().toLowerCase().equals(textChannelName)) {
                c.sendMessage(e.getUser().getName() + " disconnected.");
            }
        }
    }

    @EventSubscriber
    public void onVoiceMove(UserVoiceChannelMoveEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
//        String channelName = e.getVoiceChannel().getName();
//        LOG.info("{}, {}", e.getNewChannel().getName(), e.getOldChannel().getName());
//        for(IChannel c : e.getGuild().getChannels()) {
//            String textChannelName = c.getName().toLowerCase();
//            if(e.getOldChannel().getName().toLowerCase().equals(textChannelName.toLowerCase())) {
//                c.sendMessage(e.getUser().getName() + " moved to " + e.getNewChannel().getName() + ".");
//            }
//        }
    }
}

