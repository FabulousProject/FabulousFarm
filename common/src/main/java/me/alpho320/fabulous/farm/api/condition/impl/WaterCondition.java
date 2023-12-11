package me.alpho320.fabulous.farm.api.condition.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.condition.Condition;
import me.alpho320.fabulous.farm.api.pot.PotHolder;
import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class WaterCondition extends Condition {

    private int waterLevel;

    public WaterCondition(@NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        super(plugin, section);
    }

    @Override
    public boolean register() {
        try {
            if (!section.isSet("value")) {
                plugin.logger().severe("WaterCondition | Water level value not found. Please check your section.");
                plugin.logger().severe("WaterCondition | 'value' key not found.");
                plugin.logger().severe("WaterCondition | Section: " + section);
                return false;
            }
            this.waterLevel = section.getInt("value", 0);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean check(@NotNull SerializableLocation location) {
        PotHolder potHolder = plugin.farmManager().potManager().findHolder(location);
        if (potHolder == null) return false;

        return potHolder.water() >= waterLevel;
    }

}