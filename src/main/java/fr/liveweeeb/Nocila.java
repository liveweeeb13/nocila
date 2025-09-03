package fr.liveweeeb;

import org.bukkit.plugin.java.JavaPlugin;
import fr.liveweeeb.managers.PluginManager;
import fr.liveweeeb.listeners.PlayerListener;

// Commandes
import fr.liveweeeb.commands.aboutCommand;
import fr.liveweeeb.commands.broadcastCommand;


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

        getLogger().info("§aNocila a été activé avec succès!");


        // Load les commandes
        getCommand("about").setExecutor(new aboutCommand(this));
        getCommand("broadcast").setExecutor(new broadcastCommand(this));
    }

    @Override
    public void onDisable() {
       getLogger().info("§cNocila a été désactivé!");
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
                getLogger().info("§aDossier Nocila créé avec succès");
            } else {
                getLogger().warning("§6Impossible de créer le dossier Nocila");
            }
        }


        
        if (!configFile.exists()) {
            try {
                if (configFile.createNewFile()) {
                    // Écrire le contenu par défaut dans le fichier config
                    String defaultConfig = 
                     "# Ceci est le fichier de configuration de Nocila\n" + 
                     "prefix: \"§3§l[§9§lNocila§3§l]§r\"\n ";

                    Files.write(configFile.toPath(), defaultConfig.getBytes(), StandardOpenOption.WRITE);
                    getLogger().info("§aFichier config.yml créé avec succès avec les valeurs par défaut");
                }
            } catch (IOException e) {
                getLogger().severe("§cErreur lors de la création du fichier config.yml: " + e.getMessage());
            }
        }
    }
}