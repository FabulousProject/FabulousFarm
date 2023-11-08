package me.alpho320.fabulous.farm.util.item;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.lone.itemsadder.api.CustomStack;
import me.alpho320.fabulous.core.bukkit.BukkitCore;
import me.alpho320.fabulous.farm.FarmPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A library for the Bukkit API to create player skulls & custom items
 * from names, base64 strings, and texture URLs.
 *
 * Does not use any NMS code, and should work across all versions.
 *
 * @author Dean B on 12/28/2016.
 * @author Cahit Şahin on 01/26/2021.
 *
 */
public class ItemCreatorUtil {

    public static final @NotNull Map<UUID, ItemStack> headMap = new ConcurrentHashMap<>();

    public static ItemStack createItem(Material material, String name, short i, List<String> lore){
        return createItem(material, name, i, lore, 1);
    }

    public static ItemStack createItem(Material material, String name, List<String> lore){
        return createItem(material, name, (short)0, lore, 1);
    }

    public static ItemStack createItem(Material material, String name, short i, List<String> lore, int amount){
        ItemStack item;

        if (i <= 0)
            item = new ItemStack(material, 1);
        else
            item = new ItemStack(material, 1, i);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(lore);

        item.setItemMeta(meta);
        item.setAmount(amount);

        return item;
    }

