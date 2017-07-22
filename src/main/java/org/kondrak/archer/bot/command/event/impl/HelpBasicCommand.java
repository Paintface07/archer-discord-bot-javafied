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
public class HelpBasicCommand extends AbstractMessageCommand {

    private static final Logger LOG = LoggerFactory.getLogger(HelpBasicCommand.class);

    private final String helpText;

    public HelpBasicCommand(ArcherBotContext ctx, String command) {
        super(ctx, command);
        final String prefix = ctx.getPrefix();
        this.helpText =
                "==============================================================================\n" +
                "== **Available Commands:**\n" +
                "==============================================================================\n" +
                "== **" + prefix + "archerism** - display a random archer quote\n" +
                "== **" + prefix + "help** - display help information on the commands you can use\n" +
                "== **" + prefix + "word <word/phrase>** - display the number of word usages by user\n" +
                "== **" + prefix + "timer <ms up to 13 characters> <name>** - start a timer\n"+
                "== **" + prefix + "roll <number up to 3 characters>d<sides up to 3 characters>** - roll one or more dice" +
                "==============================================================================";
    }

    @Override
    public void execute(IMessage input) {
        try {
            new MessageBuilder(this.getClient()).withChannel(
                    getClient().getOrCreatePMChannel(input.getAuthor())).withContent(helpText).send();
        } catch(RateLimitException | DiscordException | MissingPermissionsException ex) {
            LOG.error("Could not build/send help message");
        }
    }

    @Override
    public boolean shouldExecute(IMessage input) {
        return null != input.getContent() && input.getContent().startsWith(getCommand());
    }

    @Override
    public String getFormatErrorMessage(IMessage input) {
        throw new UnsupportedOperationException("getFormatErrorMessage() is not implemented for " + this.getClass().getName());
    }
}
