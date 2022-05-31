package me.lucanius.label.service;

import me.lucanius.label.Label;
import me.lucanius.label.adapter.NametagAdapter;
import me.lucanius.label.data.NametagData;
import me.lucanius.label.tools.Reflections;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author Lucanius
 * @since May 31, 2022.
 * Label - All Rights Reserved.
 */
public interface NametagService {

    @SuppressWarnings("unchecked")
    static void update(Player first, Player second, String name) {
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        String teamName = "§8§" + Label.COUNTER++ + name;
        if (teamName.length() > 16) {
            teamName = teamName.substring(0, 16);
        }

        Reflections.setAndGet(packet, "a", teamName);
        Reflections.setAndGet(packet, "h", 3);
        ((Collection<String>) Reflections.getAccessible(packet, "e")).add(second.getName());

        ((CraftPlayer) first).getHandle().playerConnection.sendPacket(packet);
    }

    void join(Player player);

    void refresh(Player player, boolean async);

    void refresh(Player first, Player second, boolean async);

    void refresh(Player first, Player second);

    NametagAdapter getAdapter();

    ExecutorService getExecutor();

    List<NametagData> getRegistered();

}
