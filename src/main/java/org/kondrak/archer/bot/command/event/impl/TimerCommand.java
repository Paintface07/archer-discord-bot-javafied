package org.kondrak.archer.bot.command.event.impl;

import org.kondrak.archer.bot.command.event.AbstractMessageCommand;
import org.kondrak.archer.bot.context.ArcherBotContext;
import org.kondrak.archer.bot.util.Emote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * Created by nosferatu on 7/11/17.
 */
public class TimerCommand extends AbstractMessageCommand {

    public static final Logger LOG = LoggerFactory.getLogger(TimerCommand.class);

    public TimerCommand(ArcherBotContext ctx, String command) {
        super(ctx, command);
    }

    @Override
    public IMessage execute(IMessage input) {
        String content = input.getContent().replace(getCommand()+ " ", "");
        LOG.info("Content {}{}]", (content.matches(getFormatRegex()) ? "matches [" : "does not match ["), content);

        if(content.matches(getFormatRegex())) {
            String[] parts = content.split(" ");

            if(parts.length >= 2) {
                List<String> partsArray = Arrays.stream(parts).collect(Collectors.toList());
                long wait = Long.valueOf(partsArray.get(0));
                partsArray.remove(0);
                String message = partsArray.stream().reduce("", (a, p) -> a += " " + p);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            input.reply(Emote.ALARM_CLOCK + " Time for" + message + "!");
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

    @Override
    public String getFormatRegex() {
        return "[0-9]{1,13}[ ][A-z, ]+";
    }

    @Override
    public String getFormatErrorMessage(IMessage input) {
        return "Your timer command was improperly formatted.\n" +
                "Please use the following format:\n" +
                "\t* <prefix>timer [1-13 digit ms] [name]";
    }
}
