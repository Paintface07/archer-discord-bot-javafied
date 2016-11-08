package org.kondrak.archer.bot;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.kondrak.archer.bot.command.CommandRegistry;
import org.kondrak.archer.bot.command.event.impl.ArcherismCommand;
import org.kondrak.archer.bot.listener.MessageListener;
import org.postgresql.ds.PGPoolingDataSource;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by Administrator on 11/3/2016.
 */
public class ArcherMain {

    public static void main(String[] args) {
        DataSource datasource = setupDataSource(args[1], args[2]);
        String config = readConfiguration("config/config.json");
        CommandRegistry registry = new CommandRegistry();
        IDiscordClient client = setupClient(args[0], datasource, registry);
        registry.register(new ArcherismCommand(client, registry, "!archerism"));
    }

    private static String readConfiguration(String location) {
        try {
            return Resources.toString(Resources.getResource(location), Charsets.UTF_8);
        } catch(IOException ex) {
            throw new RuntimeException("Could not read configuration");
        }
    }

    private static IDiscordClient setupClient(String appKey, DataSource datasource, CommandRegistry registry) {
        try {
            IDiscordClient client = getClient(appKey, true);
            EventDispatcher dispatcher = client.getDispatcher();
            dispatcher.registerListener(new MessageListener(datasource, registry));
            return client;
        } catch(DiscordException dx) {
            throw new RuntimeException("Could not create discord client!");
        }
    }

    private static DataSource setupDataSource(String user, String password) {
        PGPoolingDataSource datasource = new PGPoolingDataSource();
        datasource.setDataSourceName("pg datasource");
        datasource.setServerName("localhost");
        datasource.setDatabaseName("ARCHER_JAVA");
        datasource.setUser(user);
        datasource.setPassword(password);
        datasource.setMaxConnections(5);
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
