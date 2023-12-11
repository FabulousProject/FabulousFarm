package me.alpho320.fabulous.farm.api.wateringcan;

import me.alpho320.fabulous.farm.api.TypedManager;
import org.jetbrains.annotations.Nullable;

public abstract class WateringCanManager extends TypedManager<String, WateringCan> {

    @Override
    public @Nullable WateringCan find(String id) {
        return map().getOrDefault(id, null);
    }

}