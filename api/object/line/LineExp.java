package io.hungry22.addcook.api.object.line;

public class LineExp {
    private String line;
    private int amount;
    private double chance;

    public LineExp(String line, int amount, double chance) {
        this.line = line;
        this.amount = amount;
        this.chance = chance;
    }

    public String getLine() {
        return line;
    }
    public void setLine(String line) {
        this.line = line;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getChance() {
        return chance;
    }
    public void setChance(double chance) {
        this.chance = chance;
    }
}