package fr.slophil.hammercraft;

import fr.slophil.hammercraft.commands.HammerCraftCommand;
import fr.slophil.hammercraft.listeners.HammerBlockBreakEvent;
import fr.slophil.hammercraft.utils.HammersConstructor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class HammerCraft extends JavaPlugin {

    private static HammerCraft instance;

    public static HammerCraft getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new HammerBlockBreakEvent(this), this);

        HammersConstructor.registerRecipes();

        this.getCommand("hammercraft").setExecutor(new HammerCraftCommand(this));

        getLogger().info("HammerCraft is enabled !");
    }

    @Override
    public void onDisable() {
        getLogger().info("HammerCraft is disabled !");
    }
}
