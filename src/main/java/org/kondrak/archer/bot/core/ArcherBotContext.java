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

/**
 * Created by Administrator on 2/24/2017.
 */
public class ArcherBotContext {

    public static final Logger LOG = LoggerFactory.getLogger(ArcherBotContext.class);

    private final String[] args;
    private final PGConnectionPoolDataSource ds;
    private final SqlSessionFactory factory;
    private final IDiscordClient client;
    private final CommandRegistry registry;
    private final EventDispatcher dispatcher;
    private final String prefix;

    public ArcherBotContext(String[] args) {
        this.args = args;
        this.ds = setupDataSource(args[1], args[2], args[3], args[5]);
        this.factory = configureMybatis(args[1], args[2], args[3], args[5]);
        this.client = setupClient(args[0]);
        this.prefix = args[4];

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
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(configuration);
        return factory;
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
            IDiscordClient client = buildClient(appKey, true);
            return client;
        } catch(DiscordException dx) {
            throw new RuntimeException("Could not create discord client!");
        }
    }

    private static IDiscordClient buildClient(String token, boolean login) throws DiscordException {
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

    public String[] getArgs() {
        return args;
    }

    public String getPrefix() {
        return prefix;
    }

    public PGConnectionPoolDataSource getDatasource() {
        return ds;
    }

    public SqlSessionFactory getFactory() {
        return factory;
    }
}
