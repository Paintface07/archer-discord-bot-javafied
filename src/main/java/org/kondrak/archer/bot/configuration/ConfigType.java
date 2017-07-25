package org.kondrak.archer.bot.configuration;

public enum ConfigType {
    ARCHERISM_COMMAND("ARCHERISM_COMMAND"),
    DICE_COMMAND("DICE_COMMAND"),
    TIMER_COMMAND("TIMER_COMMAND"),
    WORD_COMMAND("WORD_COMMAND");

    String value;

    ConfigType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
