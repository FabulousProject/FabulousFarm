package me.alpho320.fabulous.farm.api.vermin;

import me.alpho320.fabulous.farm.api.TypedManager;
import org.jetbrains.annotations.Nullable;

public abstract class VerminManager extends TypedManager<String, Vermin> {

    @Override
    public @Nullable Vermin find(String id) {
        return map().getOrDefault(id, null);
    }

}