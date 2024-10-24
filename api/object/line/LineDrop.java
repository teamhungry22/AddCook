package io.hungry22.addcook.api.object.line;

import org.bukkit.inventory.ItemStack;
import io.hungry22.addcook.core.util.ItemUtils;

public class LineDrop {
    private String line;
    private String itemName;
    private int amount;
    private double chance;
    private ItemStack item;

    public LineDrop(String line, String itemName, int amount, double chance) {
        this.line = line;
        this.itemName = itemName;
        this.amount = amount;
        this.chance = chance;

        ItemStack itemStack = ItemUtils.getCustomItem(this.itemName);
        itemStack.setAmount(this.amount);
        this.item = itemStack;
    }

    public String getLine() {
        return line;
    }
    public void setLine(String line) {
        this.line = line;
    }

    public String getItemName() {
        return itemName;
    }
    public void getItemName(String itemName) {
        this.itemName = itemName;
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

    public ItemStack getItem() {
        return item;
    }
}