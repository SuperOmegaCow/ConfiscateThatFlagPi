package com.ctf.util;

public enum BootstrapCommands {

    UPDATE("ctf -update"),
    RESTART("ctf -restart"),
    SHUTDOWN("ctf -shutdown");

    private String command;

    private BootstrapCommands(String args) {
        this.command = args;
    }

    public String getCommand() {
        return this.command;
    }

    public static BootstrapCommands fromValue(String args) {
        for(BootstrapCommands commands : values()) {
            if(commands.getCommand().equalsIgnoreCase(args))
                return commands;
        }
        return null;
    }

}
