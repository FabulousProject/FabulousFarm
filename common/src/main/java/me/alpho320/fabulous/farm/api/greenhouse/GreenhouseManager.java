package me.alpho320.fabulous.farm.api.greenhouse;

import me.alpho320.fabulous.farm.api.TypedManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class GreenhouseManager extends TypedManager<String, Greenhouse> {

    @Override
    public @Nullable Greenhouse find(String id) {
        return map().getOrDefault(id, null);
    }

    public boolean canEffect(@NotNull Greenhouse greenhouse, @NotNull Location location) {
        Block block = location.getWorld().getHighestBlockAt(location);



        return location.getBlockY() >= greenhouse.height();
    }

}