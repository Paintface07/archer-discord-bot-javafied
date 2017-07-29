package org.kondrak.archer.bot.core;

import org.kondrak.archer.bot.configuration.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Created by Administrator on 11/5/2016.
 */
public abstract class AbstractMessageCommand implements MessageEventCommand {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractMessageCommand.class);

    private final String command;
    protected final ConfigurationService configService;

    public AbstractMessageCommand(String command) {
        Context ctx = Context.getInstance();
        this.command = ctx.getPrefix() + command;
        this.configService = ctx.getConfigService();
    }

    @Override
    public boolean shouldExecute(IMessage input) {
        // default to making sure the content contains the command
        return null != input.getContent() && input.getContent().contains(command);
    }

    @Override
    public String getFormatErrorMessage(IMessage input) {
        // default to throwing an exception for implementations that do not implement their own error messages
        throw new UnsupportedOperationException("An error message is not implemented for " + getClass().getName() + ".");
    }

    @Override
    public void handleFormatError(IMessage input) {
        try {
            input.reply(getFormatErrorMessage(input));
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            LOG.error("Error sending format error: ", e);
        }
    }

    public String getCommand() {
        return this.command;
    }
}
