package net.simoncameron.synix2;

import net.simoncameron.synix2.listeners.BlockBreakListener;
import net.simoncameron.synix2.listeners.BlockPlaceListener;
import net.simoncameron.synix2.listeners.PlayerCommandPreProcessListener;
import net.simoncameron.synix2.listeners.PlayerDeathListener;
import net.simoncameron.synix2.managers.BlockCacheManager;
import net.simoncameron.synix2.managers.ConfigManager;
import net.simoncameron.synix2.placeholders.InternalPlaceholderParser;
import net.simoncameron.synix2.scoreboard.ScoreboardManager;
import net.simoncameron.synix2.tasks.WildernessResetTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SynixPlugin extends JavaPlugin {

    private ConfigManager configManager;
    private BlockCacheManager blockCacheManager;
    private ScoreboardManager scoreboardManager;

    public void onEnable() {
        configManager = new ConfigManager(this);
        configManager.reload();
        blockCacheManager = new BlockCacheManager(this);
        InternalPlaceholderParser.loadPlaceholders();
        scoreboardManager = new ScoreboardManager(this);
        scoreboardManager.load();
        registerListeners();
        registerTasks();
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreProcessListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this), this);
    }

    public void registerTasks() {
        int interval = configManager.getConfig().getInt("wilderness-reset-interval");
        new WildernessResetTask(this).runTaskTimer(this, interval* 20L, interval* 20L);
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public BlockCacheManager getBlockCacheManager() {
        return blockCacheManager;
    }

}
