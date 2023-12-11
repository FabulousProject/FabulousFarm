package me.alpho320.fabulous.farm;

import me.alpho320.fabulous.farm.api.BukkitEventActionManager;
import me.alpho320.fabulous.farm.api.FarmManager;
import me.alpho320.fabulous.farm.api.condition.BukkitConditionManager;
import me.alpho320.fabulous.farm.api.crop.BukkitCropManager;
import me.alpho320.fabulous.farm.api.pots.BukkitPotManager;
import me.alpho320.fabulous.farm.api.wateringcan.BukkitWateringCanManager;
import org.jetbrains.annotations.NotNull;

public class BukkitFarmManager extends FarmManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitFarmManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
        this.conditionManager = new BukkitConditionManager(this.plugin);
        this.eventActionManager = new BukkitEventActionManager(this.plugin);
        this.wateringCanManager = new BukkitWateringCanManager(this.plugin);
        this.cropManager = new BukkitCropManager(this.plugin);
        this.potManager = new BukkitPotManager(this.plugin);
    }

    @Override
    public void init(@NotNull FarmPlugin plugin) {
        conditionManager.setup();
        eventActionManager.setup();

        cropManager.setup();
        potManager.setup();
        wateringCanManager.setup();
    }

}