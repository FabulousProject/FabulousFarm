package me.alpho320.fabulous.farm.api.sprinkler.animation;

import me.alpho320.fabulous.farm.FarmPlugin;
import me.alpho320.fabulous.farm.api.TypedManager;
import me.alpho320.fabulous.farm.api.condition.Condition;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class SprinklerAnimationManager extends TypedManager<String, Class<?>> {

    public @NotNull List<SprinklerAnimation> animationsFromSection(@NotNull ConfigurationSection section) {
        List<SprinklerAnimation> animations = new ArrayList<>();
        for (String key : section.getKeys(false)) {
            final SprinklerAnimation animation = animationFromSection(key, section.getConfigurationSection(key));
            if (animation != null) animations.add(animation);
        }
        return animations;
    }

    public @Nullable SprinklerAnimation animationFromSection(@NotNull String id, @NotNull ConfigurationSection section) {
        try {
            final Class<?> clazz = find(id);
            if (clazz == null) {
                plugin().logger().severe("   | Animation of " + id + " not found.");
                plugin().logger().severe("   | Available animations: " + Arrays.toString(map().keySet().toArray()));
                return null;
            }
            return (SprinklerAnimation) clazz.getConstructor(FarmPlugin.class, ConfigurationSection.class).newInstance(plugin(), section);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public @Nullable Class<?> find(String id) {
        if (id == null) return null;
        Class<?> clazz = map().getOrDefault(id, null);
        if (clazz == null) {
            for (Map.Entry<String, Class<?>> entry : map().entrySet()) {
                if (entry.getKey().equalsIgnoreCase(id) || id.toLowerCase().startsWith(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return clazz;
    }

    @Override
    public @Nullable Class<?> fromSection(String id, ConfigurationSection section) {
        SprinklerAnimation animation = animationFromSection(id, section);
        return animation != null ? animation.getClass() : null;
    }



}