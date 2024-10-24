package io.hungry22.addcook.api.event;

import io.hungry22.addcook.api.object.AddCookEntity;
import io.hungry22.addcook.api.object.AddCookItem;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RecipeUseEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final AddCookItem addCookItem;
    private boolean isCancelled;

    public RecipeUseEvent(Player player, AddCookItem addCookItem) {
        this.player = player;
        this.addCookItem = addCookItem;
        this.isCancelled = false;
    }

    public Player getPlayer() {
        return player;
    }

    public AddCookItem getAddCookItem() {
        return addCookItem;
    }

    public String getRecipeId() {
        return addCookItem.getId();
    }

    public ItemStack getItemStack() {
        return addCookItem.getItemStack();
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
