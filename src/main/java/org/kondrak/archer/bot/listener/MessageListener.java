package org.kondrak.archer.bot.listener;

import org.kondrak.archer.bot.command.CommandRegistry;
import org.kondrak.archer.bot.context.ArcherBotContext;
import org.kondrak.archer.bot.dao.MessageDao;
import org.kondrak.archer.bot.dao.UserDao;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

/**
 * Created by Administrator on 11/3/2016.
 */
public class MessageListener {
    private final MessageDao msgDao;
    private final UserDao userDao;
    private final IDiscordClient client;
    private final CommandRegistry registry;

    public MessageListener(ArcherBotContext ctx) {
        this.client = ctx.getClient();
        this.registry = ctx.getRegistry();
        this.msgDao = new MessageDao(ctx.getDatasource());
        this.userDao = new UserDao(ctx.getDatasource(), client);
    }

    @EventSubscriber
    public void onReadyEvent(ReadyEvent e) {
        System.out.println(e.getClass().getName());
        List<IUser> u = client.getUsers();

        for(int i = 0; i <u.size(); i++) {
            boolean saved = userDao.userIsSaved(u.get(i).getID());
            if(!saved) {
                System.out.println("Saved!: " + u.get(i).getName());
                userDao.insertUser(u.get(i));
            }
        }
    }

    @EventSubscriber
    public void onMessage(MessageReceivedEvent e) {
        IMessage msg = e.getMessage();
        System.out.println("Channel: " + msg.getChannel() + " Author: " + msg.getAuthor() + ": " + msg);
        System.out.println("****************************************************************");
        msgDao.saveMessage(msg);
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
