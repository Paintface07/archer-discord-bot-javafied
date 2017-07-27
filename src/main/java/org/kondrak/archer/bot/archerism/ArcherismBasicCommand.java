package org.kondrak.archer.bot.archerism;

import org.kondrak.archer.bot.configuration.ConfigScope;
import org.kondrak.archer.bot.configuration.ConfigType;
import org.kondrak.archer.bot.configuration.Configuration;
import org.kondrak.archer.bot.core.AbstractMessageCommand;
import org.kondrak.archer.bot.core.ArcherBotContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
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

    private final List<Archerism> sayings;

    public ArcherismBasicCommand(ArcherBotContext ctx, String command) {
        super(ctx, command);
        this.sayings = new ArcherismDao(ctx.getFactory()).getArcherisms();
    }

    @Override
    public void execute(IMessage input) {
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
        return null != input.getContent() && input.getContent().startsWith(getCommand())
                && configService.isConfiguredForGuild(input.getGuild(), ConfigType.ARCHERISM_COMMAND);
    }

    @Override
    public String getFormatErrorMessage(IMessage input) {
        throw new UnsupportedOperationException("getFormatErrorMessage() is not implemented for " + this.getClass().getName());
    }
}
