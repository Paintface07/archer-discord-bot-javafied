package org.kondrak.archer.bot.command.event;

import org.kondrak.archer.bot.command.BotCommand;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Created by Administrator on 11/5/2016.
 */
public interface MessageEventCommand<T> extends BotCommand<IMessage, T> {
    @Override
    boolean shouldExecute(IMessage input);

    @Override
    T execute(IMessage input);

    @Override
    void beforeExecute(IMessage input, T output);

    @Override
    void afterExecute(IMessage input, T output);

    @Override
    String getFormatErrorMessage(IMessage input);

    @Override
    void handleFailure(IMessage input);
}
