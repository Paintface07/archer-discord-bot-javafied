package org.kondrak.archer.bot.command.event.impl;

import org.kondrak.archer.bot.command.event.AbstractMessageCommand;
import org.kondrak.archer.bot.context.ArcherBotContext;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Created by nosferatu on 7/11/17.
 */
public class DiceRollCommand extends AbstractMessageCommand {

    public DiceRollCommand(ArcherBotContext ctx, String command) {
        super(ctx, command);
    }

    @Override
    public IMessage execute(IMessage input) {
        String content = input.getContent().replace(getCommand()+ " ", "");
        System.out.println("Content " + (content.matches("[0-9]{1,13}d[0-9]{1,13}") ? "matches [" : "does not match [") + content + "]");

        if(content.matches("[0-9]{1,13}d[0-9]{1,13}")) {
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
                e.printStackTrace();
            }

        } else {
            handleFailure(input);
        }

        return null;
    }

    private static void handleFailure(IMessage input) {
        try {
            input.reply("Your dice roll command was improperly formatted.\n" +
                    "Please use the following format:\n" +
                    "\t* <prefix>roll [number]d[sides]");
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }
}
