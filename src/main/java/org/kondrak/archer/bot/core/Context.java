package org.kondrak.archer.bot.core;

import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.configuration.ConfigurationService;
import sx.blah.discord.api.IDiscordClient;

import java.util.Properties;

public class Context {

    public static Context instance;

    private final Properties properties;
    private final SqlSessionFactory factory;
    private final ConfigurationService configService;
    private final String prefix;
    private final IDiscordClient client;
    private CommandRegistry registry;

    private Context(Properties properties, IDiscordClient client, ConfigurationService service, SqlSessionFactory factory) {
        this.properties = properties;
        this.client = client;
        this.factory = factory;
        this.configService = service;
        this.prefix = properties.getProperty("app.prefix");
    }

    public static Context getInstance(Properties properties, IDiscordClient client, ConfigurationService service, SqlSessionFactory factory) {
        if(null == instance) {
            instance = new Context(properties, client, service, factory);
        }
        return instance;
    }

    public static Context getInstance() {
        return instance;
    }

    public Context withCommands(String... commands) {
        this.registry = new CommandRegistry(client);
        this.registry.registerAll(CommandFactory.buildAll(commands));
        return this;
    }

    public IDiscordClient getClient() {
        return client;
    }

    public CommandRegistry getRegistry() {
        return registry;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getPrefix() {
        return prefix;
    }

    public SqlSessionFactory getFactory() {
        return factory;
    }

    public ConfigurationService getConfigService() {
        return configService;
    }
}
