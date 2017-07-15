package org.kondrak.archer.bot.listener;

import org.kondrak.archer.bot.command.CommandRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.module.ModuleDisabledEvent;
import sx.blah.discord.handle.impl.events.module.ModuleEnabledEvent;

import javax.sql.DataSource;

/**
 * Created by Administrator on 11/7/2016.
 */
public class ModuleListener {

    private final Logger LOG = LoggerFactory.getLogger(ModuleListener.class);

    private final DataSource ds;
    private final CommandRegistry registry;

    public ModuleListener(DataSource ds, CommandRegistry registry) {
        this.ds = ds;
        this.registry = registry;
    }

    @EventSubscriber
    public void onRegister(ModuleEnabledEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }

    @EventSubscriber
    public void onDeregister(ModuleDisabledEvent e) {
        LOG.info("Event triggered: {}", e.getClass().getName());
    }
}
