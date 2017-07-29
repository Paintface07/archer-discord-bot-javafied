package org.kondrak.archer.bot.core.listener;

import org.kondrak.archer.bot.core.ContextBuilder;
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
public class ChannelListener {

    private static final Logger LOG = LoggerFactory.getLogger(ChannelListener.class);

    @EventSubscriber
    public void onCreate(ChannelCreateEvent e) {
        LOG.info(ContextBuilder.EVENT_LOGGER_FORMAT, e.getClass().getName());
    }

    @EventSubscriber
    public void onDelete(ChannelDeleteEvent e) {
        LOG.info(ContextBuilder.EVENT_LOGGER_FORMAT, e.getClass().getName());
    }

    @EventSubscriber
    public void onUpdate(ChannelUpdateEvent e) {
        LOG.info(ContextBuilder.EVENT_LOGGER_FORMAT, e.getClass().getName());
    }

    @EventSubscriber
    public void onVoiceChannelCreate(VoiceChannelCreateEvent e) {
        LOG.info(ContextBuilder.EVENT_LOGGER_FORMAT, e.getClass().getName());
    }

    @EventSubscriber
    public void onVoiceChannelDelete(VoiceChannelDeleteEvent e) {
        LOG.info(ContextBuilder.EVENT_LOGGER_FORMAT, e.getClass().getName());
    }

    @EventSubscriber
    public void onVoiceChannelUpdate(VoiceChannelUpdateEvent e) {
        LOG.info(ContextBuilder.EVENT_LOGGER_FORMAT, e.getClass().getName());
    }
}
