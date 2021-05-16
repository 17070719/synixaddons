package net.simoncameron.synix2.listeners;

import me.qiooip.lazarus.factions.Faction;
import me.qiooip.lazarus.factions.claim.ClaimManager;
import me.qiooip.lazarus.factions.type.ConquestFaction;
import me.qiooip.lazarus.factions.type.DtcFaction;
import me.qiooip.lazarus.factions.type.KothFaction;
import me.qiooip.lazarus.factions.type.MountainFaction;
import net.simoncameron.synix2.SynixPlugin;
import net.simoncameron.synix2.managers.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerDeathListener implements Listener {

    private final SynixPlugin plugin;
    private final ConfigManager configManager;

    public PlayerDeathListener(SynixPlugin plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(PlayerDeathEvent event) {
        Faction landOwner = ClaimManager.getInstance().getFactionAt(event.getEntity().getLocation());
        if (landOwner instanceof ConquestFaction
        || landOwner instanceof DtcFaction
        || landOwner instanceof KothFaction
        || landOwner instanceof MountainFaction) {
            event.setDeathMessage(this.configManager.getMessages().getString("koth-death-message"));
        }
    }

}
