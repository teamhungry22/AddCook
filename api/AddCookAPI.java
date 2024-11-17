package com.github.teamhungry22.addcook.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import com.github.teamhungry22.addcook.AddCook;
import com.github.teamhungry22.addcook.core.item.AddCookItem;
import com.github.teamhungry22.addcook.core.entity.AddCookEntity;
import com.github.teamhungry22.addcook.core.config.ConfigManager;

import java.util.*;

public class AddCookAPI {
    private static final ConfigManager configManager = AddCook.getInstance().getConfigManager();

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
}
