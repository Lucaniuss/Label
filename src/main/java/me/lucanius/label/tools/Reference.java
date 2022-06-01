package me.lucanius.label.tools;

import lombok.NoArgsConstructor;

/**
 * @author Lucanius
 * @since June 01, 2022.
 * Label - All Rights Reserved.
 */
@NoArgsConstructor
public class Reference<V> {

    private V value;

    public Reference(V value) {
        this.value = value;
    }

    public V get() {
        return value;
    }

    public V set(V value) {
        return this.value = value;
    }
}
