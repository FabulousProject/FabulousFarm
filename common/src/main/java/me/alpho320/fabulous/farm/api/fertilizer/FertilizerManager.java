package me.alpho320.fabulous.farm.api.fertilizer;

import me.alpho320.fabulous.farm.api.TypedManager;
import org.jetbrains.annotations.Nullable;

public abstract class FertilizerManager extends TypedManager<String, Fertilizer> {

    @Override
    public @Nullable Fertilizer find(String id) {
        return map().getOrDefault(id, null);
    }

}