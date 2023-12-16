package me.alpho320.fabulous.farm.api.fertilizer.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.crop.CropHolder;
import me.alpho320.fabulous.farm.api.crop.CropStar;
import me.alpho320.fabulous.farm.api.event.EventType;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import me.alpho320.fabulous.farm.api.fertilizer.Fertilizer;
import me.alpho320.fabulous.farm.api.fertilizer.FertilizerHolder;
import me.alpho320.fabulous.farm.api.mode.Mode;
import me.alpho320.fabulous.farm.data.IntData;
import me.alpho320.fabulous.farm.util.item.ItemCreatorUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class QualityFertilizer extends Fertilizer {

    private @NotNull String name;
    private @NotNull ItemStack item;

    private double chance;

    private @NotNull Mode potMode;
    private @NotNull Map<EventType, List<EventAction>> events;

    private @NotNull IntData amount;

    public QualityFertilizer(@NotNull String id, @NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
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
        final int amount = this.amount.toInt();
        List<Map.Entry<Integer, CropStar>> list = crop.crop().stars().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue((o1, o2) -> (int) (o2.chance() - o1.chance())))
                .collect(Collectors.toList()); // sort by highest to lowest chance.
        Collections.reverse(list);  // reverse the list to lowest to highest chance because lowest star item is the best item.

        for (Map.Entry<Integer, CropStar> entry : list) {
            if (ThreadLocalRandom.current().nextDouble(100) <= entry.getValue().chance() + amount) {
                final Location location = crop.location().loc();
                location.getWorld().dropItemNaturally(location.clone(), entry.getValue().item());
                break;
            }
        }

        return true;
    }

}