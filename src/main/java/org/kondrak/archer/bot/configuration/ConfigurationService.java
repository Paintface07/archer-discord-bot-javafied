package org.kondrak.archer.bot.configuration;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;
import java.util.stream.Collectors;

public class ConfigurationService {

    private final ConfigDao configDao;

    public ConfigurationService(ConfigDao dao) {
        this.configDao = dao;
    }

    // TODO: make the configuration check an abstraction so it is executed by every command
    // TODO: change these configuration checks so they are more generic
    public boolean isConfiguredForGuild(IGuild guild, ConfigType type) {
        return configDao.getConfigurationByNameScopeAndType(type,
                ConfigScope.GUILD, guild.getStringID())
                .stream().map(c -> Boolean.parseBoolean(c.getConfigValue()))
                .collect(Collectors.toList())
                .contains(true);
    }

    public boolean isConfiguredForChannel(IChannel channel, ConfigType type) {
        return configDao.getConfigurationByNameScopeAndType(type,
                ConfigScope.CHANNEL, channel.getStringID())
                .stream().map(c -> Boolean.parseBoolean(c.getConfigValue()))
                .collect(Collectors.toList())
                .contains(true);
    }

    public boolean isConfiguredForUser(IUser user, ConfigType type) {
        return configDao.getConfigurationByNameScopeAndType(type,
                ConfigScope.USER, user.getStringID())
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

    public int removeBooleanConfiguration(ConfigType type, ConfigScope scope, String guildId) {
        return configDao.removeBooleanConfiguration(type, scope, guildId);
    }
}
