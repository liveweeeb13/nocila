package fr.liveweeeb;

import org.bukkit.plugin.java.JavaPlugin;
import fr.liveweeeb.managers.PluginManager;
import fr.liveweeeb.utils.UpdateChecker;
import fr.liveweeeb.listeners.PlayerListener;

// Commandes
import fr.liveweeeb.commands.aboutCommand;
import fr.liveweeeb.commands.broadcastCommand;
import fr.liveweeeb.commands.dayCommand;
import fr.liveweeeb.commands.delfireCommand;
import fr.liveweeeb.commands.nightCommand;
import fr.liveweeeb.commands.nocilaCommand;
import fr.liveweeeb.commands.repairCommand;
import fr.liveweeeb.commands.renameCommand;
import fr.liveweeeb.commands.loreCommand;
import fr.liveweeeb.commands.masssummonCommand;
import fr.liveweeeb.commands.smiteCommand;
import fr.liveweeeb.commands.craftCommand;
import fr.liveweeeb.commands.hatCommand;
import fr.liveweeeb.commands.killallCommand;

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
        return prefix;
    }

    public boolean isUpdateEnabled() {
        return getConfig().getBoolean("update", true);
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
                    String defaultConfig = "# This is the Nocila configuration file\n" +
                            "# For information go to https://modrinth.com/plugin/nocila\n" +
                            "prefix: \"§3§l[§9§lNocila§3§l]§r\"\n\n" +
                            "update: true\n\n" +
                            "# Fire command settings\n" +
                            "fire:\n" +
                            "  default-radius: 10 # Using a radius larger than 200 may crash your server.\n" +
                            "  max-radius: 50     # Using a radius larger than 200 may crash your server.\n\n" +
                            "masssummon:\n" +
                            "  max-amount: 50\n\n" +
                            "killall:\n" +
                            "   nokill:\n" +
                            "         - CAT     # It won't kill the   CAT  when the /killall command will be executed\n"+
                            "         - AXOLOT  # It won't kill the AXOLOT when the /killall command will be executed\n" + 
                            "         - WOLF    # It won't kill the  WOLF  when the /killall command will be executed\n\n" +
                            "# Thanks for using Nocila\n";

                    Files.write(configFile.toPath(), defaultConfig.getBytes(), StandardOpenOption.WRITE);
                    getLogger().info("config.yml file successfully created with default values");
                }
            } catch (IOException e) {
                getLogger().severe("Error creating config.yml file: " + e.getMessage());
            }
        }
    }
}