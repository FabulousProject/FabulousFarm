package me.alpho320.fabulous.farm.hook;

import me.alpho320.fabulous.farm.Callback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class HookManager {

    private final @NotNull Map<String, Hook> map = new HashMap<>();

    public abstract void init(@Nullable Callback callback);

    public void register(@NotNull String id, @NotNull Hook hook) {
        map.put(id, hook);
    }

    public void register(@NotNull Hook hook) {
        map.put(hook.name(), hook);
    }

    public @Nullable Hook find(@NotNull String id) {
        return map.getOrDefault(id, null);
    }

    public @NotNull Map<String, Hook> map() {
        return this.map;
    }

}