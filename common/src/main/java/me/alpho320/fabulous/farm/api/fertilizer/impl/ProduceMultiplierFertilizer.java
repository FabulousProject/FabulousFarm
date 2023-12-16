package me.alpho320.fabulous.farm.api.fertilizer.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import me.alpho320.fabulous.farm.api.crop.CropStage;
import me.alpho320.fabulous.farm.api.crop.CropStar;
import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import me.alpho320.fabulous.farm.api.fertilizer.Fertilizer;
import me.alpho320.fabulous.farm.api.fertilizer.FertilizerHolder;
import me.alpho320.fabulous.farm.api.mode.Mode;
import me.alpho320.fabulous.farm.api.pot.PotHolder;
import me.alpho320.fabulous.farm.data.IntData;
import me.alpho320.fabulous.farm.util.item.ItemCreatorUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProduceMultiplierFertilizer extends Fertilizer {

    private @NotNull String name;
    private @NotNull ItemStack item;

    private double chance;

    private @NotNull Mode potMode;
    private @NotNull Map<EventType, List<EventAction>> events;

    private @NotNull IntData amount;

    public ProduceMultiplierFertilizer(@NotNull String id, @NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        super(id, plugin, section);
    }

    @Override
    public @NotNull String name() {
        return this.name;
    }

    @Override
    public @NotNull ItemStack item() {
        return this.item.clone();
    }

    @Override
    public double chance() {
        return this.chance;
    }

    @Override
    public @NotNull Map<EventType, List<EventAction>> events() {
        return this.events;
    }

    @Override
    public @NotNull Mode potMode() {
        return this.potMode;
    }

    @Override
    public boolean register() {
        try {
            this.name = section.getString("name", "");
            this.item = ItemCreatorUtil.getItemFromSection(section.getConfigurationSection("item"),"material");
            this.chance = section.getDouble("chance", 100.0);
            this.potMode = new Mode(Mode.Type.match(section.getString("pots.mode", "ALL")), section.getStringList("pots.list"));
            this.events = plugin.farmManager().eventActionManager().actionMapFromSection(section.getConfigurationSection("events"));
            this.amount = IntData.of(section.getConfigurationSection("amount"));

            plugin.logger().info(" | Registered fertilizer type: " + id());
        } catch (Exception e) {
            e.printStackTrace();
            plugin.logger().warning(" | Failed to register fertilizer type: " + id());
        }
        return false;
    }

    @Override
    public boolean onGrow(@NotNull FertilizerHolder fertilizer, @NotNull CropHolder crop) {
        return false;
    }

    @Override
    public boolean onHarvest(@NotNull FertilizerHolder fertilizer, @NotNull CropHolder crop) {
        final PotHolder pot = fertilizer.potHolder();
        final CropStage cropStage = crop.currentStage();

        if (cropStage == null) return false;

        final int amount = this.amount.toInt();
        for (int i = 0; i < amount; i++) {
            final Location location = crop.location().loc();
            final CropStar star = crop.crop().star();

            cropStage.events().getOrDefault(EventType.ON_HARVEST, new ArrayList<>()).forEach(action -> action.execute(location));
            if (star != null) location.getWorld().dropItemNaturally(location.clone(), star.item());
        }

        return true;
    }

}