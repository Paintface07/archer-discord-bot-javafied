package org.kondrak.archer.bot.archerism;

import org.kondrak.archer.bot.configuration.ConfigType;
import org.kondrak.archer.bot.core.AbstractMessageCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 11/5/2016.
 */
public class ArcherismBasicCommand extends AbstractMessageCommand {

    private static final Logger LOG = LoggerFactory.getLogger(ArcherismBasicCommand.class);

    private ArcherismDao dao;

    public ArcherismBasicCommand(String command, ArcherismDao dao) {
        super(command);
        this.dao = dao;
    }

    @Override
    public void execute(IMessage input) {
        List<Archerism> sayings = dao.getArcherisms();
        int rand = new Random().nextInt(sayings.size());
        Archerism randMessage = sayings.get(rand);
        try {
            input.reply(randMessage.getText());
        } catch (MissingPermissionsException | RateLimitException | DiscordException ex) {
            LOG.error("Could not reply to archerism command: ", ex);
        }
    }

    @Override
    public boolean shouldExecute(IMessage input) {
        String content = input.getContent();
        return null != content && content.startsWith(getCommand())
                && configService.isConfiguredForGuild(input.getGuild(), ConfigType.ARCHERISM_COMMAND);
    }

    @Override
    public String getFormatErrorMessage(IMessage input) {
        throw new UnsupportedOperationException("getFormatErrorMessage() is not implemented for " + this.getClass().getName());
    }
}
