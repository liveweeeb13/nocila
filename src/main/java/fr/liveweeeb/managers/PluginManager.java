package fr.liveweeeb.managers;

import fr.liveweeeb.Nocila;

public class PluginManager {
    private static PluginManager instance;

    //Manager
    private ConfigManager configManager;
    
    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }
    
    public void initialize(Nocila main) {
        configManager = new ConfigManager(main);
        configManager.createConfigIfNotExists();
        configManager.loadConfig();
    }

    public ConfigManager getConfigManager() { return configManager; }
}