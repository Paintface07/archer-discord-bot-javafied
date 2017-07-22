package org.kondrak.archer.bot.command.event.impl;

import org.kondrak.archer.bot.command.event.AbstractMessageCommand;
import org.kondrak.archer.bot.context.ArcherBotContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Created by nosferatu on 7/11/17.
 */
public class DiceRollParameterizedCommand extends AbstractMessageCommand {

    private static final Logger LOG = LoggerFactory.getLogger(DiceRollParameterizedCommand.class);

    public DiceRollParameterizedCommand(ArcherBotContext ctx, String command) {
        super(ctx, command);
    }

    // TODO : make these types of commands have their own interface and abstract class to avoid repetition regex application
    @Override
    public void execute(IMessage input) {
        String content = input.getContent().replace(getCommand()+ " ", "");

        String[] parts = content.replace(" ", "").split("d");
        int number = Integer.valueOf(parts[0]);
        int sides = Integer.valueOf(parts[1]);
        int total = 0;

        for(int n = 0; n < number; n++) {
            total += (int) (Math.random() * sides) + 1;
        }

        try {
            input.reply("You rolled: " + total);
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            LOG.error("Could not respond to DiceRollParameterizedCommand: ", e);
        }
    }

    @Override
    public boolean shouldExecute(IMessage input) {
        if(null != input.getContent() && input.getContent().startsWith(getCommand())) {
            String content = input.getContent().replace(getCommand()+ " ", "");

            if(!content.matches("[0-9]{1,3}d[0-9]{1,3}")) {
                handleFailure(input);
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getFormatErrorMessage(IMessage input) {
        return "Your dice roll command was improperly formatted.\n" +
                "Please use the following format:\n" +
                "\t* <prefix>roll [number]d[sides]";
    }
}
