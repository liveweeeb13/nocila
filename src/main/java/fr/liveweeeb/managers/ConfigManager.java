package fr.liveweeeb.managers;

import fr.liveweeeb.Nocila;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigManager {

    private final Nocila main;
    private static final Map<String, Object> configCache = new HashMap<>();; //Evite de I/O h24

    private String prefix;

    public ConfigManager(Nocila main) {
        this.main = main;
    }

    public void createConfigIfNotExists() {
        File pluginFolder = main.getDataFolder();
        File configFile = new File(pluginFolder, "config.yml");

        if (!pluginFolder.exists()) {
            if (pluginFolder.mkdirs()) {
                main.getLogger().info("Nocila file successfully created");
            } else {
                main.getLogger().warning("Unable to create Nocila folder");
            }
        }

        if (!configFile.exists()) {
            try {
                if (configFile.createNewFile()) {
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
                    main.getLogger().info("config.yml file successfully created with default values");
                }
            } catch (IOException e) {
                main.getLogger().severe("Error creating config.yml file: " + e.getMessage());
            }
        }
    }

    public void loadConfig() {
        Set<String> keys = main.getConfig().getKeys(true); // ← true = récursif
        for (String key : keys) {
            Object value = main.getConfig().get(key);
            configCache.put(key, value);
            if (key.equals("prefix")) prefix = String.valueOf(value);
        }
    }

    public static Object getConfigValue(String key, Object defaultValue) {
        return configCache.getOrDefault(key, defaultValue);
    }

    public void reloadPluginConfig() {
        main.reloadConfig();
        configCache.clear();
        loadConfig();
    }

    public String getPrefix() { return prefix; }
}