package com.github.teamhungry22.addcook.api.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import com.github.teamhungry22.addcook.core.entity.AddCookEntity;
import com.github.teamhungry22.addcook.core.config.RecipeData;
import com.github.teamhungry22.addcook.core.listener.addcook.CookResult;
import org.jetbrains.annotations.Nullable;

public class CookStartSuccessEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final AddCookEntity addCookEntity;
    private final RecipeData recipeData;
    private final CookResult cookResult;
    private boolean isCancelled;

    public CookStartSuccessEvent(Player player, AddCookEntity addCookEntity, @Nullable RecipeData recipeData, CookResult result) {
        this.player = player;
        this.addCookEntity = addCookEntity;
        this.recipeData = recipeData;
        this.cookResult = result;
        this.isCancelled = false;
    }

    public Player getPlayer() {
        return player;
    }

    public AddCookEntity getAddCookEntity() {
        return addCookEntity;
    }

    @Nullable
    public RecipeData getRecipe() {
        return recipeData;
    }

    public CookResult getCookResult() {
        return cookResult;
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