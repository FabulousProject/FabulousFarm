package me.alpho320.fabulous.farm.api.scarecrow;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.TypedManager;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class ScarecrowManager extends TypedManager<String, Scarecrow> {

    public @Nullable Scarecrow find(@NotNull String id) {
        return map().getOrDefault(id, null);
    }

}