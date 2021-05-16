package net.simoncameron.synix2.managers;

import net.simoncameron.synix2.SynixPlugin;
import net.simoncameron.synix2.other.Config;

import java.io.File;

public class ConfigManager {

    private SynixPlugin plugin;
    private Config messages;
    private Config config;

    public ConfigManager(SynixPlugin plugin) {
        this.plugin = plugin;
    }

    public void reload() {
        saveResources();
        messages = Config.loadConfiguration(new File(plugin.getDataFolder(), "messages.yml"));
        config = Config.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
    }

    private void saveResources() {
        if (!(new File(plugin.getDataFolder(), "messages.yml")).exists())
            plugin.saveResource("messages.yml", true);
        if (!(new File(plugin.getDataFolder(), "config.yml")).exists())
            plugin.saveResource("config.yml", true);
    }

    public Config getMessages() {
        return messages;
    }

    public Config getConfig() {
        return config;
    }

}
