package org.kondrak.archer.bot.command;

/**
 * Created by Administrator on 11/4/2016.
 */
public interface BotCommand<Q, A> {

    boolean shouldExecute(Q input);

    A execute(Q input);

    void beforeExecute(Q input, A output);

    void afterExecute(Q input, A output);
}