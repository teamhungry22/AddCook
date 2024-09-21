package org.hungry22.addCook;

import org.bukkit.plugin.java.JavaPlugin;
import org.hungry22.addCook.command.BaseCommand;
import org.hungry22.addCook.config.ConfigManager;
import org.hungry22.addCook.event.MechanicLoadEvent;
import org.hungry22.addCook.event.PlaceEvent;
import org.hungry22.addCook.event.ReloadEvent;

public final class AddCook extends JavaPlugin {

    private static ConfigManager configManager;
    @Override
    public void onEnable() {
        getLogger().info("AddCook 활성화");
        getConfigManager();
        getCommand("addcook").setExecutor(new BaseCommand());
        getServer().getPluginManager().registerEvents(new ReloadEvent(), this);
        getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new MechanicLoadEvent(), this);
    }
    @Override
    public void onDisable() {
        getLogger().info("AddCook 비활성화");
    }

    public static AddCook getInstance() {
        return getPlugin(AddCook.class);
    }

    public static ConfigManager getConfigManager(){
        if(configManager == null)
            configManager = new ConfigManager();
        return configManager;
    }
}
