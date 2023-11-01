package me.alpho320.fabulous.farm.data;

import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class IntData {

    private final @NotNull Object object;

    public IntData(@NotNull Object object) {
        this.object = object;
    }
    
    public int toInt() {
        if (object instanceof String) {
            String string = (String) object;
            if (string.contains("-")) {
                try {
                    String[] split = string.split("-");
                    return ThreadLocalRandom.current().nextInt(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                } catch (NumberFormatException e) {
                    Debug.debug(1, object + " is not a number. Using default value(1800).");
                    return 1800;
                }
            } else {
                try {
                    return Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    Debug.debug(1, object + " is not a number. Using default value(1800).");
                    return 1800;
                }
            }
        } else if (object instanceof Integer) {
            return (int) object;
        }

        Debug.debug(1, object + " is not valid for time. Using default value(1800)");
        return 1800;
    }

    public static @NotNull IntData of(@NotNull Object object) {
        return new IntData(object);
    }

    public static @NotNull IntData of(@NotNull ConfigurationSection section) {
        return of(section, "amount");
    }

    public static @NotNull IntData of(@NotNull ConfigurationSection section, String key) {
        IntData data = new IntData(1);
        if (section.isSet(key)) {
            if (section.isString(key))
                data = new IntData(section.getString(key));
            else
                data = new IntData(section.getInt(key));
        }
        return data;
    }

    
}