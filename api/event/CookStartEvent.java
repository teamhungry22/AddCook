package io.hungry22.addcook.api.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import io.hungry22.addcook.api.object.AddCookEntity;

public class CookStartEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final AddCookEntity addCookEntity;
    private boolean isCancelled;

    public CookStartEvent(Player player, AddCookEntity addCookEntity) {
        this.player = player;
        this.addCookEntity = addCookEntity;
        this.isCancelled = false;
    }

    public Player getPlayer() {
        return player;
    }

    public AddCookEntity getAddCookEntity() {
        return addCookEntity;
    }

    public Location getLocation() {
        return addCookEntity.getLocation();
    }

    public String getFurniture() {
        return addCookEntity.getFurniture();
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