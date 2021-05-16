package net.simoncameron.synix2.tasks;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;
import net.simoncameron.synix2.SynixPlugin;
import net.simoncameron.synix2.managers.BlockCacheManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class WildernessResetTask extends BukkitRunnable {

    private SynixPlugin plugin;
    private BlockCacheManager manager;

    public WildernessResetTask(SynixPlugin plugin) {
        this.plugin = plugin;
        this.manager = plugin.getBlockCacheManager();
    }

    public void run() {
        for (Map.Entry<Location, Material> entry : manager.getApplicableBlocks().entrySet()) {
            entry.getKey().getBlock().setType(entry.getValue());
            plugin.getServer().broadcastMessage((plugin.getConfigManager().getMessages().getString("wilderness-reset")));
        }
    }

}
