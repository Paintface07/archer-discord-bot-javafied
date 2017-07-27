package org.kondrak.archer.bot.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;
import java.util.stream.Collectors;

public class ConfigurationService {

    private final ConfigDao configDao;

    public ConfigurationService(SqlSessionFactory factory) {
        this.configDao = new ConfigDao(factory);
    }

    // TODO: make the configuration check an abstraction so it is executed by every command
    // TODO: fix always passing.  Need to add guild ID to query.
    public boolean isConfiguredForGuild(IGuild guild, IUser user, ConfigType type){
        return configDao.getConfigurationByNameScopeAndType(ConfigType.ARCHERISM_COMMAND,
                ConfigScope.GUILD, guild.getStringID())
                .stream().map(c -> Boolean.parseBoolean(c.getConfigValue()))
                .collect(Collectors.toList())
                .contains(true);
    }

    public List<Configuration> getConfigurationByNameScopeAndType(ConfigType type, ConfigScope scope, String fkey) {
        return configDao.getConfigurationByNameScopeAndType(type, scope, fkey);
    }

    public int addBooleanConfiguration(ConfigType type, ConfigScope scope, String guildId) {
        return configDao.addBooleanConfiguration(type, scope, guildId);
    }
}
