package org.kondrak.archer.bot.command;

import org.kondrak.archer.bot.command.event.MessageEventCommand;
import sx.blah.discord.api.IDiscordClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 11/4/2016.
 */
public class CommandRegistry {

    private List<MessageEventCommand> commands = new ArrayList<>();
    private final IDiscordClient client;

    public CommandRegistry(IDiscordClient client, MessageEventCommand... commands){
        this.client = client;
        registerAllCommands(commands);
    }

    public CommandRegistry register(MessageEventCommand command) {
        commands.add(command);
        return this;
    }

    public CommandRegistry registerAll(MessageEventCommand... commands) {
        registerAllCommands(commands);
        return this;
    }

    private void registerAllCommands(MessageEventCommand... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    public List<MessageEventCommand> getCommandsAsList() {
        return commands.stream().collect(Collectors.toList());
    }

    public IDiscordClient getClient() {
        return this.client;
    }
}
