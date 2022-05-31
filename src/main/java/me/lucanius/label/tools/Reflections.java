package me.lucanius.label.tools;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

/**
 * @author Lucanius
 * @since May 30, 2022.
 * Edge - All Rights Reserved.
 */
@UtilityClass
public class Reflections {

    @SneakyThrows
    public Field setAndGet(Object from, String name, Object to) {
        Field field = from.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(from, to);

        return field;
    }

    @SneakyThrows
    public Field setAndGet(Class<?> from, String name, Object to) {
        Field field = from.getDeclaredField(name);
        field.setAccessible(true);
        field.set(from, to);

        return field;
    }

    @SneakyThrows
    public Object getAccessible(Object from, String name) {
        Field field = from.getClass().getDeclaredField(name);
        field.setAccessible(true);

        return field;
    }

    @SneakyThrows
    public Object getAccessible(Class<?> from, String name) {
        Field field = from.getDeclaredField(name);
        field.setAccessible(true);

        return field;
    }
}
