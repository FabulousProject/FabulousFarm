package me.alpho320.fabulous.farm.api.greenhouse;

import me.alpho320.fabulous.farm.api.TypedManager;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import me.alpho320.fabulous.farm.api.pot.PotHolder;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class GreenhouseManager extends TypedManager<String, Greenhouse> {

    private final @NotNull Map<Location, GreenhouseHolder> GREENHOUSE_HOLDERS = new ConcurrentHashMap<>();
    private final int MAX_GREENHOUSE_HEIGHT = 30;

    @Override
    public @Nullable Greenhouse find(String id) {
        return map().getOrDefault(id, null);
    }



    public int maxGreenhouseHeight() {
        return this.MAX_GREENHOUSE_HEIGHT;
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
            if (plugin().farmManager().potManager().findHolder(checkLocation) != null) {
                return checkLocation.equals(location); // a greenhouse can effect only one pot.
            }
        }

        return false;
    }

    /**
     *
     * @param greenhouseHolder greenhouse to check can this greenhouse effect.
     * @param cropHolder crop to check can this greenhouse effect.
     * @return true if can effect, otherwise false. (pot always under the crop)
     */
    public boolean canEffect(@NotNull GreenhouseHolder greenhouseHolder, @NotNull CropHolder cropHolder) {
        return canEffect(greenhouseHolder, cropHolder.location().loc().subtract(0, 1, 0));
    }

    public boolean canEffect(@NotNull GreenhouseHolder greenhouseHolder, @NotNull PotHolder potHolder) {
        return canEffect(greenhouseHolder, potHolder.location().loc());
    }

    public @Nullable GreenhouseHolder findHolder(@NotNull Location location) {
        return GREENHOUSE_HOLDERS.getOrDefault(location, null);
    }

    public @Nullable GreenhouseHolder findHolder(@NotNull CropHolder cropHolder) {
        Location location = cropHolder.location().loc();

        for (int i = 1; i < maxGreenhouseHeight(); i++) {
            Location checkLocation = location.clone().add(0, i, 0);
            GreenhouseHolder greenhouseHolder = findHolder(checkLocation);
            if (greenhouseHolder != null)
                return greenhouseHolder;
        }

        return null;
    }

    public Map<Location, GreenhouseHolder> greenhouseHolders() {
        return this.GREENHOUSE_HOLDERS;
    }

}