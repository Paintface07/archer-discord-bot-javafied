package org.kondrak.archer.bot.command.event;

import org.kondrak.archer.bot.command.BotCommand;
import sx.blah.discord.handle.obj.IMessage;

/**
 * Created by Administrator on 11/5/2016.
 */
public interface MessageEventCommand<T> extends BotCommand<IMessage> {
    @Override
    boolean shouldExecute(IMessage input);

    @Override
    void execute(IMessage input);

    @Override
    void handleFailure(IMessage input);
}
