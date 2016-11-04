package org.kondrak.archer.bot.listener;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IMessage;

import javax.sql.DataSource;

/**
 * Created by Administrator on 11/3/2016.
 */
public class MessageListener {

    private final DataSource ds;

    public MessageListener(DataSource ds) {
        this.ds = ds;
    }

    @EventSubscriber
    public void onReadyEvent(ReadyEvent e) {
        System.out.println("READY");
    }

    @EventSubscriber
    public void onMessage(MessageReceivedEvent e) {
        IMessage msg = e.getMessage();
        System.out.println("Channel: " + msg.getChannel() + " Author: " + msg.getAuthor() + ": " + msg);
        System.out.println("****************************************************************");

    }
}
