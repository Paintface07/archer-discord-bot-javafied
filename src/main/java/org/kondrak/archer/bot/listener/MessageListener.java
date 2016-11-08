package org.kondrak.archer.bot.listener;

import org.kondrak.archer.bot.command.CommandRegistry;
import org.kondrak.archer.bot.dao.MessageDao;
import org.kondrak.archer.bot.dao.UserDao;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Administrator on 11/3/2016.
 */
public class MessageListener {

    private final DataSource ds;
    private final CommandRegistry registry;
    private final MessageDao msgDao;
    private final UserDao userDao;

    public MessageListener(DataSource ds, CommandRegistry registry) {
        this.ds = ds;
        this.msgDao = new MessageDao(ds);
        this.userDao = new UserDao(ds, registry.getClient());
        this.registry = registry;
    }

    @EventSubscriber
    public void onReadyEvent(ReadyEvent e) {
        System.out.println(e.getClass().getName());
//        List<IUser> clientUsers = registry.getClient().getUsers();
        List<IUser> u = registry.getClient().getUsers();

        for(int i = 0; i < u.size(); i++) {
            System.out.println("User* " + i + " " + u.get(i).getName() + " " + u.get(i).getID() + " " + u.get(i).getStatus().toString());
        }

        System.out.println(u.size());
        for(int i = 0; i <u.size(); i++) {
            System.out.println(u.size() + " " + u.get(i).getStatus().toString());
            System.out.println("User! " + u.get(i).getName() + " " + u.get(i).getID());
            boolean saved = userDao.userIsSaved(u.get(i).getID());
            System.out.println("Saved*: " + u.get(i).getName());
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
