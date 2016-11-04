package org.kondrak.archer.bot;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;

/**
 * Created by Administrator on 11/3/2016.
 */
public class MessageListener {

    @EventSubscriber
    public void onReadyEvent(ReadyEvent e) {
        System.out.println("READY");
    }
}
