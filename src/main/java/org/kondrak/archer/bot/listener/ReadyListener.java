package org.kondrak.archer.bot.listener;

import org.kondrak.archer.bot.context.ArcherBotContext;
import org.kondrak.archer.bot.dao.ChannelDao;
import org.kondrak.archer.bot.dao.GuildDao;
import org.kondrak.archer.bot.dao.UserDao;
import sx.blah.discord.api.IDiscordClient;
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
    private final ChannelDao channelDao;
    private final GuildDao guildDao;
    private final UserDao userDao;
    private final IDiscordClient client;

    public ReadyListener(ArcherBotContext ctx) {
        this.channelDao = new ChannelDao(ctx.getDatasource());
        this.guildDao = new GuildDao(ctx.getDatasource());
        this.userDao = new UserDao(ctx);
        this.client = ctx.getClient();
    }

    @EventSubscriber
    public void onReady(ReadyEvent e) {
        System.out.println(e.getClass().getName());
        List<IUser> u = client.getUsers();

        for(int i = 0; i <u.size(); i++) {
            boolean saved = userDao.userIsSaved(u.get(i).getID());
            if(!saved) {
                System.out.println("Saved!: " + u.get(i).getName());
                userDao.insertUser(u.get(i));
            }
        }

        List<IGuild> guilds = e.getClient().getGuilds();

        for(IGuild g : guilds) {
            if(!guildDao.guildIsSaved(g)) {
                System.out.println("Adding " + g.getName());
                guildDao.addGuild(g);
            }
        }

        List<IChannel> channels = e.getClient().getChannels();

        for(IChannel c : channels) {
            if(!channelDao.channelIsSaved(c)) {
                System.out.println("Adding " + c.getName());
                channelDao.addChannel(c);
            }
        }
    }
}