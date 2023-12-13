package me.alpho320.fabulous.farm.api;

import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.event.action.EventActionManager;
import me.alpho320.fabulous.farm.api.event.action.impl.ChangeStageAction;
import me.alpho320.fabulous.farm.api.event.action.impl.DropExpAction;
import me.alpho320.fabulous.farm.api.event.action.impl.DropItemAction;
import me.alpho320.fabulous.farm.api.event.action.impl.PlayEffectAction;
import me.alpho320.fabulous.farm.api.event.action.impl.PlaySoundAction;
import me.alpho320.fabulous.farm.api.event.action.impl.ReplantAction;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class BukkitEventActionManager extends EventActionManager {

    private final @NotNull BukkitFarmPlugin plugin;

    public BukkitEventActionManager(@NotNull BukkitFarmPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup() {
        plugin.logger().info(" | Loading event actions...");

        register("change-stage", ChangeStageAction.class);
        register("drop-exp", DropExpAction.class);
        register("drop-item", DropItemAction.class);
        register("play-effect", PlayEffectAction.class);
        register("play-sound", PlaySoundAction.class);
        register("replant", ReplantAction.class);
        //todo: call EventActionRegisterEvent

        plugin.logger().info(" | Amount of " + map().size() + " event actions loaded. (" + Arrays.toString(map().keySet().toArray()) + ")");
    }

    @Override
    public @NotNull FarmPlugin plugin() {
        return this.plugin;
    }

}