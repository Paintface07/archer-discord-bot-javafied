package org.kondrak.archer.bot.command.event.impl;

import org.kondrak.archer.bot.command.event.AbstractMessageCommand;
import org.kondrak.archer.bot.context.ArcherBotContext;
import sx.blah.discord.handle.obj.IMessage;

public class ConfigurationParameterizedCommand extends AbstractMessageCommand {

    public ConfigurationParameterizedCommand(ArcherBotContext ctx, String command) {
        super(ctx, command);
    }


    @Override
    public void execute(IMessage input) {

    }

    @Override
    public boolean shouldExecute(IMessage msg) {
        boolean result = null != msg.getContent() && msg.getContent().startsWith(getCommand())
                && (msg.getAuthor().getStringID().equals(msg.getGuild().getOwner().getStringID())
                || (msg.getAuthor().getName().equals("Paintface07")
                    && msg.getAuthor().getDiscriminator().equals("2733")))
                && msg.getContent().matches("");

        return result;
    }

    @Override
    public String getFormatErrorMessage(IMessage input) {
        return "Your configuration command was malformed.  Try again.";
    }
}
