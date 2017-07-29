package org.kondrak.archer.bot.core.listener;

import org.kondrak.archer.bot.core.ContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.module.ModuleDisabledEvent;
import sx.blah.discord.handle.impl.events.module.ModuleEnabledEvent;

/**
 * Created by Administrator on 11/7/2016.
 */
public class ModuleListener {

    private static final Logger LOG = LoggerFactory.getLogger(ModuleListener.class);

    @EventSubscriber
    public void onRegister(ModuleEnabledEvent e) {
        LOG.info(ContextBuilder.EVENT_LOGGER_FORMAT, e.getClass().getName());
    }

    @EventSubscriber
    public void onDeregister(ModuleDisabledEvent e) {
        LOG.info(ContextBuilder.EVENT_LOGGER_FORMAT, e.getClass().getName());
    }
}
