package me.alpho320.fabulous.farm.api.pot;

import com.google.common.collect.ImmutableList;
import me.alpho320.fabulous.farm.api.TypedManager;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import me.alpho320.fabulous.farm.hook.Hook;
import me.alpho320.fabulous.farm.hook.type.CanChangePotModel;
import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PotManager extends TypedManager<String, Pot> {

    private final @NotNull Map<Location, PotHolder> POT_HOLDERS = new ConcurrentHashMap<>();
    private final @NotNull Map<World, List<PotHolder>> POT_HOLDERS_WORLD_MAP = new ConcurrentHashMap<>();

    @Override
    public @Nullable Pot find(String id) {
        return map().getOrDefault(id, null);
    }

    public void updatePotModel(@NotNull PotHolder pot, @NotNull String model) {
        boolean changed = false;
        for (Hook hook : plugin().hookManager().hooks()) {
            if (!hook.getClass().isAssignableFrom(CanChangePotModel.class)) continue;

            CanChangePotModel canChangePotModel = (CanChangePotModel) hook;
            if (canChangePotModel.changePotModel(pot, model)) {
                changed = true;
                break;
            }
        }
        if (changed) return;

        Material type = Material.matchMaterial(model);
        if (type != null) {
            pot.location().loc().getBlock().setType(type);
        } else {
            plugin().logger().severe(" | Pot model of '" + model + "' not found!");
        }
    }

    public @Nullable PotHolder findPotHolder(@NotNull CropHolder cropHolder) {
        Location location = cropHolder.location().loc().clone().subtract(0, 1, 0); // pot always under the crop.
        return plugin().farmManager().potManager().findHolder(location);
    }

    public @Nullable PotHolder findHolder(@NotNull Location location) {
        return POT_HOLDERS.getOrDefault(location, null);
    }

    public @Nullable PotHolder findHolder(@NotNull SerializableLocation location) {
        return POT_HOLDERS.getOrDefault(location.loc(), null);
    }

    public @NotNull ImmutableList<PotHolder> potHolders() {
        return ImmutableList.copyOf(POT_HOLDERS.values());
    }

    public @NotNull ImmutableList<PotHolder> potHoldersFromWorld(World world) {
        return ImmutableList.copyOf(POT_HOLDERS_WORLD_MAP.getOrDefault(world, ImmutableList.of()));
    }

    public void registerHolder(@NotNull Location location, @NotNull PotHolder potHolder) {
        POT_HOLDERS.put(location, potHolder);
        POT_HOLDERS_WORLD_MAP.computeIfAbsent(location.getWorld(), world -> new ArrayList<>()).add(potHolder);
    }

    public void unregisterHolder(@NotNull Location location) {
        POT_HOLDERS.remove(location);
        POT_HOLDERS_WORLD_MAP.computeIfAbsent(location.getWorld(), world -> new ArrayList<>())
                .removeIf(potHolder -> potHolder.location().loc().equals(location));
    }

}