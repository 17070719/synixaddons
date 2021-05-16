package net.simoncameron.synix2.scoreboard;

import me.clip.placeholderapi.PlaceholderAPI;
import net.simoncameron.synix2.other.Chat;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardObject {

    private List<String> lines;
    private String title;

    public ScoreboardObject() {
        this.lines = new ArrayList<>();
    }

    public void setLines(List<String> strings) {
        this.lines = strings;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLines(Player player) {
        return lines.stream().map(string -> Chat.translate(PlaceholderAPI.setPlaceholders(player, string))).collect(Collectors.toList());
    }

    public String getTitle(Player player) {
        return Chat.translate(PlaceholderAPI.setPlaceholders(player, title));
    }

}
