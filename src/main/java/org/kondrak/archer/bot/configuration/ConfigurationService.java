package org.kondrak.archer.bot.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

public class ConfigurationService {

    private final ConfigDao configDao;

    public ConfigurationService(SqlSessionFactory factory) {
        this.configDao = new ConfigDao(factory);
    }

    // TODO: make the configuration check an abstraction so it is executed by every command
    public boolean isConfiguredForGuild(IGuild guild, IUser user, ConfigType type){
        List<Configuration> configs = configDao.getConfigurationByNameScopeAndType(ConfigType.ARCHERISM_COMMAND,
                ConfigScope.GUILD, guild.getStringID());
        if(configs != null && !configs.isEmpty()) {
            for (Configuration c : configs) {
                if (Boolean.parseBoolean(c.getConfigValue())) {
                    return true;
                }
            }
        } else {
            user.getOrCreatePMChannel().sendMessage(type + " is not enabled in " + guild.getName());
        }
        return false;
    }
}
