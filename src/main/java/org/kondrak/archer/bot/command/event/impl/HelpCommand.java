package org.kondrak.archer.bot.command.event.impl;

import org.kondrak.archer.bot.command.event.AbstractMessageCommand;
import org.kondrak.archer.bot.context.ArcherBotContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Created by Administrator on 11/7/2016.
 */
public class HelpCommand extends AbstractMessageCommand {

    private static final Logger LOG = LoggerFactory.getLogger(HelpCommand.class);

    private static String HELP_TEXT =
            "==============================================================================\n" +
            "== **Available Commands:**\n" +
            "==============================================================================\n" +
            "== **!archerism** - display a random archer quote\n" +
            "== **!help** - display help information on the commands you can use\n" +
            "== **!word <word/phrase>** - display the number of word usages by user\n" +
            "== **!timer <ms up to 13 characters> <name>** - start a timer\n"+
            "== **!roll <number up to 3 characters>d<sides up to 3 characters>** - roll one or more dice" +
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
            LOG.error("Could not build/send help message");
        }
        return null;
    }
}
