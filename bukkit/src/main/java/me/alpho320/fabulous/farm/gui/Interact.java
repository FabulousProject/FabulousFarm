package me.alpho320.fabulous.farm.gui;

import me.alpho320.fabulous.core.bukkit.BukkitCore;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class Interact {

    private final List<String> commands;
    private final Map<Sound, Integer> sounds;

    private final InteractType type;

    public Interact(List<String> commands, Map<Sound, Integer> sounds, InteractType type) {
        this.commands = commands;
        this.sounds = sounds;
        this.type = type;
    }

    public void run(Player player) {
        FarmAPI.runSYNC(
                () -> {
                    FarmAPI.executeCommands(player, commands);

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