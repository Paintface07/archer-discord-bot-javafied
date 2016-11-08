package org.kondrak.archer.bot.listener;

import org.kondrak.archer.bot.command.CommandRegistry;
import org.kondrak.archer.bot.dao.MessageDao;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.obj.IMessage;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Administrator on 11/3/2016.
 */
public class MessageListener {

    private final DataSource ds;
    private final CommandRegistry registry;
    private final MessageDao msgDao;

    public MessageListener(DataSource ds, CommandRegistry registry) {
        this.ds = ds;
        this.msgDao = new MessageDao(ds);
        this.registry = registry;
    }

    @EventSubscriber
    public void onReadyEvent(ReadyEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onMessage(MessageReceivedEvent e) {
        IMessage msg = e.getMessage();
        System.out.println("Channel: " + msg.getChannel() + " Author: " + msg.getAuthor() + ": " + msg);
        System.out.println("****************************************************************");
        try {
            msgDao.saveMessage(msg);
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        registry.getCommandsAsList().forEach((cmd) -> {
            if(cmd.shouldExecute(msg)) {
                cmd.execute(msg);
            }
        });
    }

    @EventSubscriber
    public void onMention(MentionEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onDelete(MessageDeleteEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onUpdate(MessageUpdateEvent e) {
        System.out.println(e.getClass().getName());
    }
}
