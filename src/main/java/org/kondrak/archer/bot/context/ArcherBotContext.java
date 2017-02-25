package org.kondrak.archer.bot.context;

import org.kondrak.archer.bot.command.CommandRegistry;
import org.kondrak.archer.bot.command.event.impl.ArcherismCommand;
import org.kondrak.archer.bot.command.event.impl.HelpCommand;
import org.kondrak.archer.bot.listener.MessageListener;
import org.kondrak.archer.bot.listener.ReadyListener;
import org.postgresql.ds.PGConnectionPoolDataSource;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

/**
 * Created by Administrator on 2/24/2017.
 */
public class ArcherBotContext {
    private final String[] args;
    private final PGConnectionPoolDataSource ds;
    private final IDiscordClient client;
    private final CommandRegistry registry;
    private final EventDispatcher dispatcher;

    public ArcherBotContext(String[] args) {
        this.args = args;
        this.ds = setupDataSource(args[1], args[2]);
        this.client = setupClient(args[0]);

        this.registry = new CommandRegistry(client);
        registry.register(new ArcherismCommand(this, "!archerism"));
        registry.register(new HelpCommand(this, "!help"));

        this.dispatcher = client.getDispatcher();
        dispatcher.registerListener(new MessageListener(this));
        dispatcher.registerListener(new ReadyListener(this));
    }

    private static PGConnectionPoolDataSource setupDataSource(String user, String password) {
        PGConnectionPoolDataSource datasource = new PGConnectionPoolDataSource();
        datasource.setServerName("localhost");
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
}
