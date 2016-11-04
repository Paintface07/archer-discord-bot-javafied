package org.kondrak.archer.bot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

/**
 * Created by Administrator on 11/3/2016.
 */
public class ArcherMain {

    public static void main(String[] args) {
        try {
            IDiscordClient client = getClient(args[0], true);
            EventDispatcher dispatcher = client.getDispatcher();
            dispatcher.registerListener(new MessageListener());
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
