package org.kondrak.archer.bot.listener;

import org.kondrak.archer.bot.command.CommandRegistry;
import org.kondrak.archer.bot.context.ArcherBotContext;
import org.kondrak.archer.bot.dao.MessageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MentionEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageDeleteEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageUpdateEvent;
import sx.blah.discord.handle.obj.IMessage;

/**
 * Created by Administrator on 11/3/2016.
 */
public class MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(MessageListener.class);

    private final MessageDao msgDao;
    private final IDiscordClient client;
    private final CommandRegistry registry;

    public MessageListener(ArcherBotContext ctx) {
        this.client = ctx.getClient();
        this.registry = ctx.getRegistry();
        this.msgDao = new MessageDao(ctx.getDatasource());
    }

    @EventSubscriber
    public void onMessage(MessageReceivedEvent e) {
        IMessage msg = e.getMessage();
        LOG.info("Channel: {} Author: {} - {}", msg.getChannel(),  msg.getAuthor(),  msg);
        msgDao.saveMessage(msg);
        registry.getCommandsAsList().forEach((cmd) -> {
            if(cmd.shouldExecute(msg)) {
                cmd.execute(msg);
            }
        });
    }

    // TODO: add a mentions table that ties back to messages and/or message history
    @EventSubscriber
    public void onMention(MentionEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    // TODO: add deletes and a message history table to preserve message history
    @EventSubscriber
    public void onDelete(MessageDeleteEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    // TODO: add updates and a message history table to preserve message history
    @EventSubscriber
    public void onUpdate(MessageUpdateEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }
}
