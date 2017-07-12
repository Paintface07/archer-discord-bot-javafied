package org.kondrak.archer.bot.command.event.impl;

import org.kondrak.archer.bot.command.event.AbstractMessageCommand;
import org.kondrak.archer.bot.context.ArcherBotContext;
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
            "== **!word <word/phrase>** - display the number of word usages by user\n" +
            "== **!timer <ms> <name>** - start a timer\n"+
            "== **!roll <number>d<sides>** - roll one or more dice" +
            "==============================================================================";

    public HelpCommand(ArcherBotContext ctx, String command) {
        super(ctx, command);
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
