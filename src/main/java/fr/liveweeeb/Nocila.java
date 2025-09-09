package fr.liveweeeb;

import org.bukkit.plugin.java.JavaPlugin;
import fr.liveweeeb.managers.PluginManager;
import fr.liveweeeb.listeners.PlayerListener;

// Commandes
import fr.liveweeeb.commands.aboutCommand;
import fr.liveweeeb.commands.broadcastCommand;
import fr.liveweeeb.commands.dayCommand;
import fr.liveweeeb.commands.nightCommand;
import fr.liveweeeb.commands.repairCommand;
import fr.liveweeeb.commands.renameCommand;
import fr.liveweeeb.commands.loreCommand;
import fr.liveweeeb.commands.smiteCommand;
import fr.liveweeeb.commands.craftCommand;



// JSP
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class Nocila extends JavaPlugin {
    
    private String prefix;
    
    @Override
    public void onEnable() {
        createConfigIfNotExists();
        reloadConfig();
        
        // Récupérer le prefix
        prefix = getConfig().getString("prefix", "§3§l[§9§lNocila§3§l]§r");
    
        // Initialize managers
        PluginManager.getInstance().initialize();
        
        // Register listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        getLogger().info("Nocila has been successfully enabled! l38");


        // Load les commandes
        getCommand("about").setExecutor(new aboutCommand(this));
        getCommand("broadcast").setExecutor(new broadcastCommand(this));
        // // // // //
        getCommand("day").setExecutor(new dayCommand(this));
        getCommand("night").setExecutor(new nightCommand(this));
        // // // // //
        getCommand("rename").setExecutor(new renameCommand(this));
        getCommand("lore").setExecutor(new loreCommand(this));
        getCommand("repair").setExecutor(new repairCommand(this));
        getCommand("smite").setExecutor(new smiteCommand(this));
        getCommand("craft").setExecutor(new craftCommand(this));
    }

    @Override
    public void onDisable() {
       getLogger().info("Nocila has been disabled! l50");
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    // Méthode pour recharger la configuration SERT A R 
    public void reloadPluginConfig() {
        reloadConfig();
        prefix = getConfig().getString("prefix", "§3§l[§9§lNocila§3§l]§r");
    }
    

    private void createConfigIfNotExists() {
        File pluginFolder = getDataFolder();
        File configFile = new File(pluginFolder, "config.yml");
        
        if (!pluginFolder.exists()) {
            if (pluginFolder.mkdirs()) {
                getLogger().info("Nocila file successfully created");
            } else {
                getLogger().warning("Unable to create Nocila folder");
            }
        }


        
        if (!configFile.exists()) {
            try {
                if (configFile.createNewFile()) {
                    // Écrire le contenu par défaut dans le fichier config
                    String defaultConfig = 
                     "# This is the Nocila configuration file\n" + 
                     "prefix: \"§3§l[§9§lNocila§3§l]§r\"\n ";

                    Files.write(configFile.toPath(), defaultConfig.getBytes(), StandardOpenOption.WRITE);
                    getLogger().info("config.yml file successfully created with default values");
                }
            } catch (IOException e) {
                getLogger().severe("Error creating config.yml file: " + e.getMessage());
            }
        }
    }
}