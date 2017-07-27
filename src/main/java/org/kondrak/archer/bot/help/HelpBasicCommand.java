package org.kondrak.archer.bot.help;

import org.kondrak.archer.bot.configuration.ConfigCommand;
import org.kondrak.archer.bot.configuration.ConfigScope;
import org.kondrak.archer.bot.configuration.ConfigType;
import org.kondrak.archer.bot.core.AbstractMessageCommand;
import org.kondrak.archer.bot.core.ArcherBotContext;
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

    private static final String LINE_PREFIX = "== **";

    private final String helpText;

    // TODO: add an admin flavor of this command so admin commands don't display alongside user commands
    public HelpBasicCommand(ArcherBotContext ctx, String command) {
        super(ctx, command);
        final String prefix = ctx.getPrefix();
        this.helpText =
                "==============================================================================\n" +
                "== **Available Commands:**\n" +
                "==============================================================================\n" +
                LINE_PREFIX + prefix + "archerism** - display a random archer quote\n" +
                LINE_PREFIX + prefix + "help** - display help information on the commands you can use\n" +
                LINE_PREFIX + prefix + "word <word/phrase>** - display the number of word usages by user\n" +
                LINE_PREFIX + prefix + "timer <ms up to 13 characters> <name>** - start a timer\n"+
                LINE_PREFIX + prefix + "roll <number up to 3 characters>d<sides up to 3 characters>** - roll one or more dice\n" +
                LINE_PREFIX + prefix + "config (" + ConfigCommand.pipeDelimited() + ") (" + ConfigScope.pipeDelimited()
                        + ") (" + ConfigType.pipeDelimited() + ")** - configure commands\n" +
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
