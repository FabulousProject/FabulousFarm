package me.alpho320.fabulous.farm.manager;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.Manager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractDataManager<OBJECT_ID, OBJECT> {

    protected final @NotNull FarmPlugin plugin;
    private final @NotNull Map<OBJECT_ID, OBJECT> map = new HashMap<>();

    public AbstractDataManager(@NotNull FarmPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract @NotNull CompletableFuture<Boolean> loadAll(boolean async);
    public abstract @NotNull CompletableFuture<Boolean> unloadAll(boolean async);
    public abstract @NotNull CompletableFuture<Boolean> saveAll(boolean async);

    public abstract @NotNull CompletableFuture<@Nullable OBJECT> load(OBJECT_ID id, boolean async);
    public abstract @Nullable OBJECT load(OBJECT_ID id);
    public abstract @NotNull CompletableFuture<Boolean> unload(OBJECT_ID id, boolean async);
    public abstract @NotNull CompletableFuture<Boolean> save(OBJECT_ID id, boolean async);

    public @Nullable OBJECT find(@NotNull OBJECT_ID id) {
        return map.getOrDefault(id, null);
    }

    public void register(@NotNull OBJECT_ID id, @NotNull OBJECT object) {
        Objects.requireNonNull(id, "id cannot be null!");
        Objects.requireNonNull(object, "object cannot be null!");

        map.put(id, object);
    }

    public void unregister(@NotNull OBJECT_ID id) {
        map.remove(id);
    }

    public @NotNull Map<OBJECT_ID, OBJECT> map() {
        return this.map;
    }

    public void clear() {
        this.map.clear();
    }

    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

}