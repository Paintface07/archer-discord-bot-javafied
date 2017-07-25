package org.kondrak.archer.bot.configuration;

public enum ConfigCommand {
    ADD("add"), REMOVE("remove"), SHOW("show");

    private String value;

    ConfigCommand(String command) {
        this.value = command;
    }

    @Override
    public String toString() {
        return value;
    }
}
