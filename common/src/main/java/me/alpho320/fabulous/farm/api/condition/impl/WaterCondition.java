package me.alpho320.fabulous.farm.api.condition.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.condition.Condition;
import me.alpho320.fabulous.farm.api.pot.PotHolder;
import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.jetbrains.annotations.NotNull;

public class WaterCondition extends Condition {

    private final int waterLevel;

    public WaterCondition(@NotNull FarmPlugin plugin, int waterLevel) {
        super(plugin);
        this.waterLevel = waterLevel;
    }

    @Override
    public boolean check(@NotNull SerializableLocation location) {
        PotHolder potHolder = plugin.farmManager().potManager().findHolder(location);
        if (potHolder == null) return false;

        return potHolder.water() >= waterLevel;
    }

}