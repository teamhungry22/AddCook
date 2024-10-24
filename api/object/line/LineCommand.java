package io.hungry22.addcook.api.object.line;

public class LineCommand {
    private String line;
    private String command;
    private double chance;
    private boolean isPlayerCommand;

    public LineCommand(String line, String command, double chance, boolean isPlayerCommand) {
        this.line = line;
        this.command = command;
        this.chance = chance;
        this.isPlayerCommand = isPlayerCommand;
    }

    public String getLine() {
        return line;
    }
    public void setLine(String line) {
        this.line = line;
    }

    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }

    public double getChance() {
        return chance;
    }
    public void setChance(double chance) {
        this.chance = chance;
    }

    public boolean isPlayerCommand() {
        return isPlayerCommand;
    }
    public void setPlayerCommand(boolean playerCommand) {
        isPlayerCommand = playerCommand;
    }
}