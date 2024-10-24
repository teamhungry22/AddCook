package io.hungry22.addcook.api.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import io.hungry22.addcook.AddCook;
import io.hungry22.addcook.api.object.AddCookItem;
import io.hungry22.addcook.api.object.line.LineCommand;
import io.hungry22.addcook.api.object.line.LineDrop;
import io.hungry22.addcook.api.object.line.LineExp;
import io.hungry22.addcook.api.object.line.LineSound;
import io.hungry22.addcook.api.object.AddCookEntity;
import io.hungry22.addcook.api.object.AddCookRecipe;
import io.hungry22.addcook.core.config.ConfigManager;
import io.hungry22.addcook.core.util.MathUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class AddCookUtils {
    private static final AddCook plugin = AddCook.getInstance();
    private static final ConfigManager configManager = AddCook.getConfigManager();

    public static boolean isAddCookEntity(Entity entity) {
        return AddCookEntity.load(entity).isPresent();
    }

    public static boolean isAddCookEntity(UUID uuid) {
        Entity entity = Bukkit.getEntity(uuid);
        if (entity == null) {
            return false;
        }
        return isAddCookEntity(entity);
    }

    public static AddCookEntity getAddCookEntity(Entity entity) {
        return AddCookEntity.load(entity).orElse(null);
    }

    public static Optional<AddCookEntity> getOptAddCookEntity(Entity entity) {
        return AddCookEntity.load(entity);
    }

    public static Optional<AddCookEntity> getOptAddCookEntity(UUID uuid) {
        Entity entity = Bukkit.getEntity(uuid);
        if (entity == null) {
            return Optional.empty();
        }
        return getOptAddCookEntity(entity);
    }

    public static AddCookRecipe getAddCookRecipe(String name) {
        return configManager.getRecipeMap().get(name);
    }

    public static boolean isAddCookItem(ItemStack itemStack) {
        return AddCookItem.load(itemStack).isPresent();
    }

    public static boolean isAddCookItem(String addCookItemStringId) {
        return AddCookItem.load(addCookItemStringId).isPresent();
    }

    public static AddCookItem getAddCookItem(ItemStack itemStack) {
        return AddCookItem.load(itemStack).orElse(null);
    }

    public static AddCookItem getAddCookItem(String addCookItemStringId) {
        return AddCookItem.load(addCookItemStringId).orElse(null);
    }

    public static Optional<AddCookItem> getOptAddCookItem(ItemStack itemStack) {
        return AddCookItem.load(itemStack);
    }

    public static Optional<AddCookItem> getOptAddCookItem(String addCookItemStringId) {
        return AddCookItem.load(addCookItemStringId);
    }

    public static String getFurnitureTag(Entity entity) {
        Set<String> tags = entity.getScoreboardTags();
        if (tags.contains("addcook_stove")) {
            return "stove";
        } else if (tags.contains("addcook_frypan")) {
            return "frypan";
        } else if (tags.contains("addcook_pot")) {
            return "pot";
        } else if (tags.contains("addcook_fryer")) {
            return "fryer";
        } else if (tags.contains("addcook_chopping_board")) {
            return "chopping_board";
        }
        return null;
    }

    public static List<AddCookRecipe> getAddCookRecipeList(String furniture) {
        return switch (furniture) {
            case "frypan" -> configManager.getRecipeFrypanList();
            case "pot" -> configManager.getRecipePotList();
            case "fryer" -> configManager.getRecipeFryerList();
            case "chopping_board" -> configManager.getRecipeBoardList();
            default -> null;
        };
    }

    public static LineCommand getLineCommand(String line, boolean isPlayerCommand) {
        String[] lineArray = getLineArray(line);
        if (lineArray == null) {
            return null;
        }

        String command = "tellraw @s \"{\"text\":\"올바르지 않은 커맨드 작동 - 운영자에게 문의\"}\"";
        double chance = 100;

        if (lineArray.length >= 1) {
            command = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
        }

        try {
            if (lineArray.length >= 2 && line.lastIndexOf("]") + 2 < line.length()) {
                chance = Double.parseDouble(line.substring(line.lastIndexOf("]") + 2));
            }
        } catch (NumberFormatException ignored) {}

        return new LineCommand(line, command, chance, isPlayerCommand);
    }

    public static LineDrop getLineDrop(String line) {
        String[] lineArray = getLineArray(line);
        if (lineArray == null) {
            return null;
        }

        String value = lineArray[0];
        int min;
        int max;
        double chance = 100;
        int resultAmount = 1;
        try {
            if (lineArray.length >= 2) {
                String[] amountRange = lineArray[1].split("-");
                min = Integer.parseInt(amountRange[0]);
                max = amountRange.length > 1 ? Integer.parseInt(amountRange[1]) : min;
                resultAmount = ThreadLocalRandom.current().nextInt(min, max + 1);
            }

            if (lineArray.length >= 3) {
                chance = Double.parseDouble(lineArray[2]);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return new LineDrop(line, value, resultAmount, chance);
    }

    public static LineExp getLineExp(String line) {
        String[] lineArray = getLineArray(line);
        if (lineArray == null) {
            return null;
        }

        int resultAmount = 1;
        double chance = 100;

        try {
            if (lineArray.length >= 1) {
                String[] amountRange = lineArray[0].replaceFirst("exp:", "").split("-");
                int minAmount = Integer.parseInt(amountRange[0]);
                int maxAmount = amountRange.length > 1 ? Integer.parseInt(amountRange[1]) : minAmount;
                resultAmount = ThreadLocalRandom.current().nextInt(minAmount, maxAmount + 1);
            }

            if (lineArray.length >= 2) {
                chance = Double.parseDouble(lineArray[1]);
            }

        } catch (NumberFormatException e) {
            return null;
        }
        return new LineExp(line, resultAmount, chance);
    }

    public static LineSound getLineSound(String line) {
        String[] lineArray = getLineArray(line);
        if (lineArray == null) {
            return null;
        }

        String value = lineArray[0];
        float resultVolume = 1;
        float resultPitch = 1;
        double chance = 100;

        try {
            if (lineArray.length >= 2) {
                String[] volumeRange = lineArray[1].split("-");
                float minVolume = Float.parseFloat(volumeRange[0]);
                float maxVolume = volumeRange.length > 1 ? Float.parseFloat(volumeRange[1]) : minVolume;
                resultVolume = (minVolume == maxVolume) ? minVolume : ThreadLocalRandom.current().nextFloat(minVolume, maxVolume);
            }

            if (lineArray.length >= 3) {
                String[] pitchRange = lineArray[2].split("-");
                float minPitch = Float.parseFloat(pitchRange[0]);
                float maxPitch = pitchRange.length > 1 ? Float.parseFloat(pitchRange[1]) : minPitch;
                resultPitch = (minPitch == maxPitch) ? minPitch : ThreadLocalRandom.current().nextFloat(minPitch, maxPitch);
            }

            if (lineArray.length >= 4) {
                chance = Double.parseDouble(lineArray[3]);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return new LineSound(line, value, Math.round(resultVolume * 10) / 10.0f, Math.round(resultPitch * 10) / 10.0f, chance);
    }

    public static Object getLine(Player player, String line) {
        String lineType = line.split(":", 2)[0];
        return switch (lineType) {
            case "sound" -> AddCookUtils.getLineSound(line);
            case "exp" -> AddCookUtils.getLineExp(line);
            case "pcmd" -> AddCookUtils.getLineCommand(line.replace("{player}", player.getName()), true);
            case "cmd" -> AddCookUtils.getLineCommand(line.replace("{player}", player.getName()), false);
            default -> getLineDrop(line);
        };
    }

    public static void executeLine(Player player, Location location, String line, boolean isForce) {
        Object lineObj = getLine(player, line);
        if (lineObj == null) {
            plugin.getLogger().warning("Line 값 오류: " + line + "에서 발생!");
            return;
        }

        if (!isForce && !MathUtils.getChance(getLineChance(lineObj))) {
            return;
        }

        switch (lineObj) {
            case LineCommand lineCommand -> executeCommand(player, lineCommand);
            case LineDrop lineDrop -> dropItem(player, location, lineDrop);
            case LineExp lineExp -> player.giveExp(lineExp.getAmount());
            case LineSound lineSound -> playSound(player, location, lineSound);
            default -> plugin.getLogger().warning("알 수 없는 Line 타입: " + lineObj.getClass().getName());
        }
    }

    public static void executeLine(Player player, Location location, List<String> lineList) {
        List<Double> lineChanceList = new ArrayList<>();
        for (String line : lineList) {
            Object lineObj = getLine(player, line);
            lineChanceList.add(getLineChance(lineObj));
        }
        Integer resultIndex = MathUtils.getIndexWeight(lineChanceList);
        executeLine(player, location, lineList.get(resultIndex), true);
    }

    private static void executeCommand(Player player, LineCommand lineCommand) {
        String command = lineCommand.getCommand();
        if (lineCommand.isPlayerCommand()) {
            player.performCommand(command);
        } else {
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Bukkit.dispatchCommand(console, command);
        }
    }

    public static void dropItem(Player player, Location location, ItemStack itemStack) {
        String dropType = configManager.getCookDropType();
        itemStack.setAmount(1);
        handleDropItem(player, location, dropType, itemStack);
    }

    public static void dropItem(Player player, Location location, LineDrop lineDrop) {
        String dropType = configManager.getCookDropType();
        handleDropItem(player, location, dropType, lineDrop.getItem());
    }

    public static void handleDropItem(Player player, Location location, String dropType, ItemStack itemStack) {
        UUID uuid = player.getWorld().getUID();
        if (dropType.equalsIgnoreCase("give")) {
            if (player.getInventory().firstEmpty() != -1) {
                player.getInventory().addItem(itemStack);
            } else {
                Bukkit.getWorld(uuid).dropItem(location, itemStack);
            }
        } else if (dropType.equalsIgnoreCase("drop")) {
            Bukkit.getWorld(uuid).dropItem(location, itemStack);
        }
    }

    public static void playSound(Player player, Location location, String line) {
        LineSound lineSound = getLineSound(line);
        if (lineSound == null) {
            return;
        }
        playSound(player, location, lineSound);
    }

    public static void playSound(Player player, Location location, List<String> lineList) {
        if (lineList.isEmpty()) {
            return;
        }
        for (String line : lineList) {
            LineSound lineSound = getLineSound(line);
            if (lineSound == null) {
                continue;
            }
            playSound(player, location, lineSound);
        }
    }

    public static void playSound(Player player, Location location, LineSound lineSound) {
        String sound = lineSound.getSound();
        if (sound.equalsIgnoreCase("none") || sound.isEmpty()) {
            return;
        }

        float volume = lineSound.getVolume();
        float pitch = lineSound.getPitch();
        try {
            Sound.valueOf(sound.toUpperCase().replaceAll("\\.","_"));
        } catch (Exception ex) {
            player.playSound(location, sound.replaceFirst("\\.", ":"), volume, pitch);
            return;
        }
        player.playSound(location, sound, volume, pitch);
    }

    private static String[] getLineArray(String line) {
        if (line == null) {
            return null;
        }
        String[] lineArray = line.trim().split(" ");
        if (lineArray.length == 0) {
            return null;
        }
        return lineArray;
    }

    private static double getLineChance(Object lineObj) {
        return switch (lineObj) {
            case LineCommand lineCommand -> lineCommand.getChance();
            case LineDrop lineDrop -> lineDrop.getChance();
            case LineExp lineExp -> lineExp.getChance();
            case LineSound lineSound -> lineSound.getChance();
            default -> 0;
        };
    }
}
