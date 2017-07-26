package org.kondrak.archer.bot.core.listener;

import org.kondrak.archer.bot.configuration.ConfigDao;
import org.kondrak.archer.bot.core.CommandRegistry;
import org.kondrak.archer.bot.core.ArcherBotContext;
import sx.blah.discord.api.IDiscordClient;

/**
 * Created by nosferatu on 7/14/17.
 */
public abstract class AbstractListener {
    private final IDiscordClient client;
    private final CommandRegistry registry;

    public AbstractListener(ArcherBotContext ctx) {
        this.client = ctx.getClient();
        this.registry = ctx.getRegistry();
    }

    public IDiscordClient getClient() {
        return client;
    }

    public CommandRegistry getRegistry() {
        return registry;
    }
}
