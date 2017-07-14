package org.kondrak.archer.bot.command.event.impl;

import org.kondrak.archer.bot.command.event.AbstractMessageCommand;
import org.kondrak.archer.bot.context.ArcherBotContext;
import org.kondrak.archer.bot.dao.ArcherismDao;
import org.kondrak.archer.bot.model.Archerism;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.List;

/**
 * Created by Administrator on 11/5/2016.
 */
public class ArcherismCommand extends AbstractMessageCommand {

    private static final Logger LOG = LoggerFactory.getLogger(ArcherismCommand.class);

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
        } catch(MissingPermissionsException | RateLimitException | DiscordException ex) {
            LOG.error("Could not reply to message: {}", input.getChannel().getName());
            ex.printStackTrace();
        }
        return null;
    }
}
