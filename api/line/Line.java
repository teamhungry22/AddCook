package com.github.teamhungry22.addcook.api.line;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface Line {
    String getLine();
    Double getChance();
    LineType getType();
    LineResult getResult();
    void setResult(LineResult lineResult);
    void execute(Player player, Location location);
}
