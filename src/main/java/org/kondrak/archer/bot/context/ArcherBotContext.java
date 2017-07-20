package org.kondrak.archer.bot.context;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.kondrak.archer.bot.command.CommandRegistry;
import org.kondrak.archer.bot.command.event.impl.ArcherismCommand;
import org.kondrak.archer.bot.command.event.impl.DiceRollCommand;
import org.kondrak.archer.bot.command.event.impl.HelpCommand;
import org.kondrak.archer.bot.command.event.impl.LoadExistingMessagesCommand;
import org.kondrak.archer.bot.command.event.impl.TimerCommand;
import org.kondrak.archer.bot.command.event.impl.WordUsageCommand;
import org.kondrak.archer.bot.dao.mappers.MessageMapper;
import org.kondrak.archer.bot.listener.MessageListener;
import org.kondrak.archer.bot.listener.ReadyListener;
import org.kondrak.archer.bot.listener.UserListener;
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

    public ArcherBotContext(String[] args) {
        this.args = args;
        this.ds = setupDataSource(args[1], args[2], args[3]);
        this.factory = configureMybatis(args[1], args[2], args[3]);
        this.client = setupClient(args[0]);
        final String prefix = args[4];

        this.registry = new CommandRegistry(client);
        registry.registerAll(
                new ArcherismCommand(this, prefix + "archerism"),
                new HelpCommand(this, prefix + "help"),
                new LoadExistingMessagesCommand(this, prefix + "admin load"),
                new WordUsageCommand(this, prefix + "word"),
                new TimerCommand(this, prefix + "timer"),
                new DiceRollCommand(this, prefix + "roll"));

        this.dispatcher = client.getDispatcher();
        dispatcher.registerListener(new MessageListener(this));
        dispatcher.registerListener(new ReadyListener(this));
        dispatcher.registerListener(new UserListener(this));
    }

    private SqlSessionFactory configureMybatis(String server, String user, String password) {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setDatabaseName("archer_java");
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setServerName(server);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
//        configuration.addMapper(MessageMapper.class);
        configuration.addMappers("org.kondrak.archer.bot.dao.mappers");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(configuration);
        return factory;
    }

    private static PGConnectionPoolDataSource setupDataSource(String server, String user, String password) {
        PGConnectionPoolDataSource datasource = new PGConnectionPoolDataSource();
        datasource.setServerName(server);
        datasource.setDatabaseName("archer_java");
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

    public PGConnectionPoolDataSource getDatasource() {
        return ds;
    }

    public SqlSessionFactory getFactory() {
        return factory;
    }
}
