package org.kondrak.archer.bot.listener;

import org.kondrak.archer.bot.context.ArcherBotContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.ChannelCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.ChannelDeleteEvent;
import sx.blah.discord.handle.impl.events.guild.channel.ChannelUpdateEvent;
import sx.blah.discord.handle.impl.events.guild.voice.VoiceChannelCreateEvent;
import sx.blah.discord.handle.impl.events.guild.voice.VoiceChannelDeleteEvent;
import sx.blah.discord.handle.impl.events.guild.voice.VoiceChannelUpdateEvent;

/**
 * Created by Administrator on 11/7/2016.
 */
public class ChannelListener extends AbstractListener {

    private final Logger LOG = LoggerFactory.getLogger(ChannelListener.class);

    public ChannelListener(ArcherBotContext ctx) {
        super(ctx);
    }

    @EventSubscriber
    public void onCreate(ChannelCreateEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onDelete(ChannelDeleteEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onUpdate(ChannelUpdateEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onVoiceChannelCreate(VoiceChannelCreateEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onVoiceChannelDelete(VoiceChannelDeleteEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onVoiceChannelUpdate(VoiceChannelUpdateEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }
}
