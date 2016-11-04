package org.kondrak.archer.bot;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.kondrak.archer.bot.listener.MessageListener;
import org.postgresql.ds.PGPoolingDataSource;
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
        try {
            PGPoolingDataSource datasource = new PGPoolingDataSource();
            datasource.setDataSourceName("pg datasource");
            datasource.setServerName("localhost");
            datasource.setDatabaseName("ARCHER_JAVA");
            datasource.setUser(args[1]);
            datasource.setPassword(args[2]);
            datasource.setMaxConnections(5);

            try {
                String config = Resources.toString(Resources.getResource("config.json"), Charsets.UTF_8);
            } catch(IOException ex) {
                throw new RuntimeException("Could not read configuration");
            }

            IDiscordClient client = getClient(args[0], true);
            EventDispatcher dispatcher = client.getDispatcher();
            dispatcher.registerListener(new MessageListener(datasource));
        } catch(DiscordException dx) {
            throw new RuntimeException("Could not create discord client!");
        }
    }

    public static IDiscordClient getClient(String token, boolean login) throws DiscordException {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(token);
        if (login) {
            return clientBuilder.login();
        } else {
            return clientBuilder.build();
        }
    }
}
