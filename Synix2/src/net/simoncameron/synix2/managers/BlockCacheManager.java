package net.simoncameron.synix2.managers;

import me.qiooip.lazarus.factions.claim.ClaimManager;
import me.qiooip.lazarus.factions.type.WarzoneFaction;
import net.simoncameron.synix2.SynixPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class BlockCacheManager {

    private SynixPlugin plugin;
    private Map<Location, Material> blockCache;

    public BlockCacheManager(SynixPlugin plugin) {
        this.plugin = plugin;
        this.blockCache = new HashMap<>();
    }

    public boolean isAlreadyCached(Location location) {
        return blockCache.containsKey(location);
    }

    public void cacheBlock(Location location, Material material) {
        blockCache.put(location, material);
    }

    public Map<Location, Material> getApplicableBlocks() {
        Map<Location, Material> toReturn = new HashMap<>();
        new BukkitRunnable() {
            public void run() {
                for (Location location : blockCache.keySet()) {
                    if (ClaimManager.getInstance().getClaimAt(location) == null || !(ClaimManager.getInstance().getClaimAt(location).getOwner() instanceof WarzoneFaction))
                        continue;
                    toReturn.put(location, blockCache.get(location));
                }
            }
        }.runTaskAsynchronously(plugin);
        return toReturn;
    }

}
