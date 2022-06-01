package me.lucanius.label.service.impl;

import me.lucanius.label.cache.NametagCache;
import me.lucanius.label.service.NametagService;
import me.lucanius.label.thread.NametagThread;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lucanius
 * @since May 31, 2022.
 * Label - All Rights Reserved.
 */
public class StandardNametagService implements NametagService {

    private final Map<UUID, NametagCache> cache;
    private final NametagThread thread;

    public StandardNametagService() {
        this.cache = new ConcurrentHashMap<>();
        this.thread = new NametagThread(this);
    }

    @Override
    public void destroy() {
        if (thread.isAlive()) {
            thread.stop();
        }

        cache.values().forEach(NametagCache::destroy);
    }

    @Override
    public void join(PlayerJoinEvent event) {
        cache.put(event.getPlayer().getUniqueId(), new NametagCache(event.getPlayer()));
    }

    @Override
    public void quit(PlayerQuitEvent event) {
        cache.remove(event.getPlayer().getUniqueId());
    }

    @Override
    public NametagThread getThread() {
        return thread;
    }

    @Override
    public Map<UUID, NametagCache> getCache() {
        return cache;
    }
}
