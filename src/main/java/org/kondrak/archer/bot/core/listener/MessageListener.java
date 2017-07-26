package org.kondrak.archer.bot.core.listener;

import org.kondrak.archer.bot.core.ArcherBotContext;
import org.kondrak.archer.bot.core.dao.MessageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MentionEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageDeleteEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageUpdateEvent;
import sx.blah.discord.handle.obj.IMessage;

/**
 * Created by Administrator on 11/3/2016.
 */
public class MessageListener extends AbstractListener {

    private static final Logger LOG = LoggerFactory.getLogger(MessageListener.class);

    private final MessageDao msgDao;

    public MessageListener(ArcherBotContext ctx) {
        super(ctx);
        this.msgDao = new MessageDao(ctx.getFactory());
    }

    @EventSubscriber
    public void onMessage(MessageReceivedEvent e) {
        IMessage msg = e.getMessage();
        LOG.info("Channel: {} Author: {} - {}", msg.getChannel(),  msg.getAuthor(),  msg);
        msgDao.saveMessage(msg);
        getRegistry().getCommandsAsList().forEach(cmd -> {
            if(cmd.shouldExecute(msg)) {
                cmd.execute(msg);
            }
        });
    }

    // TODO: add a mentions table that ties back to messages and/or message history
    @EventSubscriber
    public void onMention(MessageEvent e) {
        LOG.info(ArcherBotContext.EVENT_LOGGER_FORMAT, e.getClass().getName());
    }

    // TODO: add deletes and a message history table to preserve message history
    @EventSubscriber
    public void onDelete(MessageDeleteEvent e) {
        LOG.info(ArcherBotContext.EVENT_LOGGER_FORMAT, e.getClass().getName());
    }

    // TODO: add updates and a message history table to preserve message history
    @EventSubscriber
    public void onUpdate(MessageUpdateEvent e) {
        LOG.info(ArcherBotContext.EVENT_LOGGER_FORMAT, e.getClass().getName());
    }
}
