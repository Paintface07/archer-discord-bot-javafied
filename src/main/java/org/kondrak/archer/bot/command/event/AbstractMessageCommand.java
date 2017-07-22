package org.kondrak.archer.bot.command.event;

import org.kondrak.archer.bot.command.CommandRegistry;
import org.kondrak.archer.bot.context.ArcherBotContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Created by Administrator on 11/5/2016.
 */
public abstract class AbstractMessageCommand implements MessageEventCommand<IMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractMessageCommand.class);

    private final ArcherBotContext ctx;
    private final String command;

    public AbstractMessageCommand(ArcherBotContext ctx, String command) {
        this.ctx = ctx;
        this.command = command;
    }

    @Override
    public abstract void execute(IMessage msg);

    @Override
    public abstract boolean shouldExecute(IMessage input);

    /**
     * Implement this method to write formatted error messages when getFormatErrorMessage() is called.
     * @param input - the message that errored
     * @return {@code String} - the error message to reply with
     */
    public abstract String getFormatErrorMessage(IMessage input);

    @Override
    public void handleFailure(IMessage input) {
        try {
            input.reply(getFormatErrorMessage(input));
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            LOG.error("Error responding with failure: ", e);
        }
    }

    public String getCommand() {
        return this.command;
    }

    public CommandRegistry getRegistry() {
        return ctx.getRegistry();
    }

    protected IDiscordClient getClient() {
        return ctx.getClient();
    }
}
