package net.simoncameron.synix2.scoreboard;

import me.qiooip.lazarus.Lazarus;
import net.simoncameron.synix2.SynixPlugin;
import net.simoncameron.synix2.managers.ConfigManager;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardManager {

    private SynixPlugin plugin;
    private ConfigManager configManager;
    private ScoreboardObject normalScoreboard;
    private ScoreboardObject cooldownScoreboard;
    private ScoreboardManager instance;

    public ScoreboardManager(SynixPlugin plugin) {
        instance = this;
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    public void load() {
        normalScoreboard = new ScoreboardObject();
        normalScoreboard.setLines(plugin.getConfigManager().getConfig().getStringList("default-scoreboard"));
        new BukkitRunnable() {
            public void run() {
                Lazarus.getInstance().getScoreboardManager().setUpdater(new SynixScoreboardUpdater(instance));
            }
        }.runTaskLater(plugin, 40);

    }

    public ScoreboardObject getNormalScoreboard() {
        return normalScoreboard;
    }

}
