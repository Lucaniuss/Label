package me.lucanius.label.data;

import lombok.Data;
import me.lucanius.label.Label;
import me.lucanius.label.tools.Reflections;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;

/**
 * @author Lucanius
 * @since May 31, 2022.
 * Label - All Rights Reserved.
 */
@Data
public class NametagData {

    private final String name;
    private final String prefix;
    private final String suffix;
    private final int i;
    private final PacketPlayOutScoreboardTeam packet;

    public NametagData(String name, String prefix, String suffix) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
        this.i = Label.COUNTER++;
        this.packet = new PacketPlayOutScoreboardTeam();

        String teamName = "§8§" + i + name;
        if (teamName.length() > 16) {
            teamName = teamName.substring(0, 16);
        }

        Reflections.setAndGet(packet, "a", teamName);
        Reflections.setAndGet(packet, "h", 0);
        Reflections.setAndGet(packet, "b", teamName);
        Reflections.setAndGet(packet, "c", prefix);
        Reflections.setAndGet(packet, "d", suffix);
        Reflections.setAndGet(packet, "e", "always");
        Reflections.setAndGet(packet, "i", 3);
    }
}
