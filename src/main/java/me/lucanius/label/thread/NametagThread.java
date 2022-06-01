package me.lucanius.label.thread;

import me.lucanius.label.Label;
import me.lucanius.label.adapter.NametagAdapter;
import me.lucanius.label.cache.NametagCache;
import me.lucanius.label.data.NametagData;
import me.lucanius.label.service.NametagService;
import me.lucanius.label.tools.Reference;
import me.lucanius.label.tools.Voluntary;
import org.apache.commons.lang3.StringEscapeUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

/**
 * @author Lucanius
 * @since June 01, 2022.
 * Label - All Rights Reserved.
 */
public class NametagThread extends Thread {

    private final long sleepTime;
    private final Label label;
    private final NametagService service;
    private final NametagAdapter adapter;
    private final Map<UUID, String> cached;

    public NametagThread(NametagService service) {
        this.sleepTime = 20L * 50L;
        this.label = Label.getInstance();
        this.service = service;
        this.adapter = label.getAdapter();
        this.cached = new HashMap<>();

        start();
    }

    @Override
    public void run() {
        while (true) {
            Collection<? extends Player> all = label.getOnline();
            try {
                all.forEach(online -> refresh(all, online));
                sleep(sleepTime);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void refresh(Collection<? extends Player> all, Player player) {
        NametagCache cache = service.getCache().get(player.getUniqueId());
        if (cache == null) {
            return;
        }

        Reference<NametagData> reference = new Reference<>();
        Reference<Integer> priority = new Reference<>(0);

        all.forEach(online -> {
            List<NametagData> onlineData = adapter.getData(online);
            if (onlineData == null || onlineData.isEmpty()) {
                return;
            }

            onlineData.stream().filter(d -> d.getPriority() > priority.get()).forEach(d -> priority.set(d.getPriority()));
            reference.set(onlineData.stream().filter(d -> d.getPriority() == priority.get()).findFirst().orElse(null));

            NametagData data = reference.get();
            if (data == null) {
                return;
            }

            String name = data.getName();
            UUID onlineId = online.getUniqueId();
            String onlineName = online.getName();
            Scoreboard scoreboard = cache.getScoreboard();

            if (cached.containsKey(onlineId)) {
                Voluntary.ofNull(scoreboard.getTeam(cached.get(onlineId)))
                        .filter(Objects::nonNull)
                        .filter(team -> name != null && !team.getName().equalsIgnoreCase(name))
                        .ifPresent(team -> team.removeEntry(onlineName));
            }

            if (name != null) {
                Team team = scoreboard.getTeam(name);
                if (team == null) {
                    team = scoreboard.registerNewTeam(name);
                }

                team.setPrefix(data.getPrefix());
                team.setSuffix(data.getSuffix());

                if (!team.hasEntry(onlineName)) {
                    team.addEntry(onlineName);
                }

                cached.put(onlineId, name);
            }

            Objective objective = scoreboard.getObjective(DisplaySlot.BELOW_NAME);
            if (data.isShowHealth()) {
                if (objective == null) {
                    objective = scoreboard.registerNewObjective("showhealth", "health");
                }

                objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
                objective.setDisplayName(ChatColor.DARK_RED + StringEscapeUtils.unescapeJava("\u2764"));
                objective.getScore(onlineName).setScore((int) Math.floor(online.getHealth()));
            } else if (objective != null) {
                objective.unregister();
            }

            player.setScoreboard(scoreboard);
        });
    }
}
