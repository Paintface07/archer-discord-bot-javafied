package org.kondrak.archer.bot.configuration;

import org.kondrak.archer.bot.core.AbstractMessageCommand;
import org.kondrak.archer.bot.core.ArcherBotContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;

import java.util.List;

import static org.kondrak.archer.bot.configuration.ConfigCommand.ADD;
import static org.kondrak.archer.bot.configuration.ConfigCommand.REMOVE;
import static org.kondrak.archer.bot.configuration.ConfigCommand.SHOW;
import static org.kondrak.archer.bot.configuration.ConfigScope.GUILD;
import static org.kondrak.archer.bot.configuration.ConfigScope.CHANNEL;
import static org.kondrak.archer.bot.configuration.ConfigScope.USER;

public class ConfigurationParameterizedCommand extends AbstractMessageCommand {

    public static final Logger LOG = LoggerFactory.getLogger(ConfigurationParameterizedCommand.class);

    private final String prefix;

    public ConfigurationParameterizedCommand(ArcherBotContext ctx, String command) {
        super(ctx, command);
        this.prefix = ctx.getPrefix();
    }

    @Override
    public void execute(IMessage input) {
        // TODO: abstract command parsing into its own class
        String content = input.getContent().replace(getCommand()+ " ", "");

        if(content.startsWith(ADD + " ")) {
            String[] p = content.replaceFirst(ADD + " ", "").split(" ");
            if (p.length == 2) {
                ConfigScope scope = ConfigScope.valueOf(p[0]);
                ConfigType type = ConfigType.valueOf(p[1]);
                switch(scope) {
                    case GUILD:
                        addCommand(type, scope, input);
                        break;
                    case CHANNEL:
                        input.reply("Adding channel configurations is not yet supported.");
//                        addCommand(type, scope, input);
                        break;
                    case USER:
                        input.reply("Adding user configurations is not yet supported.");
//                        addCommand(type, scope, input);
                        break;
                    default:
                        input.reply("'" + scope + "' is not a valid configuration scope.");
                }
            } else {
                handleFormatError(input);
            }
        } else if(content.startsWith(REMOVE + " ")) {
            input.reply("Removing configurations are not yet supported.");
        } else if(content.startsWith(SHOW + " ")) {
            String[] p = content.replaceFirst(SHOW + " ", "").split(" ");
            if(p.length == 2) {
                ConfigScope scope = ConfigScope.valueOf(p[0]);
                ConfigType type = ConfigType.valueOf(p[1]);
                if(p[1] != null) {
                    switch (scope) {
                        case GUILD:
                            showCommand(type, scope, input);
                            break;
                        case CHANNEL:
//                            showCommand(type, scope, input);
                            input.reply("Displaying channel configurations is not yet supported.");
                            break;
                        case USER:
//                            showCommand(type, scope, input);
                            input.reply("Displaying user configurations is not yet supported.");
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

            if(content.matches(ConfigCommand.anyOfRegex() + "(?> )" + ConfigScope.anyOfRegex() + "(?> )" +
                    ConfigType.anyOfRegex())) {
                if (!(msg.getAuthor().getStringID().equals(msg.getGuild().getOwner().getStringID())
                        || (msg.getAuthor().getName().equals(getCtx().getProperties().getProperty("admin.name"))
                        && msg.getAuthor().getDiscriminator().equals(getCtx().getProperties().getProperty("admin.discriminator"))))) {
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
                "\t * Please use the format: **" + prefix + "**config (" + ConfigCommand.pipeDelimited() +
                ") (" + ConfigScope.pipeDelimited() + ") (" + ConfigType.pipeDelimited() + ")";
    }

    private void showCommand(ConfigType type, ConfigScope scope, IMessage input) {
        List<Configuration> config = configService.getConfigurationByNameScopeAndType(type, scope,
                input.getGuild().getStringID());
        if(null != config && !config.isEmpty()) {
            StringBuilder reply = new StringBuilder("\n");
            for (Configuration c : config) {
                reply.append("\t * **")
                        .append(c.getConfigType())
                        .append("** is **").append(c.getConfigValue())
                        .append("** for this **").append(c.getScope().toString().toLowerCase())
                        .append("**\n");
            }
            input.reply(reply.toString());
        } else {
            input.reply("No "+ type + " configurations are setup.");
        }
    }

    // TODO: use IDiscordObject instead so arbitrary stringIDs can be handled
    private void addCommand(ConfigType type, ConfigScope scope, IMessage input) {
        if(!configService.isConfiguredForGuild(input.getGuild(), type)) {
            int inserts = configService.addBooleanConfiguration(type, scope, input.getGuild().getStringID());
            if(inserts > 0) {
                input.reply("**" + type + "** was configured for this **" + scope + "**.");
            }
        } else {
            input.reply("**" + type + "** is already configured for this **" + scope + "**.");
        }
    }
}
