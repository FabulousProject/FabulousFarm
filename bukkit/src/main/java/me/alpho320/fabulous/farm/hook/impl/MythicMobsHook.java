package me.alpho320.fabulous.farm.hook.impl;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class MythicMobsHook implements Hook, Listener {

    private final @NotNull BukkitFarmPlugin plugin;
    private boolean enabled;

    public MythicMobsHook(@NotNull BukkitFarmPlugin plugin, boolean enabled) {
        this.plugin = plugin;
        this.enabled = enabled;
    }

    @Override
    public boolean init() {
        plugin.registerListeners(this);
        return true;
    }

    @EventHandler
    public void onMMSpawn(MythicMobSpawnEvent event) {
        ActiveMob mob = event.getMob();
        String name = mob.getDisplayName() == null ? event.getEntity().getName() : mob.getDisplayName();

        Debug.debug(2, "MMobSpawnEvent " + name);
        Debug.debug(2, "enttype: " + event.getMobType().getEntityType());

        LivingEntity livingEntity = event.getLivingEntity();
        if (livingEntity == null) {
            Debug.debug(2, "Living entity null!");
            return;
        }

    }

    @Override
    public @NotNull String name() {
        return "MythicMobs";
    }

    @Override
    public boolean enabled() {
        return this.enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}