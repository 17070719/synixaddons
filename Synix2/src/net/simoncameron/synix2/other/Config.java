package net.simoncameron.synix2.other;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.simoncameron.synix2.other.Chat.translate;

public class Config extends YamlConfiguration {

    private final File file;

    public Config(File file) {
        this.file = file;
    }

    public static Config loadConfiguration(File file) {
        Config config = new Config(file);
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return config;
    }

    public String getString(String path) {
        return translate(super.getString(path));
    }

    public List<String> getStringList(String path) {
        return super.getStringList(path).stream().map(Chat::translate).collect(Collectors.toList());
    }

    public String getUntranslatedString(String path) {
        return super.getString(path);
    }

    public List<String> getUntranslatedStringList(String path) {
        return super.getStringList(path);
    }

    public void save() {
        try {
            super.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while saving configuration '" + getName() + "'.");
        }
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return super.getConfigurationSection(path);
    }
}
