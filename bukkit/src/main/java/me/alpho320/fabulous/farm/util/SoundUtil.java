package me.alpho320.fabulous.farm.util;

import me.alpho320.fabulous.core.bukkit.BukkitCore;
import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.BukkitFarmPlugin;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundUtil {

    public static Map<Sound, Integer> getSoundFromList(List<String> list) {
        Map<Sound, Integer> sounds = new HashMap<>();

        for (String sound : list) {
            try {
                String[] split = sound.split(":");

                sounds.put(
                        Sound.valueOf(split[0]),
                        Integer.parseInt(split[1])
                );
            } catch (Exception e) {
                Debug.debug(1, sound + " is not valid a sound!");
            }
        }

        return sounds;
    }

    public static void sendSoundFromConfig(Player player, String key) {
        if (key == null || player == null) return;
        BukkitCore.instance().sound().send(
                player,
                matchSound(BukkitFarmPlugin.instance().getConfig().getString("Sound." + key, "null"))
        );
    }

    public static void sendSoundFromConfig(List<Player> players, String key) {
        if (key == null || players == null) return;
        BukkitCore.instance().sound().send(
                players,
                matchSound(BukkitFarmPlugin.instance().getConfig().getString("Sound." + key, "null"))
        );
    }

    public static @NotNull Sound matchSound(String key) {
        Sound sound = null;
        try {
            sound = Sound.valueOf(key);
        } catch (Exception ignored) {
            sound = Sound.values()[1];
            Debug.debug(1, " | Sound key of " + key + " is not valid! (Using: " + sound + ")");
        }

        return sound;
    }

}
