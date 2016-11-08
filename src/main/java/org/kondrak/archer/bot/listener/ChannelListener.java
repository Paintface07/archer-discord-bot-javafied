package org.kondrak.archer.bot.listener;

import org.kondrak.archer.bot.command.CommandRegistry;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;

import javax.sql.DataSource;

/**
 * Created by Administrator on 11/7/2016.
 */
public class ChannelListener {
    private final DataSource ds;
    private final CommandRegistry registry;

    public ChannelListener(DataSource ds, CommandRegistry registry) {
        this.ds = ds;
        this.registry = registry;
    }

    @EventSubscriber
    public void onCreate(ChannelCreateEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onDelete(ChannelDeleteEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onUpdate(ChannelUpdateEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onVoiceChannelCreate(VoiceChannelCreateEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onVoiceChannelDelete(VoiceChannelDeleteEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onVoiceChannelUpdate(VoiceChannelUpdateEvent e) {
        System.out.println(e.getClass().getName());
    }
}
