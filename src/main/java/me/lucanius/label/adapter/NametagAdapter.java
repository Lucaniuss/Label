package me.lucanius.label.adapter;

import me.lucanius.label.data.NametagData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucanius
 * @since May 31, 2022.
 * Label - All Rights Reserved.
 */
public interface NametagAdapter {

    void update(Player first, Player second);

    default List<NametagData> data() {
        return new ArrayList<>();
    }
}
