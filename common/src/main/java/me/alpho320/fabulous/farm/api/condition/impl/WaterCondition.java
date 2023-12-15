package me.alpho320.fabulous.farm.api.condition.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.FarmManager;
import me.alpho320.fabulous.farm.api.condition.Condition;
import me.alpho320.fabulous.farm.api.pot.PotHolder;
import me.alpho320.fabulous.farm.api.pot.PotManager;
import me.alpho320.fabulous.farm.api.sprinkler.SprinklerHolder;
import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public boolean check(@NotNull SerializableLocation location) {;
        final FarmManager farmManager = plugin.farmManager();
        final PotManager potManager = farmManager.potManager();

        PotHolder pot = potManager.findHolder(location, true);
        if (pot != null) return pot.water() >= waterLevel;

        SprinklerHolder sprinkler = farmManager.sprinklerManager().findHolder(location.loc());
        return sprinkler != null && sprinkler.water() >= waterLevel;
    }

    @Override
    public boolean remove(@Nullable SerializableLocation location, @Nullable Object object, int amount) {
        if (object != null) {
            if (object instanceof SprinklerHolder) {
                SprinklerHolder sprinklerHolder = (SprinklerHolder) object;
                sprinklerHolder.setWater(Math.max(0, sprinklerHolder.water() - waterLevel));
                return true;
            } else if (object instanceof PotHolder) {
                PotHolder potHolder = (PotHolder) object;
                potHolder.setWater(Math.max(0, potHolder.water() - waterLevel));

                if (potHolder.water() == 0) {
                    plugin.farmManager().potManager().updatePotModel(potHolder, potHolder.pot().dryModel());
                }

                return true;
            }
        }

        return false;
    }

}