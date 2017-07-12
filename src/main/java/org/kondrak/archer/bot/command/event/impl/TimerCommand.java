package org.kondrak.archer.bot.command.event.impl;

import org.kondrak.archer.bot.command.event.AbstractMessageCommand;
import org.kondrak.archer.bot.context.ArcherBotContext;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nosferatu on 7/11/17.
 */
public class TimerCommand extends AbstractMessageCommand {

    public TimerCommand(ArcherBotContext ctx, String command) {
        super(ctx, command);
    }

    @Override
    public IMessage execute(IMessage input) {
        String content = input.getContent().replace(getCommand()+ " ", "");
        System.out.println("Content " + (content.matches("[0-9]{1,13}[ ]\\w*") ? "matches [" : "does not match [") + content + "]");

        if(content.matches("[0-9]{1,13}[ ]\\w*")) {
            String[] parts = content.split(" ");

            if(parts.length == 2) {
                List<String> partsArray = Arrays.stream(parts).collect(Collectors.toList());
                long wait = Long.valueOf(partsArray.get(0));
                partsArray.remove(0);
                String message = partsArray.stream().reduce("", (p, a) -> a += " ");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            input.reply("Time for " + message + "!");
                        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                            e.printStackTrace();
                        }
                    }
                }, wait);
            } else {
                handleFailure(input);
            }
        } else {
            handleFailure(input);
        }

        return null;
    }

    private static void handleFailure(IMessage input) {
        try {
            input.reply("Your timer command was improperly formatted.\n" +
                    "Please use the following format:\n" +
                    "\t* <prefix>timer [1-13 digit ms] [name]");
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }
}
