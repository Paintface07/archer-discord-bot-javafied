package org.kondrak.archer.bot.command.event.impl;

import com.google.common.collect.Lists;
import org.kondrak.archer.bot.command.CommandRegistry;
import org.kondrak.archer.bot.command.event.AbstractMessageCommand;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.List;

/**
 * Created by Administrator on 11/5/2016.
 */
public class ArcherismCommand extends AbstractMessageCommand {

    public ArcherismCommand(IDiscordClient client, CommandRegistry registry, String command) {
        super(client, registry, command);
    }

    @Override
    public IMessage execute(IMessage input) {
        int rand = (int) (Math.random() * SAYINGS.size());
        String randMessage = SAYINGS.get(rand);
        try {
            input.reply(randMessage);
            // IMessage m = new MessageBuilder(this.getClient()).withContent(randMessage).build();
        } catch(MissingPermissionsException | RateLimitException | DiscordException ex) {
            System.out.println("Could not reply to message: " + input.getChannel().getName());
        }
        return null;
    }

    private static List<String> SAYINGS = Lists.newArrayList(
            "DANGER ZONE!",
            "Danger zone.",
            "*whispers* Danger zone. *whispers*",
            "LAAAANNNNAAAAAAAAAAAAAAAAAAAA!!!!!!",
            "I swear to god I had something for this!",
            "Phrasing.",
            "Er, phrasing.",
            "Phrasing?",
            "Boop.",
            "Read a book.",
            "RAAAMPAAAAAAAAAAAAGE!!!!!!",
            "Just the tip.",
            "Do you not?",
            "Oh my god, is that Burt Reynolds?!",
            "Can't? Or won't?",
            "I've never seen an oscelot!",
            "Holy shit you guys, look at his little spots!",
            "Look at his tufted ears!",
            "No, Cyril.  When they're dead, they're just hookers!",
            "Just one.  I'm scared if I quit drinking all at once, the cumulative hangover will literally kill me.",
            "For I am a sinner in the hands of an angry god.  Bloody Mary, full of vodka, blessed are you among cocktails.  Pray for me now, and at the hour of my death, which I hope is soon.  Amen.",
            "I can't hear you over the sound of my giant, throbbing erection!",
            "You killed a black astronaut, Cyril!  That's like killing a unicorn!",
            "Sorry, that's just a sympathy boner.",
            "Hello, airplanes?  It's blimps.  You win.  Bye.",
            "It's like Meow-schwitz in there.",
            "Relax, it's North Korea, the nation-state equivalent of the short bus.",
            "Those can't seriously be your only shoes.",
            "Big whoop!  I'm spooning a Barrett 50 cal.  I could kill a building.",
            "I hate surprises.  Except surprise fellatio.  That, I like.",
            "Eat a buffet of dicks.",
            "Thanks, Jungle.  Eat a buffet of dicks.",
            "Hm?  Sorry, I was picturing whore island.",
            "Call Kenny Loggins 'cause you're in the danger zone.",
            "I'm not slurring my words, I'm talking in cursive.",
            "All I've had today is like six gummy bears, and some scotch.",
            "Hooray for small miracles.",
            "M, as in Mancy.",
            "Mawp.",
            "Mawp!  Mawp!  Mawp!",
            "MAWP!  MAWP!  MAWP!  Mawp! Mawp.",
            "It's just like the gypsy woman said!",
            "I am everybody's type.",
            "Your authority is not recognized in Fort Kickass.",
            "I call it the Sterling Archer Triple-A Power Play, and yes, the A stands for awesome.",
            "I have no response to that.",
            "Hey, I know you're upset, but if you ever mention my mother's loins or their frothiness to me again, I don't know what I'll do ... but it will be bad. Now let's go bury this dead hooker.",
            "He thinks he's people!",
            "You know, if there's one thing women totally love, it's to be smothered by men.  Or choked, in your case.",
            "I'm getting my turtleneck.  I'm not defusing a bomb in this!",
            "Lying is like 95% of what I do.",
            "Karate? The Dane Cook of martial arts? No, ISIS agents use Krav Maga.",
            "Hey. Hey, proposition: first person to untie me, guy or gal, I will let him or her give me a handy. Come on, let's share the milk of human kindness!"
    );
}
