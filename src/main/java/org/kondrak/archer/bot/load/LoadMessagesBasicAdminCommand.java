package org.kondrak.archer.bot.load;

import org.kondrak.archer.bot.core.AbstractMessageCommand;
import org.kondrak.archer.bot.core.Context;
import org.kondrak.archer.bot.core.dao.MessageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageList;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by Administrator on 2/25/2017.
 */
public class LoadMessagesBasicAdminCommand extends AbstractMessageCommand {

    private static final Logger LOG = LoggerFactory.getLogger(LoadMessagesBasicAdminCommand.class);

    private MessageDao messageDao;

    public LoadMessagesBasicAdminCommand(String command) {
        super(command);
        messageDao = new MessageDao(Context.getInstance().getFactory());
    }

    @Override
    public void execute(IMessage input) {
        List<IChannel> channels = input.getClient().getChannels();

        int channelCount = 0;
        int msgCount = 0;
        for(IChannel c : channels) {
            LOG.info("Loading from channel: {}", c.getName());
            channelCount++;

            MessageList msg = new MessageList(input.getClient(), c);
            msg.setCacheCapacity(20000);

            try {
                msg.load(100);
            } catch (RateLimitException e) {
                LOG.error("Could not reload initial buffer in LoadMessagesBasicAdminCommand: ", e);
            }

            loadBatch(msg, msgCount);
        }

        try {
            input.reply(msgCount + " messages loaded from " + channelCount + " channels");
        } catch(MissingPermissionsException | RateLimitException | DiscordException ex) {
            LOG.error("Could not reply to message: {}", input.getChannel().getName());
            ex.printStackTrace();
        }
    }

    @Override
    public boolean shouldExecute(IMessage msg) {
        Context ctx = Context.getInstance();
        return null != msg.getContent() && msg.getContent().startsWith(getCommand())
                && msg.getAuthor().getName().equals(ctx.getProperties().getProperty("admin.name"))
                && msg.getAuthor().getDiscriminator().equals(ctx.getProperties().getProperty("admin.discriminator"));
    }

    @Override
    public String getFormatErrorMessage(IMessage input) {
        throw new UnsupportedOperationException("getFormatErrorMessage() is not implemented for " + this.getClass().getName());
    }

    private void loadBatch(MessageList msg, int msgCount) {
        ListIterator<IMessage> iter =  msg.listIterator();
        IMessage m = iter.next();
        if(m != null) {
            while (iter.hasNext()) {
                boolean alreadyExists = messageDao.messageExists(m.getStringID());

                if(!alreadyExists) {
                    LOG.info("Loading message: {}", m.getContent());
                    messageDao.saveMessage(m);
                    msgCount++;
                }

                m = iter.next();
                if (msgCount % 50 == 0) {
                    try {
                        LOG.info("=== Refreshing buffer @ {} ===", msgCount);
                        msg.load(100);
                    } catch (RateLimitException e) {
                        LOG.error("Could not reload subsequent buffer in LoadMessagesBasicAdminCommand: ", e);
                    }
                }
            }
        }
    }
}
