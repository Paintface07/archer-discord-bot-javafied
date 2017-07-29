package org.kondrak.archer.bot.core.listener;

import org.kondrak.archer.bot.core.Context;
import org.kondrak.archer.bot.core.ContextBuilder;
import org.kondrak.archer.bot.core.dao.ChannelDao;
import org.kondrak.archer.bot.core.dao.GuildDao;
import org.kondrak.archer.bot.core.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

/**
 * Created by Administrator on 2/24/2017.
 */
public class ReadyListener {

    private static final Logger LOG = LoggerFactory.getLogger(ReadyListener.class);

    private final ChannelDao channelDao;
    private final GuildDao guildDao;
    private final UserDao userDao;

    public ReadyListener() {
        ContextBuilder ctx = ContextBuilder.getInstance();
        this.channelDao = new ChannelDao(ctx.getFactory());
        this.guildDao = new GuildDao(ctx.getFactory());
        this.userDao = new UserDao(ctx.getFactory());
    }

    @EventSubscriber
    public void onReady(ReadyEvent e) {
        LOG.info(ContextBuilder.EVENT_LOGGER_FORMAT, e.getClass().getName());
        List<IUser> u = Context.getInstance().getClient().getUsers();

        for(int i = 0; i <u.size(); i++) {
            boolean saved = userDao.userIsSaved(u.get(i).getStringID());
            if(!saved) {
                LOG.info("Saved!: " + u.get(i).getName());
                userDao.insertUser(u.get(i));
            }
        }

        List<IGuild> guilds = e.getClient().getGuilds();

        for(IGuild g : guilds) {
            if(!guildDao.guildIsSaved(g)) {
                LOG.info("Adding " + g.getName());
                guildDao.addGuild(g);
            }
        }

        List<IChannel> channels = e.getClient().getChannels();

        for(IChannel c : channels) {
            if(!channelDao.channelIsSaved(c)) {
                LOG.info("Adding " + c.getName());
                channelDao.addChannel(c);
            }
        }
    }
}
