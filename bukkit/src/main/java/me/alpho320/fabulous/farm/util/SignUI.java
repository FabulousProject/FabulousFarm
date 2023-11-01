package me.alpho320.fabulous.farm.util;

import me.alpho320.fabulous.core.api.manager.impl.sign.SignGUI;
import me.alpho320.fabulous.core.bukkit.BukkitCore;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SignUI {

    public static void openSign(@NotNull Player player, SignGUI.SignType type, String[] lines, Consumer<String[]> whenClose) {
        BukkitCore.instance().sign()
                .create()
                .setType(type)
                .withLines(lines)
                .whenClose(whenClose)
                .open(player);
    }

}