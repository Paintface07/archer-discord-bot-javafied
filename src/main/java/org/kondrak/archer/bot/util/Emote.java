package org.kondrak.archer.bot.util;

/**
 * Created by nosferatu on 7/12/17.
 */
public enum Emote {
    THUMBS_UP(":thumbsup:"),
    THUMBS_DOWN(":thumbsdown:"),
    FLAG_US(":flag_us:"),
    ALARM_CLOCK(":alarm_clock:");

    private final String emoteVal;

    Emote(final String emoteString) {
        this.emoteVal = emoteString;
    }

    @Override
    public String toString() {
        return emoteVal;
    }
}