    public static ItemStack createItem(Material material, String name, short i, List<String> lore, int amount, int modelData) {
        ItemStack item = createItem(material, name, i, lore, amount);

        ItemMeta meta = item.getItemMeta();
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getItem(String material, String name, short i, int amount, int modelData, List<String> lore, List<String> enchantments) {
        ItemStack item = getItem(material, name, i, amount, lore);

        ItemMeta meta = item.getItemMeta();

        item.setItemMeta(meta);
        item.addUnsafeEnchantments(getEnchantmentsFromList(enchantments));

        return item;
    }

    public static ItemStack getItem(String material, String name, short i, int amount, int modelData, List<String> lore, List<String> enchantments, List<String> flags) {
        ItemStack item = getItem(material, name, i, amount, modelData, lore, enchantments);

        if (item.hasItemMeta())
            getFlagsFromList(flags).forEach(flag -> item.getItemMeta().addItemFlags(flag));

        return item;
    }

    public static ItemStack getItem(String material, String name, short i, int amount, int modelData, List<String> lore, List<String> enchantments, List<String> flags, Map<String, Map<String, Object>> nbt) {
        NBTItem item = new NBTItem(getItem(material, name, i, amount, modelData, lore, enchantments, flags));

        for (Map.Entry<String, Map<String, Object>> entry : nbt.entrySet()) {
            NBTCompound compound = item.getOrCreateCompound(entry.getKey());

            for (Map.Entry<String, Object> e : entry.getValue().entrySet()) {
                if (e.getKey().contains("mcomp-")) {
                    item.setObject(e.getKey().split("-")[1], e.getValue());
                } else {
                    compound.setObject(e.getKey(), e.getValue());
                }
            }
        }

        return item.getItem();
    }

    public static ItemStack getItem(String material, String name, short i, List<String> lore) {
        ItemStack item;

        if (material.startsWith("ITEMSADDER-")) {
            CustomStack stack = CustomStack.getInstance(material.split("-")[1]);
            if (stack != null) {
                ItemStack clone = stack.getItemStack().clone();
                ItemMeta meta = clone.getItemMeta();

                meta.setLore(BukkitCore.instance().message().colored(lore));
                meta.setDisplayName(BukkitCore.instance().message().colored(name));

                clone.setItemMeta(meta);
                return clone;
            }
        }

        try {
            item = createItem(Material.matchMaterial(material), name, i, lore);
        } catch (Exception e) {
            item = createSkull(itemFromBase64(material), name, lore);
        }
        return item;
    }

    public static ItemStack getItem(String material, String name, short i, int amount, List<String> lore) {
        ItemStack item = getItem(material, name, i, lore);
        item.setAmount(amount);

        return item;
    }

    public static ItemStack getItem(Player player, String material, String name, short data, List<String> lore) {
        ItemStack item;

        if ((material.contains("%player_head%") || material.contains("%player-head%")) && player != null) {
            item = createSkull(
                    itemFromName(player.getName()),
                    BukkitCore.instance().message().colored(name),
                    BukkitCore.instance().message().colored(lore)
            );
        } else if (material.startsWith("customHead-")) {
            item = createSkull(
                    itemFromName(material.split("-")[1]),
                    BukkitCore.instance().message().colored(name),
                    BukkitCore.instance().message().colored(lore)
            );
        } else {
            item = getItem(material, name, data, lore);
        }
        return item;
    }

    public static ItemStack getItem(Player player, String material, String name, short data, int amount, List<String> lore) {
        ItemStack item = getItem(player, material, name, data, lore);
        item.setAmount(amount);

        return item;
    }

    public static ItemStack getItem(Player player, String material, String name, short data, int amount, int modelData, List<String> lore) {
        ItemStack item = getItem(player, material, name, data, amount, lore);

        ItemMeta meta = item.getItemMeta();
        item.setItemMeta(meta);

        return item;
    }


    public static List<ItemFlag> getFlagsFromList(List<String> list) {
        List<ItemFlag> flags = new ArrayList<>();

        for (String flag : list) {
            try {
                flags.add(ItemFlag.valueOf(flag));
            } catch (Exception e) {
                throw new IllegalArgumentException(flag + " is not valid ItemFlag!");
            }
        }
        return flags;
    }

    public static Map<Enchantment, Integer> getEnchantmentsFromList(List<String> list) {
        Map<Enchantment, Integer> map = new HashMap<>();

        for (String enchantment : list) {
            try {
                String[] split = enchantment.split(":");

                if (Enchantment.getByName(split[0]) == null)
                    throw new IllegalArgumentException(split[0] + " is not valid a enchantment!");

                map.put(Enchantment.getByName(split[0]), Integer.parseInt(split[1]));
            } catch (Exception e) {
                throw new IllegalArgumentException(enchantment + " is not valid type of ench!");
            }
        }

        return map;
    }

    public static ItemStack getItemWithGlow(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.DURABILITY,1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack removeGlowFromItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        meta.removeEnchant(Enchantment.DURABILITY);
        meta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack setLore(ItemStack item, List<String> lore) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    /**
     * Creates a player skull based on a player's name.
     *
     * @param name The Player's name
     * @return The head of the Player
     *
     * @deprecated names don't make for good identifiers
     */
    @Deprecated
    public static ItemStack itemFromName(String name) {
        ItemStack item = getPlayerSkullItem();

        return itemWithName(item, name);
    }

    /**
     * Creates a player skull based on a player's name.
     *
     * @param item The item to apply the name to
     * @param name The Player's name
     * @return The head of the Player
     *
     * @deprecated names don't make for good identifiers
     */
    @Deprecated
    public static ItemStack itemWithName(ItemStack item, String name) {
        notNull(item, "item");
        notNull(name, "name");

        ItemStack itemStack = Bukkit.getUnsafe().modifyItemStack(item,
                "{SkullOwner:\"" + name + "\"}"
        );
        return itemStack.clone();
    }

    /**
     * Creates a player skull with a UUID. 1.13 only.
     *
     * @param id The Player's UUID
     * @return The head of the Player
     */
    public static ItemStack itemFromUuid(UUID id) {
        ItemStack item = getPlayerSkullItem();

        return itemWithUuid(item, id);
    }

    /**
     * Creates a player skull based on a UUID. 1.13 only.
     *
     * @param item The item to apply the name to
     * @param id The Player's UUID
     * @return The head of the Player
     */
    public static ItemStack itemWithUuid(ItemStack item, UUID id) {
        notNull(item, "item");
        notNull(id, "id");

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(Bukkit.getOfflinePlayer(id).getName());
        item.setItemMeta(meta);

        return item;
    }

    public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static ItemStack[] itemStackArrayFromBase64(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            ItemStack[] itemStacks = new ItemStack[1];
            itemStacks[0] = getPlayerSkullItem();
            return itemStacks;
        }
    }

    /**
     * Creates a player skull based on a UUID. 1.13 only.
     *
     * @param id The Player's UUID
     * @return The head of the Player
     */
    public static ItemStack itemWithUuid(UUID id) {
        notNull(id, "id");
        ItemStack stack = headMap.getOrDefault(id, null);
        if (stack != null) return stack.clone();
        ItemStack item = getPlayerSkullItem();

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(Bukkit.getOfflinePlayer(id).getName());
        item.setItemMeta(meta);

        return item;
    }

    /**
     * Creates a player skull based on a Mojang server URL.
     *
     * @param url The URL of the Mojang skin
     * @return The head associated with the URL
     */
    public static ItemStack itemFromUrl(String url) {
        ItemStack item = getPlayerSkullItem();

        return itemWithUrl(item, url);
    }


    /**
     * Creates a player skull based on a Mojang server URL.
     *
     * @param item The item to apply the skin to
     * @param url The URL of the Mojang skin
     * @return The head associated with the URL
     */
    public static ItemStack itemWithUrl(ItemStack item, String url) {
        notNull(item, "item");
        notNull(url, "url");

        return itemWithBase64(item, urlToBase64(url));
    }

    /**
     * Creates a player skull based on a base64 string containing the link to the skin.
     *
     * @param base64 The base64 string containing the texture
     * @return The head with a custom texture
     */
    public static ItemStack itemFromBase64(String base64) {
        ItemStack item = getPlayerSkullItem();
        return itemWithBase64(item, base64);
    }

    /**
     * Applies the base64 string to the ItemStack.
     *
     * @param item The ItemStack to put the base64 onto
     * @param base64 The base64 string containing the texture
     * @return The head with a custom texture
     */
    public static ItemStack itemWithBase64(ItemStack item, String base64) {
        notNull(item, "item");
        notNull(base64, "base64");

        UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());
        return Bukkit.getUnsafe().modifyItemStack(item,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}"
        );
    }

    /**
     * Sets the block to a skull with the given name.
     *
     * @param block The block to set
     * @param name The player to set it to
     *
     * @deprecated names don't make for good identifiers
     */
    @Deprecated
    public static void blockWithName(Block block, String name) {
        notNull(block, "block");
        notNull(name, "name");

        setBlockType(block);
        ((Skull) block.getState()).setOwner(Bukkit.getOfflinePlayer(name).getName());
    }

    /**
     * Sets the block to a skull with the given UUID.
     *
     * @param block The block to set
     * @param id The player to set it to
     */
    public static void blockWithUuid(Block block, UUID id) {
        notNull(block, "block");
        notNull(id, "id");

        setBlockType(block);
        ((Skull) block.getState()).setOwner(Bukkit.getOfflinePlayer(id).getName());
    }

    /**
     * Sets the block to a skull with the given UUID.
     *
     * @param block The block to set
     * @param url The mojang URL to set it to use
     */
    public static void blockWithUrl(Block block, String url) {
        notNull(block, "block");
        notNull(url, "url");

        blockWithBase64(block, urlToBase64(url));
    }

    /**
     * Sets the block to a skull with the given UUID.
     *
     * @param block The block to set
     * @param base64 The base64 to set it to use
     */
    public static void blockWithBase64(Block block, String base64) {
        notNull(block, "block");
        notNull(base64, "base64");

        UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());

        String args = String.format(
                "%d %d %d %s",
                block.getX(),
                block.getY(),
                block.getZ(),
                "{Owner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}"
        );

        if (newerApi()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "data merge block " + args);
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"blockdata " + args);
        }
    }

    private static boolean newerApi() {
        try {

            Material.valueOf("PLAYER_HEAD");
            return true;

        } catch (IllegalArgumentException e) { // If PLAYER_HEAD doesn't exist
            return false;
        }
    }

    private static ItemStack getPlayerSkullItem() {
        if (newerApi()) {
            return new ItemStack(Material.valueOf("PLAYER_HEAD"));
        } else {
            return new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (byte) 3);
        }
    }

    private static void setBlockType(Block block) {
        try {
            block.setType(Material.valueOf("PLAYER_HEAD"), false);
        } catch (IllegalArgumentException e) {
            block.setType(Material.valueOf("SKULL"), false);
        }
    }

    private static void notNull(Object o, String name) {
        if (o == null) {
            throw new NullPointerException(name + " should not be null!");
        }
    }

    private static String urlToBase64(String url) {

        URI actualUrl;
        try {
            actualUrl = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String toEncode = "{\"textures\":{\"SKIN\":{\"url\":\"" + actualUrl.toString() + "\"}}}";
        return Base64.getEncoder().encodeToString(toEncode.getBytes());
    }

    public static ItemStack createSkull(ItemStack item, String name, String...lore){
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createSkull(ItemStack item, String name, List<String> lore) {
        ItemMeta meta = item.getItemMeta();

        String x = name.replaceAll("&", "§");
        meta.setDisplayName(x);
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }
}