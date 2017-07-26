package org.kondrak.archer.bot.configuration;

import org.kondrak.archer.bot.core.AbstractMessageCommand;
import org.kondrak.archer.bot.core.ArcherBotContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;

import java.util.List;

public class ConfigurationParameterizedCommand extends AbstractMessageCommand {

    public static final Logger LOG = LoggerFactory.getLogger(ConfigurationParameterizedCommand.class);

    private final ConfigDao configDao;
    private final String prefix;

    public ConfigurationParameterizedCommand(ArcherBotContext ctx, String command) {
        super(ctx, command);
        this.configDao = new ConfigDao(ctx.getFactory());
        this.prefix = ctx.getPrefix();
    }

    @Override
    public void execute(IMessage input) {
        // TODO: abstract command parsing into its own class
        String content = input.getContent().replace(getCommand()+ " ", "");

        if(content.startsWith(ConfigCommand.ADD + " ")) {
            input.reply("Adding configurations are not yet supported.");
        } else if(content.startsWith(ConfigCommand.REMOVE + " ")) {
            input.reply("Removing configurations are not yet supported.");
        } else if(content.startsWith(ConfigCommand.SHOW + " ")) {
            String[] p = content.replaceFirst(ConfigCommand.SHOW + " ", "").split(" ");
            if(p.length == 2) {
                ConfigScope type = ConfigScope.valueOf(p[0]);
                if(p[1] != null) {
                    switch (type) {
                        case GUILD:
                            handleCommand(ConfigType.valueOf(p[1]), type, input, ConfigScope.GUILD.toString().toLowerCase());
                            break;
                        case CHANNEL:
                            handleCommand(ConfigType.valueOf(p[1]), type, input, ConfigScope.CHANNEL.toString().toLowerCase());
                            break;
                        case USER:
                            handleCommand(ConfigType.valueOf(p[1]), type, input, ConfigScope.USER.toString().toLowerCase());
                            break;
                        default:
                            handleFormatError(input);
                    }
                } else {
                    handleFormatError(input);
                }
            } else {
                handleFormatError(input);
            }
        } else {
            handleFormatError(input);
        }
    }

    // TODO: split this into one method that handles permissions and another that handles validity
    // TODO: remove admin hard-coding
    @Override
    public boolean shouldExecute(IMessage msg) {
        if(null != msg.getContent() && msg.getContent().startsWith(getCommand())) {
            String content = msg.getContent().replace(getCommand() + " ", "");

            if(!content.matches("(?>" + ConfigCommand.SHOW + "|" + ConfigCommand.ADD + "|" + ConfigCommand.REMOVE +
                    ")(?>" + ConfigScope.GUILD + "|" + ConfigScope.CHANNEL + "|" + ConfigScope.USER +
                    ") (?> )(?>" + ConfigType.ARCHERISM_COMMAND + "|" + ConfigType.DICE_COMMAND + "|" +
                    ConfigType.TIMER_COMMAND + "|" + ConfigType.WORD_COMMAND + ")")) {
                if (!(msg.getAuthor().getStringID().equals(msg.getGuild().getOwner().getStringID())
                        || (msg.getAuthor().getName().equals("Paintface07")
                        && msg.getAuthor().getDiscriminator().equals("2733")))) {
                    handleFormatError(msg);
                } else {
                    return true;
                }
            } else {
                handleFormatError(msg);
            }
        }

        return false;
    }

    @Override
    public String getFormatErrorMessage(IMessage input) {
        return "Your configuration command was malformed, or you do not have permission to execute it.\n" +
                "\t * Please use the format: **" + prefix + "**config (GUILD|CHANNEL|USER) (ARCHERISM_COMMAND|DICE_COMMAND|TIMER_COMMAND|WORD_COMMAND)";
    }

    private void handleCommand(ConfigType parameter, ConfigScope scope, IMessage input, String msg) {
        List<Configuration> config = configDao.getConfigurationByNameScopeAndType(parameter,
                scope, input.getGuild().getStringID());
        if(null != config && !config.isEmpty()) {
            StringBuilder reply = new StringBuilder("\n");
            for (Configuration c : config) {
                reply.append("\t * ").append(c.toString()).append("\n");
            }
            input.reply(reply.toString());
        } else {
            input.reply("No "+ msg + " configurations are setup.");
        }
    }
}
