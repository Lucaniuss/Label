package me.lucanius.label;

import lombok.Getter;
import me.lucanius.label.adapter.NametagAdapter;
import me.lucanius.label.service.NametagService;
import me.lucanius.label.service.impl.StandardNametagService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

/**
 * @author Lucanius
 * @since May 31, 2022.
 * Label - All Rights Reserved.
 */
@Getter
public class Label implements Listener {

    @Getter private static Label instance;

    private final Plugin plugin;
    private final NametagAdapter adapter;
    private final NametagService service;

    public Label(Plugin plugin, NametagAdapter adapter) {
        instance = this;

        this.plugin = plugin;
        this.adapter = adapter;
        this.service = new StandardNametagService();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Collection<? extends Player> getOnline() {
        return plugin.getServer().getOnlinePlayers();
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        service.join(event);
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        service.quit(event);
    }
}
