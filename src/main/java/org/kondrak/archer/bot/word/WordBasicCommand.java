package org.kondrak.archer.bot.word;

import org.kondrak.archer.bot.core.AbstractMessageCommand;
import org.kondrak.archer.bot.core.ArcherBotContext;
import org.kondrak.archer.bot.core.dao.MessageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.List;

/**
 * Created by Administrator on 2/25/2017.
 */
public class WordBasicCommand extends AbstractMessageCommand {

    private static final Logger LOG = LoggerFactory.getLogger(WordBasicCommand.class);

    private MessageDao messageDao;

    public WordBasicCommand(ArcherBotContext ctx, String command) {
        super(ctx, command);
        messageDao = new MessageDao(ctx.getFactory());
    }

    @Override
    public void execute(IMessage input) {
        String content = input.getContent().replace(getCommand()+ " ", "");

        List<Statistic> values = messageDao.getTimesSaidByUser(input.getGuild().getStringID(), content);

        String response = "The word/phrase '" + content + "' has been used a total of ";
        String table = "";
        Long total = 0L;

        for(Statistic s : values) {
            Long ct = Long.valueOf(s.getValue());
            table += "- " + s.getKey() + ": " + ct + "\n";
            total += ct;
        }

        response += total + " times by:\n" + table;

        try {
            input.reply(response);
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            LOG.error("Could not execute WordBasicCommand: ", e);
        }
    }

    @Override
    public boolean shouldExecute(IMessage input) {
        return null != input.getContent() && input.getContent().startsWith(getCommand());
    }

    @Override
    public String getFormatErrorMessage(IMessage input) {
        throw new UnsupportedOperationException("getFormatErrorMessage() is not implemented for " + this.getClass().getName());
    }
}
