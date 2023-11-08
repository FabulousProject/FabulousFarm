package me.alpho320.fabulous.farm.api.greenhouse;

import me.alpho320.fabulous.farm.api.TypedManager;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class GreenhouseManager extends TypedManager<String, Greenhouse> {

    @Override
    public @Nullable Greenhouse find(String id) {
        return map().getOrDefault(id, null);
    }

    /**
     * @param greenhouseHolder greenhouse to check can this greenhouse effect.
     * @param location block(pot) location to check can this greenhouse effect.
     * @return true if can effect, otherwise false.
     */
    public boolean canEffect(@NotNull GreenhouseHolder greenhouseHolder, @NotNull Location location) {
        if (!location.getWorld().getName().equals(greenhouseHolder.location().world())) return false;
        if (location.getBlockY() > greenhouseHolder.location().y()) return false;

        final Location greenHouseLocation = greenhouseHolder.location().loc();
        for (int i = 1; i < greenhouseHolder.greenhouse().height(); i++) {
            Location checkLocation = greenHouseLocation.clone().subtract(0, i, 0);
            if (plugin().farmManager().potManager().findHolder(checkLocation) != null)
                return true;
        }

        return false;
    }


}