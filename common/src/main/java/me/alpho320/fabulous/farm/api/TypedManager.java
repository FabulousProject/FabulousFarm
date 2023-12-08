package me.alpho320.fabulous.farm.api;

import me.alpho320.fabulous.farm.FarmPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class TypedManager<KEY, OBJECT> extends Manager {

    private final @NotNull Map<KEY, OBJECT> map = new HashMap<>();
    private boolean enabled = true;

    public abstract void init(@NotNull FarmPlugin plugin);

    public abstract @Nullable OBJECT find(KEY id);
    public abstract @Nullable OBJECT fromSection(String id, ConfigurationSection section);

    public void register(@NotNull KEY key, @NotNull OBJECT object) {
        this.map.put(key, object);
    }

    public void unregister(@NotNull KEY key) {
        this.map.remove(key);
    }

    public @NotNull Map<KEY, OBJECT> map() {
        return this.map;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}