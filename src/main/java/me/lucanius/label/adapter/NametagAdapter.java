package me.lucanius.label.adapter;

import me.lucanius.label.data.NametagData;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author Lucanius
 * @since May 31, 2022.
 * Label - All Rights Reserved.
 */
public interface NametagAdapter {

    List<NametagData> getData(Player player);

}
