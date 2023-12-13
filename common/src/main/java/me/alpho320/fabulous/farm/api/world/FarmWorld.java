package me.alpho320.fabulous.farm.api.world;

import me.alpho320.fabulous.farm.Callback;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import me.alpho320.fabulous.farm.api.sprinkler.SprinklerHolder;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FarmWorld {

    private final @NotNull FarmPlugin plugin;

    private final @NotNull String id;
    private @NotNull WeakReference<World> world;
    private boolean readyToNextDay = true;

    public FarmWorld(@NotNull FarmPlugin plugin, @NotNull String id, @NotNull WeakReference<World> world, boolean readyToNextDay) {
        this.plugin = plugin;
        this.id = id;
        this.world = world;
        this.readyToNextDay = readyToNextDay;
    }

    public @NotNull String id() {
        return this.id;
    }

    public @Nullable World world() {
        return this.world.get();
    }

    public void checks() {
        checkSprinklers(sprinklerCallback -> {
            checkCrops(cropsCallback -> {
                checkInsects(insectsCallback -> {
                    checkReadyToNextDay();
                });
            });
        });
    }

    public void checkSprinklers(@Nullable Callback callback) {
        List<SprinklerHolder> sprinklers = new ArrayList<>(plugin.farmManager().sprinklerManager().sprinklerHoldersFromWorld(world.get()));
        Collections.shuffle(sprinklers);

        if (sprinklers.isEmpty()) {
            if (callback != null) callback.complete(true);
            return;
        }

        checkSprinkler(sprinklers, sprinklers.get(0), callback);
    }

    public void checkSprinkler(@NotNull List<SprinklerHolder> list, SprinklerHolder sprinkler, @Nullable Callback callback) {
        if (list.isEmpty() || sprinkler == null) {
            if (callback != null) callback.complete(true);
            return;
        }

        if (sprinkler.water() > 0)
            sprinkler.sprinkle(plugin);

        list.remove(0);
        plugin.schedulerManager().runTaskLaterAsync(() -> {
            checkSprinkler(list, list.get(0), callback);
        }, new Random().nextInt(1, 5));
    }


    public void checkCrops(@Nullable Callback callback) {
        List<CropHolder> crops = new ArrayList<>(plugin.farmManager().cropManager().cropHoldersFromWorld(world.get()));
        Collections.shuffle(crops);

        if (crops.isEmpty()) {
            if (callback != null) callback.complete(true);
            return;
        }

        plugin.schedulerManager().runTaskLaterAsync(() -> {
            checkCrop(crops, crops.get(0), callback);
        }, new Random().nextInt(1, 5));
    }

    public void checkCrop(@NotNull List<CropHolder> list, CropHolder crop, @Nullable Callback callback) {
        if (list.isEmpty() || crop == null) {
            if (callback != null) callback.complete(true);
            return;
        }

        crop.grow(plugin, false);

        list.remove(0);
        plugin.schedulerManager().runTaskLaterAsync(() -> {
            checkCrop(list, list.get(0), callback);
        }, new Random().nextInt(1, 5));
    }

    public void setWorld(@NotNull WeakReference<World> world) {
        this.world = world;
    }

    public boolean readyToNextDay() {
        return this.readyToNextDay;
    }

    public void setReadyToNextDay(boolean readyToNextDay) {
        this.readyToNextDay = readyToNextDay;
    }

    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

}