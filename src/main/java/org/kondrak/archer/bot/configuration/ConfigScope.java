package org.kondrak.archer.bot.configuration;

/**
 * Describes the scope of a generic configuration.
 */
public enum ConfigScope {
    GUILD("GUILD"), CHANNEL("CHANNEL"), USER("USER");

    String value;

    ConfigScope(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
