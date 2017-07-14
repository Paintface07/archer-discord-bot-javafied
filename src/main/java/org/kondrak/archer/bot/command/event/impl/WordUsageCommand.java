package org.kondrak.archer.bot.command.event.impl;

import org.kondrak.archer.bot.command.event.AbstractMessageCommand;
import org.kondrak.archer.bot.context.ArcherBotContext;
import org.kondrak.archer.bot.dao.MessageDao;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.Map;

/**
 * Created by Administrator on 2/25/2017.
 */
public class WordUsageCommand extends AbstractMessageCommand {

    private MessageDao messageDao;

    public WordUsageCommand(ArcherBotContext ctx, String command) {
        super(ctx, command);
        messageDao = new MessageDao(ctx.getDatasource());
    }

    @Override
    public IMessage execute(IMessage input) {
        String content = input.getContent().replace(getCommand()+ " ", "");

        Map<String, Long> values = messageDao.getTimesSaidByUser(input.getGuild().getID(), content);

        String response = "The word/phrase '" + content + "' has been used a total of ";
        String table = "";
        Long total = Long.valueOf(0);

        for(Map.Entry<String, Long> e : values.entrySet()) {
            Long ct = e.getValue();
            table += "- " + e.getKey() + ": " + ct + "\n";
            total += ct;
        }

        response += total + " times by:\n" + table;

        try {
            input.reply(response);
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
        return null;
    }
}
