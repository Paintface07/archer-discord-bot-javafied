package org.kondrak.archer.bot.core;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Created by Administrator on 11/5/2016.
 */
public abstract class AbstractMessageCommand implements MessageEventCommand<IMessage> {
    private final ArcherBotContext ctx;
    private final String command;

    public AbstractMessageCommand(ArcherBotContext ctx, String command) {
        this.ctx = ctx;
        this.command = command;
    }

    @Override
    public boolean shouldExecute(IMessage input) {
        // default to making sure the content contains the command
        return null != input.getContent() && input.getContent().contains(command);
    }

    @Override
    public abstract void execute(IMessage msg);

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
            e.printStackTrace();
        }
    }

    public String getCommand() {
        return this.command;
    }

    public CommandRegistry getRegistry() {
        return ctx.getRegistry();
    }

    public IDiscordClient getClient() {
        return ctx.getClient();
    }
}