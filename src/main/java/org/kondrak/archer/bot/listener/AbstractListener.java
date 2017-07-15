package org.kondrak.archer.bot.listener;

import org.kondrak.archer.bot.command.CommandRegistry;
import org.kondrak.archer.bot.context.ArcherBotContext;
import org.kondrak.archer.bot.dao.MessageDao;
import sx.blah.discord.api.IDiscordClient;

import javax.sql.DataSource;

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
