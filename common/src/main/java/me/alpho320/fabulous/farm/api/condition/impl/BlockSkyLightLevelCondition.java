package me.alpho320.fabulous.farm.api.condition.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.condition.Condition;
import me.alpho320.fabulous.farm.util.serializable.SerializableLocation;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockSkyLightLevelCondition extends Condition {

    private int skyLightLevel = 1;

    public BlockSkyLightLevelCondition(@NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        super(plugin, section);
    }

    @Override
    public boolean register() {
        try {
            if (!section.isSet("value")) {
                plugin.logger().severe("BlockSkyLightLevel | Sky light level value not found. Please check your section.");
                plugin.logger().severe("BlockSkyLightLevel | 'value' key not found.");
                plugin.logger().severe("BlockSkyLightLevel | Section: " + section);
                return false;
            }
            this.skyLightLevel = section.getInt("value", 0);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean check(@NotNull SerializableLocation location) {
        plugin.logger().debug(" BlockSkyLightLevel | LightFromSky:" + location.loc().getBlock().getLightFromSky());
        plugin.logger().debug(" BlockSkyLightLevel | LightLevel" + location.loc().getBlock().getLightLevel());
        plugin.logger().debug(" BlockSkyLightLevel | FromBlocks" + location.loc().getBlock().getLightFromBlocks());

        return location.loc().getBlock().getLightFromSky() >= skyLightLevel;
    }

    @Override
    public boolean remove(@Nullable SerializableLocation location, @Nullable Object object, int amount) {
        return true;
    }

}