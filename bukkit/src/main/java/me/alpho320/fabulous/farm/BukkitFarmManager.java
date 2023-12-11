package me.alpho320.fabulous.farm;

import me.alpho320.fabulous.farm.api.FarmManager;
import me.alpho320.fabulous.farm.api.crop.BukkitCropManager;
import me.alpho320.fabulous.farm.api.pots.BukkitPotManager;
import org.jetbrains.annotations.NotNull;

public class BukkitFarmManager extends FarmManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitFarmManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
        this.cropManager = new BukkitCropManager(this.plugin);
        this.potManager = new BukkitPotManager(this.plugin);
    }

    @Override
    public void init(@NotNull FarmPlugin plugin) {
        cropManager.setup();
        potManager.setup();
    }

}