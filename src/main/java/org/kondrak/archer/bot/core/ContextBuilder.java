package org.kondrak.archer.bot.core;

import org.apache.ibatis.session.SqlSessionFactory;
import org.kondrak.archer.bot.configuration.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

import java.util.Properties;

/**
 * Created by Administrator on 2/24/2017.
 */
public class ContextBuilder {

    public static final Logger LOG = LoggerFactory.getLogger(ContextBuilder.class);

    public static final String EVENT_LOGGER_FORMAT = "Event triggered: {}";

    private static ContextBuilder instance;

    private final Properties properties;
    private final SqlSessionFactory factory;
    private final IDiscordClient client;
    private final EventDispatcher dispatcher;
    private final ConfigurationService configService;

    private ContextBuilder(Properties props, IDiscordClient client, SqlSessionFactory factory,
            ConfigurationService configService) {
        // hide constructor

        this.properties = props;
        this.factory = factory;
        this.client = client;
        this.dispatcher = client.getDispatcher();
        this.configService = configService;
    }

    public static ContextBuilder getInstance(Properties props, IDiscordClient client, SqlSessionFactory factory,
            ConfigurationService configService) {
        if(null == instance) {
            instance = new ContextBuilder(props, client, factory, configService);
        }
        return instance;
    }

    public static ContextBuilder getInstance() {
        return instance;
    }

    public Context context() {
        return Context.getInstance(properties, client, configService, factory);
    }

    public ContextBuilder withListeners(Object... listener) {
        dispatcher.registerListeners(listener);
        return this;
    }

    public SqlSessionFactory getFactory() {
        return factory;
    }
}
