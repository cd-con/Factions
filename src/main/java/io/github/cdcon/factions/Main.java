package io.github.cdcon.factions;

import io.github.cdcon.factions.faction.FactionCommandHandler;
import io.github.cdcon.factions.npcs.CitizenCommandHandler;
import io.github.cdcon.factions.utility.ConfigInterface;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.*;

public final class Main extends JavaPlugin {

    public static Main pluginInstance;
    private static Economy econ = null;

    ConfigInterface configInterface = new ConfigInterface();

    @Override
    public void onEnable() {
        pluginInstance = this;
        configInterface.defaultConfig();

        this.getCommand("faction").setExecutor(new FactionCommandHandler());
        this.getCommand("citizen").setExecutor(new CitizenCommandHandler());

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }


    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;

    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if (event.getPlayer().getFirstPlayed() == 0){
            event.getPlayer().sendMessage("Для вступления в фракцию используй /factions join");
        }
    }
    @Override
    public void onDisable() {
    }

    public static Main getPlugin() {
        return pluginInstance;
    }
}
