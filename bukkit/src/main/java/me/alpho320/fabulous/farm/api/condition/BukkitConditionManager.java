package me.alpho320.fabulous.farm.api.condition;

import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.condition.impl.SeasonCondition;
import me.alpho320.fabulous.farm.api.condition.impl.WaterCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class BukkitConditionManager extends ConditionManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitConditionManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        plugin.logger().info(" | Loading conditions...");

        register("season", SeasonCondition.class);
        register("water", WaterCondition.class);
        // TODO: 11/12/2023 call ConditionRegisterEvent

        plugin.logger().info(" | Amount of " + map().size() + " conditions loaded. (" + Arrays.toString(map().keySet().toArray()) + ")");
    }

    @Override
    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

}