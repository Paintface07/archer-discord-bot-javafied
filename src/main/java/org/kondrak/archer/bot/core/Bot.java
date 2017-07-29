package org.kondrak.archer.bot.core;

import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.configuration.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.IDiscordClient;

import java.util.Properties;

public class Bot {
    private static final Logger LOG = LoggerFactory.getLogger(Bot.class);

    private static Bot instance;

    private final Properties properties;
    private final SqlSessionFactory factory;
    private final IDiscordClient client;
    private final ConfigurationService configService;

    private Bot(Properties props, IDiscordClient client, SqlSessionFactory factory, ConfigurationService service) {
        this.client = client;
        this.properties = props;
        this.factory = factory;
        this.configService = service;
    }

    public static Bot getInstance(Properties props, IDiscordClient client, SqlSessionFactory factory, ConfigurationService service) {
        if(null == instance) {
            instance = new Bot(props, client, factory, service);
        }
        return instance;
    }

    public ContextBuilder builder() {
        return ContextBuilder.getInstance(properties, client, factory, configService);
    }
}
