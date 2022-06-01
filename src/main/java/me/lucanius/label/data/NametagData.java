package me.lucanius.label.data;

import lombok.Data;
import me.lucanius.label.tools.CC;

/**
 * @author Lucanius
 * @since May 31, 2022.
 * Label - All Rights Reserved.
 */
@Data
public class NametagData {

    private final String name;
    private final int priority;
    private final String prefix;
    private final String suffix;
    private final boolean showHealth;

    public NametagData(String name, int priority, String prefix, String suffix, boolean showHealth) {
        this.name = name;
        this.priority = priority;
        this.prefix = CC.translate(prefix);
        this.suffix = CC.translate(suffix);
        this.showHealth = showHealth;
    }
}
