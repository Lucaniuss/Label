package me.lucanius.label.cache;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.UUID;

/**
 * @author Lucanius
 * @since June 01, 2022.
 * Label - All Rights Reserved.
 */
@Getter
public class NametagCache {

    private final UUID uuid;
    private final Player player;
    private final ScoreboardManager manager;
    private final Scoreboard scoreboard;

    public NametagCache(Player player) {
        this.uuid = player.getUniqueId();
        this.player = player;
        this.manager = Bukkit.getScoreboardManager();

        player.setScoreboard(scoreboard = player.getScoreboard() != manager.getMainScoreboard() ? player.getScoreboard() : manager.getNewScoreboard());
    }

    public void destroy() {
        if (!player.isOnline()) {
            return;
        }

        player.setScoreboard(manager.getNewScoreboard());
    }
}
