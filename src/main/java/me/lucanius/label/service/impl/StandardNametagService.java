package me.lucanius.label.service.impl;

import me.lucanius.label.Label;
import me.lucanius.label.adapter.NametagAdapter;
import me.lucanius.label.data.NametagData;
import me.lucanius.label.service.NametagService;
import me.lucanius.label.tools.Condition;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Lucanius
 * @since May 31, 2022.
 * Label - All Rights Reserved.
 */
public class StandardNametagService implements NametagService {

    private final Label label;
    private final NametagAdapter adapter;
    private final ExecutorService thread;
    private final List<NametagData> data;

    public StandardNametagService(NametagAdapter adapter) {
        this.label = Label.INSTANCE;
        this.adapter = adapter;
        this.thread = Executors.newSingleThreadExecutor();
        this.data = adapter.data();
    }

    @Override
    public void join(Player player) {
        data.forEach(data -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(data.getPacket()));
        refresh(player, true);
    }

    @Override
    public void refresh(Player player, boolean async) {
        thread.execute(() -> label.getOnline().stream().filter(online -> online != player).forEach(online -> refresh(player, online, async)));
    }

    @Override
    public void refresh(Player first, Player second, boolean async) {
        Condition.of(async, () -> thread.execute(() -> refresh(first, second))).orElse(() -> refresh(first, second));
    }

    @Override
    public void refresh(Player first, Player second) {
        adapter.update(first, second);
    }

    @Override
    public NametagAdapter getAdapter() {
        return adapter;
    }

    @Override
    public ExecutorService getExecutor() {
        return thread;
    }

    @Override
    public List<NametagData> getRegistered() {
        return data;
    }
}
