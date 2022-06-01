package me.lucanius.label.service;

import me.lucanius.label.cache.NametagCache;
import me.lucanius.label.thread.NametagThread;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;

/**
 * @author Lucanius
 * @since May 31, 2022.
 * Label - All Rights Reserved.
 */
public interface NametagService {

    void destroy();

    void join(PlayerJoinEvent event);

    void quit(PlayerQuitEvent event);

    NametagThread getThread();

    Map<UUID, NametagCache> getCache();

}
