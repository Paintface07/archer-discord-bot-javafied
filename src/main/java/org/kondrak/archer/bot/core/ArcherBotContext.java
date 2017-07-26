package org.kondrak.archer.bot.core;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.kondrak.archer.bot.archerism.ArcherismBasicCommand;
import org.kondrak.archer.bot.configuration.ConfigurationParameterizedCommand;
import org.kondrak.archer.bot.core.listener.MessageListener;
import org.kondrak.archer.bot.core.listener.ReadyListener;
import org.kondrak.archer.bot.core.listener.UserListener;
import org.kondrak.archer.bot.dice.DiceRollParameterizedCommand;
import org.kondrak.archer.bot.help.HelpBasicCommand;
import org.kondrak.archer.bot.load.LoadMessagesBasicAdminCommand;
import org.kondrak.archer.bot.timer.TimerParameterizedCommand;
import org.kondrak.archer.bot.word.WordBasicCommand;
import org.postgresql.ds.PGConnectionPoolDataSource;
import org.postgresql.ds.PGPoolingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

import java.util.Properties;

/**
 * Created by Administrator on 2/24/2017.
 */
public class ArcherBotContext {

    public static final Logger LOG = LoggerFactory.getLogger(ArcherBotContext.class);

    public static final String EVENT_LOGGER_FORMAT = "Event triggered: {}";

    private final Properties properties;
    private final SqlSessionFactory factory;
    private final IDiscordClient client;
    private final CommandRegistry registry;
    private final EventDispatcher dispatcher;
    private final String prefix;

    public ArcherBotContext(Properties props) {
        this.properties = props;
        this.factory = configureMybatis(props.getProperty("db.server"),
                props.getProperty("db.user"), props.getProperty("db.password"),
                props.getProperty("db.name"));
        this.client = setupClient(props.getProperty("discord.token"));
        this.prefix = props.getProperty("app.prefix");

        this.registry = new CommandRegistry(client);
        registry.registerAll(
                new ArcherismBasicCommand(this, prefix + "archerism"),
                new HelpBasicCommand(this, prefix + "help"),
                new LoadMessagesBasicAdminCommand(this, prefix + "admin load"),
                new WordBasicCommand(this, prefix + "word"),
                new TimerParameterizedCommand(this, prefix + "timer"),
                new DiceRollParameterizedCommand(this, prefix + "roll"),
                new ConfigurationParameterizedCommand(this, prefix + "config"));

        this.dispatcher = client.getDispatcher();
        dispatcher.registerListener(new MessageListener(this));
        dispatcher.registerListener(new ReadyListener(this));
        dispatcher.registerListener(new UserListener(this));
    }

    private SqlSessionFactory configureMybatis(String server, String user, String password, String database) {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setDatabaseName(database);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setServerName(server);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMappers("org.kondrak.archer.bot");
        return new SqlSessionFactoryBuilder().build(configuration);
    }

    private static PGConnectionPoolDataSource setupDataSource(String server, String user, String password, String database) {
        PGConnectionPoolDataSource datasource = new PGConnectionPoolDataSource();
        datasource.setServerName(server);
        datasource.setDatabaseName(database);
        datasource.setUser(user);
        datasource.setPassword(password);
        return datasource;
    }

    private static IDiscordClient setupClient(String appKey) {
        try {
            return buildClient(appKey, true);
        } catch(DiscordException dx) {
            LOG.error("Could not create discord client!", dx);
            return null;
        }
    }

    private static IDiscordClient buildClient(String token, boolean login) {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(token);
        LOG.info("Attempting login with: {}", token);

        if (login) {
            return clientBuilder.login();
        } else {
            return clientBuilder.build();
        }
    }

    public IDiscordClient getClient() {
        return client;
    }

    public CommandRegistry getRegistry() {
        return registry;
    }

    public EventDispatcher getDispatcher() {
        return dispatcher;
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
}
