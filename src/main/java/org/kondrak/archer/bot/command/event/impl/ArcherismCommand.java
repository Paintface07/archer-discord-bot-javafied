package org.kondrak.archer.bot.command.event.impl;

import org.kondrak.archer.bot.command.event.AbstractMessageCommand;
import org.kondrak.archer.bot.context.ArcherBotContext;
import org.kondrak.archer.bot.dao.ArcherismDao;
import org.kondrak.archer.bot.model.Archerism;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.List;

/**
 * Created by Administrator on 11/5/2016.
 */
public class ArcherismCommand extends AbstractMessageCommand {
    private ArcherismDao archerismDao;
    private final List<Archerism> sayings;

    public ArcherismCommand(ArcherBotContext ctx, String command) {
        super(ctx, command);
        this.archerismDao = new ArcherismDao(ctx.getDatasource());
        this.sayings = archerismDao.getArcherisms();
    }

    @Override
    public IMessage execute(IMessage input) {
        int rand = (int) (Math.random() * sayings.size());
        Archerism randMessage = sayings.get(rand);
        try {
            input.reply(randMessage.getText());
            // IMessage m = new MessageBuilder(this.buildClient()).withContent(randMessage).build();
        } catch(MissingPermissionsException | RateLimitException | DiscordException ex) {
            System.out.println("Could not reply to message: " + input.getChannel().getName());
        }
        return null;
    }
}
