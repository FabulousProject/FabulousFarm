package me.alpho320.fabulous.farm.api.insect;

import me.alpho320.fabulous.farm.api.TypedManager;
import org.jetbrains.annotations.Nullable;

public abstract class InsectManager extends TypedManager<String, Insect> {

    @Override
    public @Nullable Insect find(String id) {
        return map().getOrDefault(id, null);
    }

}