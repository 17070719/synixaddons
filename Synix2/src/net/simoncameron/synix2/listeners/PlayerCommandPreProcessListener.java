package net.simoncameron.synix2.listeners;

import me.qiooip.lazarus.Lazarus;
import me.qiooip.lazarus.games.conquest.ConquestManager;
import net.simoncameron.synix2.SynixPlugin;
import net.simoncameron.synix2.managers.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Locale;

public class PlayerCommandPreProcessListener implements Listener {

    private final SynixPlugin plugin;
    private final ConfigManager configManager;

    public PlayerCommandPreProcessListener(SynixPlugin plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(PlayerCommandPreprocessEvent event) {
        boolean block = false;
        String blockedCommand = "";
        for (String string : configManager.getConfig().getStringList("event-blacklisted-commands")) {
            if (event.getMessage().toLowerCase().startsWith(string.toLowerCase())) {
                block = true;
                blockedCommand = string.toLowerCase();
            }
        }
        if (!block)
            return;
        if (Lazarus.getInstance().getConquestManager().isActive()
        || Lazarus.getInstance().getEnderDragonManager().isActive()
        || Lazarus.getInstance().getDtcManager().isActive()
        || !Lazarus.getInstance().getKothManager().getRunningKoths().isEmpty()
        || Lazarus.getInstance().getKillTheKingManager().isActive()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(
                    configManager.getMessages().getString("command-disabled-during-event")
                            .replace("%command%", blockedCommand));
        }
    }

}
