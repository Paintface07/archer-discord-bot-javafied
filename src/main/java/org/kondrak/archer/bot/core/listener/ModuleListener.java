package org.kondrak.archer.bot.core.listener;

import org.kondrak.archer.bot.core.ArcherBotContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.module.ModuleDisabledEvent;
import sx.blah.discord.handle.impl.events.module.ModuleEnabledEvent;

/**
 * Created by Administrator on 11/7/2016.
 */
public class ModuleListener extends AbstractListener {

    private final Logger LOG = LoggerFactory.getLogger(ModuleListener.class);

    public ModuleListener(ArcherBotContext ctx) {
        super(ctx);
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
