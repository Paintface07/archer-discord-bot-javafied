package org.kondrak.archer.bot.core;

import org.kondrak.archer.bot.archerism.ArcherismBasicCommand;
import org.kondrak.archer.bot.archerism.ArcherismDao;
import org.kondrak.archer.bot.configuration.ConfigurationParameterizedCommand;
import org.kondrak.archer.bot.dice.DiceRollParameterizedCommand;
import org.kondrak.archer.bot.help.HelpBasicCommand;
import org.kondrak.archer.bot.load.LoadMessagesBasicAdminCommand;
import org.kondrak.archer.bot.timer.TimerParameterizedCommand;
import org.kondrak.archer.bot.word.WordBasicCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandFactory {

    private static final String ARCHERISM = "archerism";
    private static final String CONFIG = "config";
    private static final String DICE = "roll";
    private static final String HELP = "help";
    private static final String LOAD = "admin load";
    private static final String TIMER = "timer";
    private static final String WORD = "word";

    private CommandFactory() {
        // hide constructor
    }

    public static MessageEventCommand build(final String commandTxt) {
        if(commandTxt.equalsIgnoreCase(ARCHERISM)) {
            return new ArcherismBasicCommand(ARCHERISM, new ArcherismDao(Context.getInstance().getFactory()));
        } else if(commandTxt.equalsIgnoreCase(CONFIG)) {
            return new ConfigurationParameterizedCommand(CONFIG);
        } else if(commandTxt.equalsIgnoreCase(DICE)) {
            return new DiceRollParameterizedCommand(DICE);
        } else if(commandTxt.equalsIgnoreCase(LOAD)) {
            return new LoadMessagesBasicAdminCommand(LOAD);
        } else if(commandTxt.equalsIgnoreCase(TIMER)) {
            return new TimerParameterizedCommand(TIMER);
        } else if(commandTxt.equalsIgnoreCase(WORD)) {
            return new WordBasicCommand(WORD);
        } else if(commandTxt.equalsIgnoreCase(HELP)) {
            return new HelpBasicCommand(HELP);
        } else {
            return null;
        }
    }

    public static List<MessageEventCommand> buildAll(final String... commands) {
        List<MessageEventCommand> commandList = new ArrayList<>();
        for(String c : commands) {
            commandList.add(build(c));
        }
        return commandList;
    }
}
