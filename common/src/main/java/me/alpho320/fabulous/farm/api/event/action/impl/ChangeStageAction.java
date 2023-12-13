package me.alpho320.fabulous.farm.api.event.action.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import me.alpho320.fabulous.farm.api.crop.CropManager;
import me.alpho320.fabulous.farm.api.crop.CropStage;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChangeStageAction extends EventAction {

    private int stage;

    public ChangeStageAction(@NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        super(plugin, section);
    }

    @Override
    public boolean register() {
        try {
            if (section.isSet("value")) {
                this.stage = section.getInt("value", 0);
                return true;
            } else {
                plugin.logger().severe("ChangeStageAction | Stage value not found. Please check your section.");
                plugin.logger().severe("ChangeStageAction | 'value' key not found.");
                plugin.logger().severe("ChangeStageAction | Section: " + section);
            }
        } catch (Exception e) {
            plugin.logger().severe("ChangeStageAction | An error accorded while registering. (" + e.getMessage() + ")");
            plugin.logger().severe("EventAction | Section: " + section);
        }
        return false;
    }

    @Override
    public void execute(@NotNull Location location, @Nullable Event event, @Nullable Entity entity, @Nullable String extraInfo) {
        final CropManager cropManager = plugin.farmManager().cropManager();
        CropHolder cropHolder = cropManager.findHolder(location);

        if (cropHolder != null) {

            cropHolder.setGrowStage(stage); //todo: call event, checks, etc.
            CropStage cropStage = cropHolder.crop().stages().getOrDefault(cropHolder.growStage(), null);

            if (cropStage == null) {
                plugin.logger().severe("ChangeStageAction | CropStage of '" + cropHolder.growStage() + "' is not found.");
                return;
            }

            cropManager.updateCropModel(cropHolder, cropStage.model());
        }
    }

}