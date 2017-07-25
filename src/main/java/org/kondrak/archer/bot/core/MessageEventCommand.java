package org.kondrak.archer.bot.core;

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
    String getFormatErrorMessage(IMessage input);

    void handleFormatError(IMessage input);
}
