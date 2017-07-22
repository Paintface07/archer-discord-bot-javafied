package org.kondrak.archer.bot.command;

import sx.blah.discord.handle.obj.IMessage;

/**
 * Created by Administrator on 11/4/2016.
 */
public interface BotCommand<Q> {

    boolean shouldExecute(Q input);

    void execute(Q input);

    void handleFailure(IMessage input);
}
