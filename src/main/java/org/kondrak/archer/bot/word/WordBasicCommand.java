package org.kondrak.archer.bot.word;

import org.kondrak.archer.bot.configuration.ConfigType;
import org.kondrak.archer.bot.core.AbstractMessageCommand;
import org.kondrak.archer.bot.core.Context;
import org.kondrak.archer.bot.core.dao.MessageDao;
import org.kondrak.archer.bot.core.model.Statistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2/25/2017.
 */
public class WordBasicCommand extends AbstractMessageCommand {

    private static final Logger LOG = LoggerFactory.getLogger(WordBasicCommand.class);

    private MessageDao messageDao;

    public WordBasicCommand(String command) {
        super(command);
        messageDao = new MessageDao(Context.getInstance().getFactory());
    }

    @Override
    public void execute(IMessage input) {
        String content = input.getContent().replace(getCommand()+ " ", "");

        List<Statistic> values = messageDao.getTimesSaidByUser(input.getGuild().getStringID(), content)
                .stream()
                .sorted((v1, v2) -> v2.getValue().compareTo(v1.getValue()))
                .collect(Collectors.toList());

        String response = "The word/phrase '" + content + "' has been used a total of ";
        StringBuilder table = new StringBuilder("");
        Long total = 0L;

        for(int s = 0; s < values.size(); s++) {
            Long ct = Long.valueOf(values.get(s).getValue());
            table.append("- "+ (s + 1) + ": " + values.get(s).getKey() + ": " + ct + "\n");
            total += ct;
        }

        response += total + " times by:\n" + table.toString();

        try {
            input.reply(response);
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            LOG.error("Could not execute WordBasicCommand: ", e);
        }
    }

    @Override
    public boolean shouldExecute(IMessage input) {
        return null != input.getContent() && input.getContent().startsWith(getCommand())
            && configService.isConfiguredForGuild(input.getGuild(), ConfigType.WORD_COMMAND);
    }

    @Override
    public String getFormatErrorMessage(IMessage input) {
        throw new UnsupportedOperationException("getFormatErrorMessage() is not implemented for " + this.getClass().getName());
    }
}
