package org.kondrak.archer.bot.timer;

import org.kondrak.archer.bot.configuration.ConfigType;
import org.kondrak.archer.bot.core.AbstractMessageCommand;
import org.kondrak.archer.bot.core.util.Emote;
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
public class TimerParameterizedCommand extends AbstractMessageCommand {

    public static final Logger LOG = LoggerFactory.getLogger(TimerParameterizedCommand.class);

    public TimerParameterizedCommand(String command) {
        super(command);
    }

    @Override
    public void execute(IMessage input) {
        String content = input.getContent().replace(getCommand()+ " ", "");

        String[] parts = content.split(" ");

        if(parts.length >= 2) {
            List<String> partsArray = Arrays.stream(parts).collect(Collectors.toList());
            long wait = Long.parseLong(partsArray.get(0));
            partsArray.remove(0);
            String message = partsArray.stream().reduce("", (a, p) -> a += " " + p);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        input.reply(Emote.ALARM_CLOCK + " Time for" + message + "!");
                    } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                        LOG.error("Could not execute TimerParameterizedCommand: ", e);
                    }
                }
            }, wait);
        } else {
            handleFormatError(input);
        }
    }

    @Override
    public boolean shouldExecute(IMessage input) {
        if(null != input.getContent() && input.getContent().startsWith(getCommand())
                && configService.isConfiguredForGuild(input.getGuild(), ConfigType.TIMER_COMMAND)) {
            String content = input.getContent().replace(getCommand() + " ", "");

            if(!content.matches("[0-9]{1,13}[ ][A-z, ]+")) {
                handleFormatError(input);
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getFormatErrorMessage(IMessage input) {
        return "Your timer command was improperly formatted.\n" +
                "Please use the following format:\n" +
                "\t* <prefix>timer [1-13 digit ms] [name]";
    }
}
