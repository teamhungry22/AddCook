package io.hungry22.addcook.api.event;

import io.hungry22.addcook.AddCook;
import io.hungry22.addcook.api.util.AddCookUtils;
import io.hungry22.addcook.core.util.ItemUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import io.hungry22.addcook.api.object.AddCookEntity;
import org.bukkit.inventory.ItemStack;

public class MaterialAddEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final AddCookEntity addCookEntity;
    private final ItemStack itemStack;
    private boolean isCancelled;

    public MaterialAddEvent(Player player, AddCookEntity addCookEntity, ItemStack itemStack) {
        this.player = player;
        this.addCookEntity = addCookEntity;
        this.itemStack = itemStack;
        this.isCancelled = false;
    }

    public Player getPlayer() {
        return player;
    }

    public AddCookEntity getAddCookEntity() {
        return addCookEntity;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Location getLocation() {
        return addCookEntity.getLocation();
    }

    public String getFurniture() {
        return addCookEntity.getFurniture();
    }

    public int getStorageSize() {
        return addCookEntity.getStorageSize();
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}