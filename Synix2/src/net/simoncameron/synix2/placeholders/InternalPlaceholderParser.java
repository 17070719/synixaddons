package net.simoncameron.synix2.placeholders;

import me.clip.placeholderapi.PlaceholderAPI;
import me.qiooip.lazarus.Lazarus;
import me.qiooip.lazarus.games.conquest.ZoneType;
import me.qiooip.lazarus.userdata.Userdata;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class InternalPlaceholderParser {

    private static Map<String, Replacer> registeredPlaceholders;

    public static String parsePlaceholders(String text, Player player) {
        String toReturn = PlaceholderAPI.setPlaceholders(player, text);
        Userdata data = Lazarus.getInstance().getUserdataManager().getUserdata(player);
        for (Map.Entry<String, Replacer> entry : registeredPlaceholders.entrySet())
            toReturn = toReturn.replace(entry.getKey(), entry.getValue().replace(data));
        return toReturn;
    }

    public static void loadPlaceholders() {
        registeredPlaceholders = new HashMap<>();
        register("%data_kills%", data -> String.valueOf(data.getKills()));
        register("%data_deaths%", data -> String.valueOf(data.getDeaths()));
        register("%data_balance%", data -> String.valueOf(data.getBalance()));
        register("%data_killstreak%", data -> String.valueOf(data.getKillstreak()));
        register("%reboot_placeholder%", data -> Lazarus.getInstance().getRebootHandler().getScoreboardEntry());
        register("%conquest_red%", data -> Lazarus.getInstance().getConquestManager().getRunningConquest().getTimeEntry(ZoneType.RED));
        register("%conquest_blue%", data -> Lazarus.getInstance().getConquestManager().getRunningConquest().getTimeEntry(ZoneType.BLUE));
        register("%conquest_green%", data -> Lazarus.getInstance().getConquestManager().getRunningConquest().getTimeEntry(ZoneType.GREEN));
        register("%conquest_yellow%", data -> Lazarus.getInstance().getConquestManager().getRunningConquest().getTimeEntry(ZoneType.YELLOW));
        register("%koth_king%", data -> Lazarus.getInstance().getKillTheKingManager().getKingName());
        register("%koth_time_lasted%", data -> Lazarus.getInstance().getKillTheKingManager().getTimeLasted());
        register("%koth_world%", data -> Lazarus.getInstance().getKillTheKingManager().getKingWorld());
        register("%koth_location%", data -> Lazarus.getInstance().getKillTheKingManager().getKingLocation());
        register("%dtc_breaks_left%", data -> String.valueOf(Lazarus.getInstance().getDtcManager().getBreaksLeft()));
        register("%ender_dragon%", data -> Lazarus.getInstance().getEnderDragonManager().getScoreboardEntry());
    }

    public static void register(String placeholder, Replacer replacer) {
        registeredPlaceholders.put(placeholder, replacer);
    }

}
