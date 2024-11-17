package com.github.teamhungry22.addcook.api.line;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface LineManager {
    public List<Line> lineSound(Player player, String result);
    public List<Line> lineSound(Player player, List<String> resultList);
    public List<Line> lineExp(Player player, String result);
    public List<Line> lineExp(Player player, List<String> resultList);
    public List<Line> lineCommand(Player player, String result);
    public List<Line> lineCommand(Player player, List<String> resultList);
    public List<Line> lineDrop(Player player, String result);
    public List<Line> lineDrop(Player player, List<String> resultList);
    public List<Line> line(Player player, String result);
    public List<Line> line(Player player, List<String> resultList);
    public List<Line> line(Player player, String result, @Nullable LineType type);
    public List<Line> line(Player player, List<String> resultList, @Nullable LineType type);
}
