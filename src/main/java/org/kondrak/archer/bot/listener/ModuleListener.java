package org.kondrak.archer.bot.listener;

import org.kondrak.archer.bot.command.CommandRegistry;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ModuleDisabledEvent;
import sx.blah.discord.handle.impl.events.ModuleEnabledEvent;

import javax.sql.DataSource;

/**
 * Created by Administrator on 11/7/2016.
 */
public class ModuleListener {
    private final DataSource ds;
    private final CommandRegistry registry;

    public ModuleListener(DataSource ds, CommandRegistry registry) {
        this.ds = ds;
        this.registry = registry;
    }

    @EventSubscriber
    public void onRegister(ModuleEnabledEvent e) {
        System.out.println(e.getClass().getName());
    }

    @EventSubscriber
    public void onDeregister(ModuleDisabledEvent e) {
        System.out.println(e.getClass().getName());
    }
}
