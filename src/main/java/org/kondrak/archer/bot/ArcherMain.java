package org.kondrak.archer.bot;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.kondrak.archer.bot.command.CommandRegistry;
import org.kondrak.archer.bot.command.event.impl.ArcherismCommand;
import org.kondrak.archer.bot.command.event.impl.HelpCommand;
import org.kondrak.archer.bot.listener.MessageListener;
import org.postgresql.ds.PGConnectionPoolDataSource;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

import java.io.IOException;

/**
 * Created by Administrator on 11/3/2016.
 */
public class ArcherMain {

    public static void main(String[] args) {
        PGConnectionPoolDataSource datasource = setupDataSource(args[1], args[2]);
        String config = readConfiguration("config/config.json");
        IDiscordClient client = setupClient(args[0]);
        CommandRegistry registry = new CommandRegistry(client);
        registry.register(new ArcherismCommand(client, registry, "!archerism"));
        registry.register(new HelpCommand(client, registry, "!help"));
        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(new MessageListener(datasource, registry));
    }

    private static String readConfiguration(String location) {
        try {
            return Resources.toString(Resources.getResource(location), Charsets.UTF_8);
        } catch(IOException ex) {
            throw new RuntimeException("Could not read configuration");
        }
    }

    private static IDiscordClient setupClient(String appKey) {
        try {
            IDiscordClient client = getClient(appKey, true);
            return client;
        } catch(DiscordException dx) {
            throw new RuntimeException("Could not create discord client!");
        }
    }

    private static PGConnectionPoolDataSource setupDataSource(String user, String password) {
        PGConnectionPoolDataSource datasource = new PGConnectionPoolDataSource();
        datasource.setServerName("localhost");
        datasource.setDatabaseName("archer_java");
        datasource.setUser(user);
        datasource.setPassword(password);
        return datasource;
    }

    private static IDiscordClient getClient(String token, boolean login) throws DiscordException {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(token);
        if (login) {
            return clientBuilder.login();
        } else {
            return clientBuilder.build();
        }
    }
}
