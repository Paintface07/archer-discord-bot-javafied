package org.kondrak.archer.bot.model;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IPresence;

/**
 * Created by nosferatu on 7/14/17.
 */
public class User {

    private final IDiscordClient client;
    private final String name;
    private final String id;
    private final String discriminator;
    private final String avatar;
    private final IPresence presence;
    private final boolean isBot;

    public User(IDiscordClient client, String name, String id, String discriminator, String avatar, IPresence presence,
            boolean isBot) {
        this.client = client;
        this.name = name;
        this.id = id;
        this.discriminator = discriminator;
        this.avatar = avatar;
        this.presence = presence;
        this.isBot = isBot;
    }

    public IDiscordClient getClient() {
        return client;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public String getAvatar() {
        return avatar;
    }

    public IPresence getPresence() {
        return presence;
    }

    public boolean isBot() {
        return isBot;
    }
}
