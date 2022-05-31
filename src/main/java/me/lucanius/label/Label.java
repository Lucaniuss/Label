package me.lucanius.label;

import lombok.Getter;
import me.lucanius.label.adapter.NametagAdapter;
import me.lucanius.label.service.NametagService;
import me.lucanius.label.service.impl.StandardNametagService;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

/**
 * @author Lucanius
 * @since May 31, 2022.
 * Label - All Rights Reserved.
 */
@Getter
public class Label {

    public static Label INSTANCE;
    public static int COUNTER = 0;

    private final Plugin plugin;
    private final NametagAdapter adapter;
    private final NametagService service;

    public Label(Plugin plugin, NametagAdapter adapter) {
        INSTANCE = this;

        this.plugin = plugin;
        this.adapter = adapter;
        this.service = new StandardNametagService(adapter);
    }

    public Collection<? extends Player> getOnline() {
        return plugin.getServer().getOnlinePlayers();
    }
}
