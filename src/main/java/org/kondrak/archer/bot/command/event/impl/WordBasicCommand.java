package org.kondrak.archer.bot.command.event.impl;

import org.kondrak.archer.bot.command.event.AbstractMessageCommand;
import org.kondrak.archer.bot.context.ArcherBotContext;
import org.kondrak.archer.bot.dao.MessageDao;
import org.kondrak.archer.bot.model.Statistic;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.List;

/**
 * Created by Administrator on 2/25/2017.
 */
public class WordBasicCommand extends AbstractMessageCommand {

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
            e.printStackTrace();
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
