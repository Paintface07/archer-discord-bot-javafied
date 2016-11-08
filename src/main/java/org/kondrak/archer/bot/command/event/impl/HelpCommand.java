package org.kondrak.archer.bot.command.event.impl;

import org.kondrak.archer.bot.command.CommandRegistry;
import org.kondrak.archer.bot.command.event.AbstractMessageCommand;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Created by Administrator on 11/7/2016.
 */
public class HelpCommand extends AbstractMessageCommand {

    private static String HELP_TEXT =
            "==============================================================================\n" +
            "== **Available Commands:**\n" +
            "==============================================================================\n" +
            "== **!archerism** - display a random archer quote\n" +
            "== **!help** - display help information on the commands you can use\n" +
            "==============================================================================";

    public HelpCommand(IDiscordClient client, CommandRegistry registry, String command) {
        super(client, registry, command);
    }

    @Override
    public IMessage execute(IMessage input) {
        try {
            new MessageBuilder(this.getClient()).withChannel(
                    getClient().getOrCreatePMChannel(input.getAuthor())).withContent(HELP_TEXT).send();
        } catch(RateLimitException | DiscordException | MissingPermissionsException ex) {
            System.out.println("Could not build/send help message");
        }
        return null;
    }
}