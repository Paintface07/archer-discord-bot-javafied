package org.kondrak.archer.bot.command.event;

import org.kondrak.archer.bot.command.CommandRegistry;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;

/**
 * Created by Administrator on 11/5/2016.
 */
public abstract class AbstractMessageCommand implements MessageEventCommand<IMessage> {
    private final String command;
    private final CommandRegistry registry;
    private final IDiscordClient client;

    public AbstractMessageCommand(IDiscordClient client, CommandRegistry registry, String command) {
        this.registry = registry;
        this.command = command;
        this.client = client;
    }

    @Override
    public boolean shouldExecute(IMessage input) {
        // default to making sure the content contains the command
        return input.getContent().contains(command);
    }

    @Override
    public void beforeExecute(IMessage input, IMessage output) {
        // default to doing nothing
    }

    @Override
    public void afterExecute(IMessage input, IMessage output) {
        // default to doing nothing
    }

    public String getCommand() {
        return this.command;
    }

    public CommandRegistry getRegistry() {
        return this.registry;
    }

    public IDiscordClient getClient() {
        return this.client;
    }
}
