package me.alpho320.fabulous.farm.api.world;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class FarmWorldManager {
    
    private final @NotNull Map<String, FarmWorld> map = new ConcurrentHashMap<>();

    public abstract void setup();
    public abstract void checkAllWorlds();

    public @Nullable FarmWorld find(@NotNull String id) {
        return this.map.get(id);
    }
    
    public void register(@NotNull String id, @NotNull FarmWorld farmWorld) {
        this.map.put(id, farmWorld);
    }
    
    public void unregister(@NotNull String id) {
        this.map.remove(id);
    }
    
    public @NotNull Map<String, FarmWorld> map() {
        return this.map;
    }
    
}