package net.simoncameron.synix2.listeners;

import net.simoncameron.synix2.SynixPlugin;
import net.simoncameron.synix2.managers.BlockCacheManager;
import net.simoncameron.synix2.managers.ConfigManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    private SynixPlugin plugin;
    private ConfigManager configManager;
    private BlockCacheManager blockCacheManager;

    public BlockPlaceListener(SynixPlugin plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.blockCacheManager = plugin.getBlockCacheManager();
    }

    @EventHandler
    public void event(BlockPlaceEvent event) {
        if (!configManager.getConfig().getStringList("block-caching-worlds").contains(
                event.getBlock().getWorld().getName()))
            return;
        if (blockCacheManager.isAlreadyCached(event.getBlock().getLocation()))
            return;
        blockCacheManager.cacheBlock(event.getBlockPlaced().getLocation(), Material.AIR);
    }

}
