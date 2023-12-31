package me.alpho320.fabulous.farm.api.event.action.impl;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.event.action.EventAction;
import me.alpho320.fabulous.farm.util.item.ItemCreatorUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DropItemAction extends EventAction {

    private ItemStack item;

    public DropItemAction(@NotNull FarmPlugin plugin, @NotNull ConfigurationSection section) {
        super(plugin, section);
    }

    @Override
    public boolean register() {
        try {
            this.item = ItemCreatorUtil.getItemFromSection(section, "material");
            return true;
        } catch (Exception e) {
            plugin.logger().severe("EventAction | DropItemAction of "+ section.getString("material", "null") +" not found.");
            plugin.logger().severe("EventAction | Section: " + section);
        }
        return false;
    }

    @Override
    public void execute(@NotNull Location location, @Nullable Event event, @Nullable Entity entity, @Nullable String extraInfo) {
        Location finalLocation = location.clone();

        if (entity != null) {
            finalLocation = entity.getLocation().clone();
        } else if (tryToGetLocation(event) != null) {
            finalLocation = tryToGetLocation(event).clone();
        }

        finalLocation.getWorld().dropItemNaturally(finalLocation, item.clone());

    }

}