package me.alpho320.fabulous.farm.api.crow;

import me.alpho320.fabulous.farm.api.TypedManager;
import org.jetbrains.annotations.Nullable;

public abstract class CrowManager extends TypedManager<String, Crow> {

    @Override
    public @Nullable Crow find(String id) {
        return map().getOrDefault(id, null);
    }

}