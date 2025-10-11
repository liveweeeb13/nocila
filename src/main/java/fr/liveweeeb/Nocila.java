package fr.liveweeeb;

import fr.liveweeeb.managers.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;
import fr.liveweeeb.managers.PluginManager;
import fr.liveweeeb.utils.UpdateChecker;
import fr.liveweeeb.listeners.PlayerListener;

// Commandes
import fr.liveweeeb.commands.*;

public class Nocila extends JavaPlugin {

    @Override
    public void onEnable() {
        // Initialize managers
        PluginManager.getInstance().initialize(this);

        // Register listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        getLogger().info("Nocila has been successfully enabled!");

        // Load les commandes
        getCommand("about").setExecutor(new aboutCommand(this));
        getCommand("broadcast").setExecutor(new broadcastCommand(this));
        // // // // //
        getCommand("day").setExecutor(new dayCommand(this));
        getCommand("night").setExecutor(new nightCommand(this));
        // // // // // 1.2.0
        getCommand("rename").setExecutor(new renameCommand(this));
        getCommand("lore").setExecutor(new loreCommand(this));
        getCommand("repair").setExecutor(new repairCommand(this));
        getCommand("smite").setExecutor(new smiteCommand(this));
        getCommand("craft").setExecutor(new craftCommand(this));
        // // // // // 1.4.0-BETA-2
        getCommand("hat").setExecutor(new hatCommand(this));
        getCommand("delfire").setExecutor(new delfireCommand(this));

        nocilaCommand nocilaCmd = new nocilaCommand(this);
        getCommand("nocila").setExecutor(nocilaCmd);
        getCommand("nocila").setTabCompleter(nocilaCmd);
        // // // // // 1.4.0
        masssummonCommand masssummonCmd = new masssummonCommand(this);
        getCommand("masssummon").setExecutor(masssummonCmd);
        getCommand("masssummon").setTabCompleter(masssummonCmd);

        getCommand("killall").setExecutor(new killallCommand(this));

        UpdateChecker updater = new UpdateChecker(this, "https://liveweeeb13.github.io/nocila.txt");
        updater.check();

        // Enregistrer le listener pour prévenir les OP qui rejoignent après
        getServer().getPluginManager().registerEvents(updater, this);

    }

    @Override
    public void onDisable() {
        getLogger().info("Nocila has been disabled! ");
    }

    public String getPrefix() {
        return PluginManager.getInstance().getConfigManager().getPrefix();
    }

    public boolean isUpdateEnabled() {
        return (boolean) ConfigManager.getConfigValue("update", true);
    }
}