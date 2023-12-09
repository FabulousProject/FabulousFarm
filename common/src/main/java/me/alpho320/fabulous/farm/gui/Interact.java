package me.alpho320.fabulous.farm.gui;

import me.alpho320.fabulous.core.bukkit.BukkitCore;
import me.alpho320.fabulous.farm.FarmPlugin;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class Interact {

    private final @NotNull FarmPlugin plugin;
    private final @NotNull List<String> commands;
    private final @NotNull Map<Sound, Integer> sounds;

    private final @NotNull InteractType type;

    public Interact(@NotNull FarmPlugin plugin, @NotNull List<String> commands, @NotNull Map<Sound, Integer> sounds, @NotNull InteractType type) {
        this.plugin = plugin;
        this.commands = commands;
        this.sounds = sounds;
        this.type = type;
    }

    public void run(Player player) {
        plugin.runSYNC(
                () -> {
                    plugin.executeCommands(player, commands);

                    sounds.forEach((key, value) ->
                            BukkitCore.instance().sound().send(
                                    player,
                                    key,
                                    3
                            )
                    );
                }
        );
    }

    public InteractType getType() {
        return type;
    }

    public enum InteractType {
        WHEN_OPEN, WHEN_CLOSE, WHEN_BOTTOM_CLICK, WHEN_OUTSIDE_CLICK, WHEN_EMPTY_CLICK, WHEN_UPDATE;

        public static InteractType getType(String type) {
            if (type.equalsIgnoreCase("when-open") || type.equalsIgnoreCase("when_open")) {
                return WHEN_OPEN;
            } else if (type.equalsIgnoreCase("when-close") || type.equalsIgnoreCase("when_close")) {
                return WHEN_CLOSE;
            } else if (type.equalsIgnoreCase("when-bottom-click") || type.equalsIgnoreCase("when_bottom_click")) {
                return WHEN_BOTTOM_CLICK;
            } else if (type.equalsIgnoreCase("when-outside-click") || type.equalsIgnoreCase("when_outside_click")) {
                return WHEN_OUTSIDE_CLICK;
            } else if (type.equalsIgnoreCase("when-empty-click") || type.equalsIgnoreCase("when_empty_click")) {
                return WHEN_EMPTY_CLICK;
            } else if (type.equalsIgnoreCase("when-update") || type.equalsIgnoreCase("when_update")) {
                return WHEN_UPDATE;
            } else {
                throw new IllegalArgumentException(type + " is not valid interacttype!");
            }
        }
    }

}